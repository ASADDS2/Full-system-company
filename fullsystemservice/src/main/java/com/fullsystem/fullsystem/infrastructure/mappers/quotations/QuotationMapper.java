package com.fullsystem.fullsystem.infrastructure.mappers.quotations;

import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.infrastructure.entities.QuotationEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Quotation domain model and QuotationEntity.
 */
@Component
public class QuotationMapper {

    /**
     * Converts a Quotation domain model to QuotationEntity.
     * 
     * @param quotation the domain model
     * @return the entity
     */
    public QuotationEntity toEntity(Quotation quotation) {
        if (quotation == null) {
            return null;
        }

        QuotationEntity entity = new QuotationEntity();
        entity.setId(quotation.getId());
        entity.setClientName(quotation.getClientName());
        entity.setServiceType(quotation.getServiceType());
        entity.setHours(quotation.getHours());
        entity.setIsRecurringClient(quotation.getIsRecurringClient());
        entity.setSubtotal(quotation.getSubtotal());
        entity.setDiscountAmount(quotation.getDiscountAmount());
        entity.setSurchargeAmount(quotation.getSurchargeAmount());
        entity.setTotalAmount(quotation.getTotalAmount());
        entity.setCreatedAt(quotation.getCreatedAt());

        return entity;
    }

    /**
     * Converts a QuotationEntity to Quotation domain model.
     * 
     * @param entity the entity
     * @return the domain model
     */
    public Quotation toDomain(QuotationEntity entity) {
        if (entity == null) {
            return null;
        }

        Quotation quotation = new Quotation();
        quotation.setId(entity.getId());
        quotation.setClientName(entity.getClientName());
        quotation.setServiceType(entity.getServiceType());
        quotation.setHours(entity.getHours());
        quotation.setIsRecurringClient(entity.getIsRecurringClient());
        quotation.setSubtotal(entity.getSubtotal());
        quotation.setDiscountAmount(entity.getDiscountAmount());
        quotation.setSurchargeAmount(entity.getSurchargeAmount());
        quotation.setTotalAmount(entity.getTotalAmount());
        quotation.setCreatedAt(entity.getCreatedAt());

        return quotation;
    }
}
