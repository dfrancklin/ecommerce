package br.com.company.ecommerce.services.reports.impl;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.company.ecommerce.dtos.CreateReportRequest;
import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.models.Platform;
import br.com.company.ecommerce.models.Report;
import br.com.company.ecommerce.repositories.ReportsRepository;
import br.com.company.ecommerce.services.platforms.LoadPlatformByIdService;
import br.com.company.ecommerce.services.reports.CreateReportService;
import br.com.company.ecommerce.utils.CurrentAccount;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportsServiceImpl implements CreateReportService {

    private final LoadPlatformByIdService loadPlatformByIdService;

    private final ReportsRepository repository;

    private final RabbitTemplate rabbitTemplate;

    @Value("${report.queue}")
    private String reportQueue;

    @Override
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

}
