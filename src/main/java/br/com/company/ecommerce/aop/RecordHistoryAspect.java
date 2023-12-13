package br.com.company.ecommerce.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.company.ecommerce.dtos.CreateHistoryRequest;
import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.utils.CurrentAccount;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class RecordHistoryAspect {

    private final ObjectMapper mapper;

    private final RabbitTemplate rabbitTemplate;

    @Value("${history.queue}")
    private String historyQueue;

    @Around("@annotation(br.com.company.ecommerce.annotations.RecordHistory)")
    public Object recordHistory(ProceedingJoinPoint joinPoint) throws Throwable {
        Account account = CurrentAccount.get();
        Signature signature = joinPoint.getSignature();
        Object[] arguments = joinPoint.getArgs();
        Object result = joinPoint.proceed();

        CreateHistoryRequest request = CreateHistoryRequest.builder()
                .accountId(account.getId())
                .signature(signature.toString())
                .arguments(arguments)
                .build();

        rabbitTemplate.convertAndSend(historyQueue, mapper.writeValueAsString(request));

        return result;
    }

}
