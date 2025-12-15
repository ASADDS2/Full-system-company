package com.fullsystem.fullsystem.domain.ports.in.quotations;

import com.fullsystem.fullsystem.domain.model.Quotation;

/**
 * Use case input port for updating a quotation.
 */
public interface UpdateQuotationUseCase {

    /**
     * Updates an existing quotation.
     * 
     * @param id        the ID of the quotation to update
     * @param quotation the new data for the quotation
     * @return the updated quotation
     * @throws com.fullsystem.fullsystem.domain.exception.NotFoundException   if not
     *                                                                        found
     * @throws com.fullsystem.fullsystem.domain.exception.ValidationException if
     *                                                                        invalid
     */
    Quotation updateQuotation(Long id, Quotation quotation);
}
