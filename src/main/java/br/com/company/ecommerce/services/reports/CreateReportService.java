package br.com.company.ecommerce.services.reports;

import br.com.company.ecommerce.dtos.CreateReportRequest;
import br.com.company.ecommerce.models.Report;

public interface CreateReportService {

    Report create(CreateReportRequest request);

}
