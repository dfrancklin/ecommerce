package br.com.company.ecommerce.services.history.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.company.ecommerce.dtos.CreateHistoryRequest;
import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.models.History;
import br.com.company.ecommerce.repositories.HistoryRepository;
import br.com.company.ecommerce.services.accounts.LoadAccountByIdService;
import br.com.company.ecommerce.services.history.CreateHistoryService;
import br.com.company.ecommerce.services.history.LoadAllHistoryService;
import br.com.company.ecommerce.utils.CurrentAccount;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements CreateHistoryService, LoadAllHistoryService {

    private final LoadAccountByIdService loadAccountByIdService;

    private final HistoryRepository repository;

    @Override
    public History create(CreateHistoryRequest request) {
        Account account = loadAccountByIdService.loadById(request.getAccountId());
        History history = History.builder()
                .signature(request.getSignature())
                .arguments(request.getArguments())
                .account(account)
                .build();

        return repository.save(history);
    }

    @Override
    public Page<History> loadAll(Pageable pageable) {
        Account current = CurrentAccount.get();

        return repository.findAllByAccount(current, pageable);
    }

}
