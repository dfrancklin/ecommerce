package br.com.company.ecommerce.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

	@Default
	private LocalDateTime timestamp = LocalDateTime.now();

	private int status;

	private String error;

	private String message;

	private String path;

	private List<Object> details;

}
