package br.com.company.ecommerce.services.accounts;

import br.com.company.ecommerce.dtos.CreateAccountRequest;
import br.com.company.ecommerce.models.Account;

public interface CreateAccountService {

	Account create(CreateAccountRequest request);

}
