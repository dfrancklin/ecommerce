package br.com.company.ecommerce.consumers;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.company.ecommerce.enums.ReportStatus;
import br.com.company.ecommerce.enums.ReportType;
import br.com.company.ecommerce.models.Report;
import br.com.company.ecommerce.services.reports.LoadReportByIdOnlyService;
import br.com.company.ecommerce.services.reports.ReportExporterService;
import br.com.company.ecommerce.services.reports.UpdateReportStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportConsumer {

    private final LoadReportByIdOnlyService loadReportByIdOnlyService;

    private final UpdateReportStatusService updateReportStatusService;

    private final List<ReportExporterService> reportExporterServices;

    @RabbitListener(queues = "${report.queue}")
    public void onMessage(String reportId) {
        log.info("Starting to process report id {}", reportId);

        Report report = null;

        try {
            report = loadReportByIdOnlyService.loadByIdOnly(Long.parseLong(reportId));
        } catch (Exception e) {
            log.error("Unable to process report id {}. Error: {}", reportId, e.getMessage());
            log.debug("Error stack:", e);
            return;
        }

        try {
            updateReportStatusService.updateStatus(report, ReportStatus.IN_PROGRESS);

            final ReportType type = report.getType();

            ReportExporterService exporter = reportExporterServices.stream()
                    .filter(item -> item.supports(type))
                    .findFirst()
                    .orElseThrow();

            exporter.export(report);

            updateReportStatusService.updateStatus(report, ReportStatus.COMPLETED);

            log.info("Report id {} completed", reportId);
        } catch (Exception e) {
            log.error("Unable to process report id {}. Error: {}", reportId, e.getMessage());
            log.debug("Error stack:", e);

            if (report != null) {
                updateReportStatusService.updateStatus(report, ReportStatus.ERROR);
            }
        }
    }

}
