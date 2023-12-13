package br.com.company.ecommerce.dtos;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePlatformRequest {

	@Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters")
	private String name;

	private Boolean enabled;

}
