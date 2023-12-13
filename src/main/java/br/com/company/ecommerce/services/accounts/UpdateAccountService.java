package br.com.company.ecommerce.services.accounts;

import br.com.company.ecommerce.dtos.UpdateAccountRequest;
import br.com.company.ecommerce.models.Account;

public interface UpdateAccountService {

	Account update(UpdateAccountRequest request);

}
