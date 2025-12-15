package com.fullsystem.fullsystem.application.usecases.quotations;

import com.fullsystem.fullsystem.domain.exception.NotFoundException;
import com.fullsystem.fullsystem.domain.ports.in.quotations.DeleteQuotationUseCase;
import com.fullsystem.fullsystem.domain.ports.out.QuotationRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteQuotationUseCaseImpl implements DeleteQuotationUseCase {

    private final QuotationRepositoryPort quotationRepositoryPort;

    public DeleteQuotationUseCaseImpl(QuotationRepositoryPort quotationRepositoryPort) {
        this.quotationRepositoryPort = quotationRepositoryPort;
    }

    @Override
    public void deleteQuotation(Long id) {
        if (quotationRepositoryPort.findById(id).isEmpty()) {
            throw new NotFoundException("Quotation with ID " + id + " not found");
        }
        quotationRepositoryPort.deleteById(id);
    }
}
