package br.com.company.ecommerce.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSaleItemRequest {

	@NotNull(message = "ProductId must be informed")
	private Long productId;

	@NotNull(message = "Amount must be informed")
	@Min(value = 1, message = "Amount must be greater than zero")
	private int amount;

}
