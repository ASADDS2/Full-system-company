package com.fullsystem.fullsystem.domain.ports.in.quotations;

import com.fullsystem.fullsystem.domain.model.Quotation;

/**
 * Input port (use case interface) for retrieving a quotation by ID.
 */
public interface GetQuotationUseCase {

    /**
     * Retrieves a quotation by its ID.
     * 
     * @param id the quotation ID
     * @return the quotation if found
     * @throws com.fullsystem.fullsystem.domain.exception.NotFoundException if
     *                                                                      quotation
     *                                                                      not
     *                                                                      found
     */
    Quotation getQuotationById(Long id);
}
