package br.com.company.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePlatformRequest {

	@NotBlank(message = "Name must be informed")
	@Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters")
	private String name;

}
