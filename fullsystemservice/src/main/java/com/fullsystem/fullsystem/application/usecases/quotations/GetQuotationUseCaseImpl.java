package com.fullsystem.fullsystem.application.usecases.quotations;

import com.fullsystem.fullsystem.domain.exception.NotFoundException;
import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.domain.ports.in.quotations.GetQuotationUseCase;
import com.fullsystem.fullsystem.domain.ports.out.QuotationRepositoryPort;
import org.springframework.stereotype.Service;

/**
 * Implementation of the GetQuotationUseCase.
 * Retrieves a quotation by its ID.
 */
@Service
public class GetQuotationUseCaseImpl implements GetQuotationUseCase {

    private final QuotationRepositoryPort quotationRepositoryPort;

    public GetQuotationUseCaseImpl(QuotationRepositoryPort quotationRepositoryPort) {
        this.quotationRepositoryPort = quotationRepositoryPort;
    }

    @Override
    public Quotation getQuotationById(Long id) {
        return quotationRepositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Quotation not found with id: " + id));
    }
}
