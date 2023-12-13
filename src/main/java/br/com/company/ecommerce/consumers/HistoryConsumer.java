package br.com.company.ecommerce.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.company.ecommerce.dtos.CreateHistoryRequest;
import br.com.company.ecommerce.services.history.CreateHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class HistoryConsumer {

    private final ObjectMapper mapper;

    private final CreateHistoryService createHistoryService;

    @RabbitListener(queues = "${history.queue}")
    public void onMessage(String message) {
        log.info("Starting to process operation history");

        try {
            CreateHistoryRequest request = mapper.readValue(message, CreateHistoryRequest.class);
            createHistoryService.create(request);
        } catch (Exception e) {
            log.error("Unable to process operation history {}. Error: {}", message, e.getMessage());
            log.debug("Error stack:", e);
        }
    }

}
