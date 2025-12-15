package com.fullsystem.fullsystem.infrastructure.repositories;

import com.fullsystem.fullsystem.infrastructure.entities.QuotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for QuotationEntity.
 * Provides CRUD operations and query methods for quotations.
 */
@Repository
public interface JpaQuotationRepository extends JpaRepository<QuotationEntity, Long> {
}
