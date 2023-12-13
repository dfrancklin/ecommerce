package br.com.company.ecommerce.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Credentials {

	@Email(message = "Invalid email")
	@NotBlank(message = "Email must be informed")
	private String email;

	@NotBlank(message = "Password must be informed")
	private String password;

}
