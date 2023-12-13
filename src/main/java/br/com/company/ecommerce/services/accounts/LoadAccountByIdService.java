package br.com.company.ecommerce.services.accounts;

import br.com.company.ecommerce.models.Account;

public interface LoadAccountByIdService {

	Account loadById(Long id);

}
