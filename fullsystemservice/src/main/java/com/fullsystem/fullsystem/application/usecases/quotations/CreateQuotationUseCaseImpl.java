package com.fullsystem.fullsystem.application.usecases.quotations;

import com.fullsystem.fullsystem.domain.exception.ValidationException;
import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.domain.ports.in.quotations.CreateQuotationUseCase;
import com.fullsystem.fullsystem.domain.ports.out.QuotationRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Implementation of the CreateQuotationUseCase.
 * Contains the business logic for creating quotations with proper calculations.
 * 
 * Business Rules:
 * 1. Calculate subtotal = hourly_rate × hours
 * 2. Apply 10% discount if client is recurring
 * 3. Apply 15% surcharge if hours > 40
 * 4. Discount and surcharge are NOT mutually exclusive
 */
@Service
public class CreateQuotationUseCaseImpl implements CreateQuotationUseCase {

    private static final BigDecimal RECURRING_CLIENT_DISCOUNT_RATE = new BigDecimal("0.10"); // 10%
    private static final BigDecimal HIGH_DEDICATION_SURCHARGE_RATE = new BigDecimal("0.15"); // 15%
    private static final int HIGH_DEDICATION_THRESHOLD = 40; // hours

    private final QuotationRepositoryPort quotationRepositoryPort;

    public CreateQuotationUseCaseImpl(QuotationRepositoryPort quotationRepositoryPort) {
        this.quotationRepositoryPort = quotationRepositoryPort;
    }

    @Override
    public Quotation createQuotation(Quotation quotation) {
        // Validate input
        validateQuotationInput(quotation);

        // Calculate amounts based on business rules
        calculateQuotationAmounts(quotation);

        // Set creation timestamp
        quotation.setCreatedAt(LocalDateTime.now());

        // Save and return
        return quotationRepositoryPort.save(quotation);
    }

    /**
     * Validates the quotation input data.
     * 
     * @param quotation the quotation to validate
     * @throws ValidationException if validation fails
     */
    private void validateQuotationInput(Quotation quotation) {
        if (quotation.getClientName() == null || quotation.getClientName().trim().isEmpty()) {
            throw new ValidationException("Client name is required");
        }

        if (quotation.getServiceType() == null) {
            throw new ValidationException("Service type is required");
        }

        if (quotation.getHours() == null || quotation.getHours() <= 0) {
            throw new ValidationException("Hours must be greater than 0");
        }

        if (quotation.getIsRecurringClient() == null) {
            throw new ValidationException("Recurring client flag is required");
        }
    }

    /**
     * Calculates all monetary amounts for the quotation based on business rules.
     * 
     * @param quotation the quotation to calculate amounts for
     */
    private void calculateQuotationAmounts(Quotation quotation) {
        // Step 1: Calculate subtotal = hourly_rate × hours
        int hourlyRate = quotation.getServiceType().getHourlyRate();
        BigDecimal subtotal = BigDecimal.valueOf(hourlyRate)
                .multiply(BigDecimal.valueOf(quotation.getHours()))
                .setScale(2, RoundingMode.HALF_UP);
        quotation.setSubtotal(subtotal);

        // Step 2: Calculate discount (if recurring client)
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (Boolean.TRUE.equals(quotation.getIsRecurringClient())) {
            discountAmount = subtotal.multiply(RECURRING_CLIENT_DISCOUNT_RATE)
                    .setScale(2, RoundingMode.HALF_UP);
        }
        quotation.setDiscountAmount(discountAmount);

        // Step 3: Calculate surcharge (if hours > 40)
        BigDecimal surchargeAmount = BigDecimal.ZERO;
        if (quotation.getHours() > HIGH_DEDICATION_THRESHOLD) {
            surchargeAmount = subtotal.multiply(HIGH_DEDICATION_SURCHARGE_RATE)
                    .setScale(2, RoundingMode.HALF_UP);
        }
        quotation.setSurchargeAmount(surchargeAmount);

        // Step 4: Calculate total = subtotal - discount + surcharge
        BigDecimal totalAmount = subtotal
                .subtract(discountAmount)
                .add(surchargeAmount)
                .setScale(2, RoundingMode.HALF_UP);
        quotation.setTotalAmount(totalAmount);
    }
}
