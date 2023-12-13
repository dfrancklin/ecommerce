package br.com.company.ecommerce.services.reports;

import java.io.IOException;

import br.com.company.ecommerce.enums.ReportType;
import br.com.company.ecommerce.models.Report;

public interface ReportExporterService {

    boolean supports(ReportType type);

    void export(Report report) throws IOException;

}
