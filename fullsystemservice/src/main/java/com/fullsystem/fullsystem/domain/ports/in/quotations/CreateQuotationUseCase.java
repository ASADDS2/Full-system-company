package com.fullsystem.fullsystem.domain.ports.in.quotations;

import com.fullsystem.fullsystem.domain.model.Quotation;

/**
 * Input port (use case interface) for creating a new quotation.
 * Defines the contract for quotation creation business logic.
 */
public interface CreateQuotationUseCase {

    /**
     * Creates a new quotation based on the provided data.
     * Applies business rules for calculating subtotal, discount, and surcharge.
     * 
     * @param quotation the quotation data to create
     * @return the created quotation with calculated amounts
     */
    Quotation createQuotation(Quotation quotation);
}
