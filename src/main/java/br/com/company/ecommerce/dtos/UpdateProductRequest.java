package br.com.company.ecommerce.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProductRequest {

	@Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters")
	private String name;

	@Min(value = 0, message = "Price must be greater than zero")
	private BigDecimal price;

}
