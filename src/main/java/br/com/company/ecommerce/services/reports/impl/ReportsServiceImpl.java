package br.com.company.ecommerce.services.reports.impl;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.company.ecommerce.annotations.RecordHistory;
import br.com.company.ecommerce.dtos.CreateReportRequest;
import br.com.company.ecommerce.enums.ReportStatus;
import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.models.Platform;
import br.com.company.ecommerce.models.Report;
import br.com.company.ecommerce.repositories.ReportsRepository;
import br.com.company.ecommerce.services.platforms.LoadPlatformByIdService;
import br.com.company.ecommerce.services.reports.CreateReportService;
import br.com.company.ecommerce.services.reports.LoadReportByIdOnlyService;
import br.com.company.ecommerce.services.reports.LoadReportByIdService;
import br.com.company.ecommerce.services.reports.UpdateReportStatusService;
import br.com.company.ecommerce.utils.CurrentAccount;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportsServiceImpl
        implements CreateReportService, LoadReportByIdOnlyService, UpdateReportStatusService, LoadReportByIdService {

    private final LoadPlatformByIdService loadPlatformByIdService;

    private final ReportsRepository repository;

    private final RabbitTemplate rabbitTemplate;

    @Value("${report.queue}")
    private String reportQueue;

    @Override
    @RecordHistory
    public Report create(CreateReportRequest request) {
        Account current = CurrentAccount.get();
        Platform platform = null;

        if (request.getPlatformId() != null) {
            platform = loadPlatformByIdService.loadById(request.getPlatformId());
        }

        String filename = String.format("report-%s.csv", UUID.randomUUID());

        Report report = Report.builder()
                .type(request.getType())
                .startAt(request.getStartAt())
                .endAt(request.getEndAt())
                .platformId(platform != null ? platform.getId() : null)
                .filename(filename)
                .account(current)
                .build();

        Report created = repository.save(report);

        rabbitTemplate.convertAndSend(reportQueue, created.getId().toString());

        return created;
    }

    @Override
    public Report loadByIdOnly(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find report with provided id"));
    }

    @Override
    public void updateStatus(Report report, @NonNull ReportStatus status) {
        report.setStatus(status);

        repository.save(report);
    }

    @Override
    public Report loadById(Long id) {
        Account current = CurrentAccount.get();

        return repository.findByIdAndAccount(id, current)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find report with provided id"));
    }

}
