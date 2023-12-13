package br.com.company.ecommerce.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.company.ecommerce.models.History;
import br.com.company.ecommerce.services.history.LoadAllHistoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class HistoryController {

    private final LoadAllHistoryService loadAllHistoryService;

    @GetMapping
    public Page<History> getAllHistory(@ParameterObject Pageable pageable) {
        return loadAllHistoryService.loadAll(pageable);
    }

}
