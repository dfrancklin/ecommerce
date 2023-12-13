package br.com.company.ecommerce.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping(path = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
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

    @GetMapping(path = "/{id}/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadReportById(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Report report = loadReportByIdService.loadById(id);
        File file = new File(path + File.separator + report.getFilename());

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.addHeader("Content-Disposition", "attachment; filename=" + report.getFilename());

        try (InputStream input = new FileInputStream(file)) {
            IOUtils.copy(input, response.getOutputStream());
        } catch (Exception e) {
            log.error("Unable to read file from report id {}. Error: {}", id, e.getMessage());
            log.debug("Error stack:", e);
        }

        response.flushBuffer();
    }

}
