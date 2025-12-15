package com.fullsystem.fullsystem.infrastructure.mappers.quotations;

import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.infrastructure.web.dto.quotations.CreateQuotationRequest;
import com.fullsystem.fullsystem.infrastructure.web.dto.quotations.QuotationResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Quotation domain model and DTOs.
 */
@Component
public class QuotationDtoMapper {

    /**
     * Converts CreateQuotationRequest DTO to Quotation domain model.
     * 
     * @param request the request DTO
     * @return the domain model
     */
    public Quotation toDomain(CreateQuotationRequest request) {
        if (request == null) {
            return null;
        }

        Quotation quotation = new Quotation();
        quotation.setClientName(request.getClientName());
        quotation.setServiceType(request.getServiceType());
        quotation.setHours(request.getHours());
        quotation.setIsRecurringClient(request.getIsRecurringClient());

        return quotation;
    }

    /**
     * Converts Quotation domain model to QuotationResponse DTO.
     * 
     * @param quotation the domain model
     * @return the response DTO
     */
    public QuotationResponse toResponse(Quotation quotation) {
        if (quotation == null) {
            return null;
        }

        QuotationResponse response = new QuotationResponse();
        response.setId(quotation.getId());
        response.setClientName(quotation.getClientName());
        response.setServiceType(quotation.getServiceType());
        response.setHours(quotation.getHours());
        response.setIsRecurringClient(quotation.getIsRecurringClient());
        response.setSubtotal(quotation.getSubtotal());
        response.setDiscountAmount(quotation.getDiscountAmount());
        response.setSurchargeAmount(quotation.getSurchargeAmount());
        response.setTotalAmount(quotation.getTotalAmount());
        response.setCreatedAt(quotation.getCreatedAt());

        return response;
    }
}
