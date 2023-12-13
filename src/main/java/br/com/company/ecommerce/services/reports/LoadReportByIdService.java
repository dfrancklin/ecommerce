package br.com.company.ecommerce.services.reports;

import br.com.company.ecommerce.models.Report;

public interface LoadReportByIdService {

    Report loadById(Long id);

}
