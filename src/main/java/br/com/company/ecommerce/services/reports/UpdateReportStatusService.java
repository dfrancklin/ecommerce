package br.com.company.ecommerce.services.reports;

import br.com.company.ecommerce.enums.ReportStatus;
import br.com.company.ecommerce.models.Report;

public interface UpdateReportStatusService {

    void updateStatus(Report report, ReportStatus status);

}
