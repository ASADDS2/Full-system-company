package com.fullsystem.fullsystem.application.usecases.quotations;

import com.fullsystem.fullsystem.domain.exception.NotFoundException;
import com.fullsystem.fullsystem.domain.exception.ValidationException;
import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.domain.ports.in.quotations.UpdateQuotationUseCase;
import com.fullsystem.fullsystem.domain.ports.out.QuotationRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class UpdateQuotationUseCaseImpl implements UpdateQuotationUseCase {

    private final QuotationRepositoryPort quotationRepositoryPort;

    public UpdateQuotationUseCaseImpl(QuotationRepositoryPort quotationRepositoryPort) {
        this.quotationRepositoryPort = quotationRepositoryPort;
    }

    @Override
    public Quotation updateQuotation(Long id, Quotation quotationData) {
        Quotation existingQuotation = quotationRepositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Quotation with ID " + id + " not found"));

        validate(quotationData);

        // Update fields
        existingQuotation.setClientName(quotationData.getClientName());
        existingQuotation.setServiceType(quotationData.getServiceType());
        existingQuotation.setHours(quotationData.getHours());
        existingQuotation.setIsRecurringClient(quotationData.getIsRecurringClient());
        existingQuotation.setUpdatedAt(LocalDateTime.now());

        // Recalculate
        calculateCosts(existingQuotation);

        return quotationRepositoryPort.save(existingQuotation);
    }

    private void validate(Quotation quotation) {
        if (quotation.getClientName() == null || quotation.getClientName().trim().isEmpty()) {
            throw new ValidationException("Client name is required");
        }
        if (quotation.getServiceType() == null) {
            throw new ValidationException("Service type is required");
        }
        if (quotation.getHours() <= 0) {
            throw new ValidationException("Hours must be greater than 0");
        }
    }

    private void calculateCosts(Quotation quotation) {
        // 1. Subtotal
        BigDecimal hourlyRate = BigDecimal.valueOf(quotation.getServiceType().getHourlyRate());
        BigDecimal subtotal = hourlyRate.multiply(BigDecimal.valueOf(quotation.getHours()));
        quotation.setSubtotal(subtotal);

        // 2. Discount (10% if recurring)
        BigDecimal discount = BigDecimal.ZERO;
        if (Boolean.TRUE.equals(quotation.getIsRecurringClient())) {
            discount = subtotal.multiply(new BigDecimal("0.10"));
        }
        quotation.setDiscountAmount(discount);

        // 3. Surcharge (15% if hours > 40)
        BigDecimal surcharge = BigDecimal.ZERO;
        if (quotation.getHours() > 40) {
            surcharge = subtotal.multiply(new BigDecimal("0.15"));
        }
        quotation.setSurchargeAmount(surcharge);

        // 4. Total
        BigDecimal total = subtotal.subtract(discount).add(surcharge);
        quotation.setTotalAmount(total);
    }
}
