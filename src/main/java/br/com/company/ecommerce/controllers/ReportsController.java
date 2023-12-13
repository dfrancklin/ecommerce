package br.com.company.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.company.ecommerce.dtos.CreateReportRequest;
import br.com.company.ecommerce.models.Report;
import br.com.company.ecommerce.services.reports.CreateReportService;
import br.com.company.ecommerce.services.reports.LoadReportByIdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReportsController {

    private final CreateReportService createReportService;

    private final LoadReportByIdService loadReportByIdService;

    @Value("${report.path}")
    private String path;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Report createReport(@Valid @RequestBody CreateReportRequest request) {
        return createReportService.create(request);
    }

    @GetMapping("/{id}")
    public Report getReportById(@PathVariable Long id) {
        return loadReportByIdService.loadById(id);
    }

}
