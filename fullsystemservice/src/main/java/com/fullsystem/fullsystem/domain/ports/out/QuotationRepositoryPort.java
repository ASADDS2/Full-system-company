package com.fullsystem.fullsystem.domain.ports.out;

import com.fullsystem.fullsystem.domain.model.Quotation;
import java.util.List;
import java.util.Optional;

/**
 * Output port (repository interface) for quotation persistence operations.
 * This interface is implemented by the infrastructure layer.
 */
public interface QuotationRepositoryPort {

    /**
     * Saves a quotation to the persistent storage.
     * 
     * @param quotation the quotation to save
     * @return the saved quotation
     */
    Quotation save(Quotation quotation);

    /**
     * Finds a quotation by its ID.
     * 
     * @param id the quotation ID
     * @return an Optional containing the quotation if found, empty otherwise
     */
    Optional<Quotation> findById(Long id);

    /**
     * Retrieves all quotations.
     * 
     * @return list of all quotations
     */
    List<Quotation> findAll();

    /**
     * Deletes a quotation by its ID.
     * 
     * @param id the quotation ID
     */
    void deleteById(Long id);
}
