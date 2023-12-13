package br.com.company.ecommerce.dtos;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateHistoryRequest {

	@Size(min = 1, message = "Signature cannot be empty")
	private String signature;

	@Size(min = 1, message = "Arguments cannot be empty")
	private String arguments;

}
