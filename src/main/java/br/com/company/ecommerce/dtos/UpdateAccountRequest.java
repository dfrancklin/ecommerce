package br.com.company.ecommerce.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateAccountRequest {

	@Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters")
	private String name;

	@Email(message = "Invalid email")
	private String email;

	private String password;

}
