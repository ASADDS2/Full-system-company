package com.fullsystem.fullsystem.domain.ports.in.quotations;

import com.fullsystem.fullsystem.domain.model.Quotation;
import java.util.List;

/**
 * Input port (use case interface) for listing all quotations.
 */
public interface ListQuotationsUseCase {

    /**
     * Retrieves all quotations.
     * 
     * @return list of all quotations
     */
    List<Quotation> listAllQuotations();
}
