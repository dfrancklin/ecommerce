package br.com.company.ecommerce.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAccountRequest {

	@NotBlank(message = "Name must be informed")
	@Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters")
	private String name;

	@Email(message = "Invalid email")
	@NotBlank(message = "Email must be informed")
	private String email;

	@NotBlank(message = "Password must be informed")
	private String password;

}
