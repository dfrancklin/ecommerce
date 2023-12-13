package br.com.company.ecommerce.dtos;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSaleRequest {

	@NotNull(message = "Items must be informed")
	@Size(min = 1, message = "Sale must contain at least one item")
	private List<@Valid CreateSaleItemRequest> items;

}
