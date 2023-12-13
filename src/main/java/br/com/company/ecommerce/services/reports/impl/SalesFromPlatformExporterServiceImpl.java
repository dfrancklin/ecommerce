package br.com.company.ecommerce.services.reports.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.company.ecommerce.dtos.SaleFromPlatformReport;
import br.com.company.ecommerce.enums.ReportType;
import br.com.company.ecommerce.models.Report;
import br.com.company.ecommerce.repositories.SalesRepository;
import br.com.company.ecommerce.services.reports.ReportExporterService;
import br.com.company.ecommerce.utils.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesFromPlatformExporterServiceImpl implements ReportExporterService {

    private static final String[] HEADER = { "month", "platformId", "platformName", "count", "sum" };

    private final SalesRepository repository;

    @Value("${report.path}")
    private String path;

    @Override
    public boolean supports(ReportType type) {
        return ReportType.SALES_FROM_PLATFORM.equals(type);
    }

    @Override
    public void export(Report report) throws IOException {
        Pageable pageable = PageRequest.of(0, 100);
        Page<SaleFromPlatformReport> page = null;

        Long accountId = report.getAccount().getId();
        LocalDateTime startAt = report.getStartAt().atTime(LocalTime.MIN);
        LocalDateTime endAt = report.getEndAt().atTime(LocalTime.MAX);
        Long platformId = report.getPlatformId();

        try (CSVWriter writer = new CSVWriter(path, report.getFilename())) {
            writer.write(HEADER);

            do {
                page = repository.findAllSalesFromPlatform(accountId, startAt, endAt, platformId, pageable);

                for (SaleFromPlatformReport item : page.getContent()) {
                    String[] fields = {
                            item.getMonth(),
                            item.getPlatformId().toString(),
                            item.getPlatformName(),
                            item.getCount().toString(),
                            item.getSum().toString()
                    };

                    writer.write(fields);
                }

                pageable = page.nextPageable();
            } while (page.hasNext());
        } catch (Exception e) {
            log.error("Unable to write file for report id {}. Error: {}", report.getId(), e.getMessage());
            log.debug("Error stack:", e);
            throw e;
        }
    }

}
