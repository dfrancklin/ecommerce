package br.com.company.ecommerce.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateHistoryRequest {

    private Long accountId;

    private String signature;

    private Object[] arguments;

}
