package com.fullsystem.fullsystem.domain.ports.in.quotations;

/**
 * Use case input port for deleting a quotation.
 */
public interface DeleteQuotationUseCase {

    /**
     * Deletes a quotation by its ID.
     * 
     * @param id the quotation ID
     * @throws com.fullsystem.fullsystem.domain.exception.NotFoundException if not
     *                                                                      found
     */
    void deleteQuotation(Long id);
}
