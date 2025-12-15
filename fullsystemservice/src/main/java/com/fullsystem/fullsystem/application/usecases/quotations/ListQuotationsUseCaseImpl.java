package com.fullsystem.fullsystem.application.usecases.quotations;

import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.domain.ports.in.quotations.ListQuotationsUseCase;
import com.fullsystem.fullsystem.domain.ports.out.QuotationRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the ListQuotationsUseCase.
 * Retrieves all quotations from the system.
 */
@Service
public class ListQuotationsUseCaseImpl implements ListQuotationsUseCase {

    private final QuotationRepositoryPort quotationRepositoryPort;

    public ListQuotationsUseCaseImpl(QuotationRepositoryPort quotationRepositoryPort) {
        this.quotationRepositoryPort = quotationRepositoryPort;
    }

    @Override
    public List<Quotation> listAllQuotations() {
        return quotationRepositoryPort.findAll();
    }
}
