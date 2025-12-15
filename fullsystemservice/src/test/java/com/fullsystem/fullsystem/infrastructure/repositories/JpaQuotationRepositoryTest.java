package com.fullsystem.fullsystem.infrastructure.repositories;

import com.fullsystem.fullsystem.domain.model.ServiceType;
import com.fullsystem.fullsystem.infrastructure.entities.QuotationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaQuotationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JpaQuotationRepository repository;

    @Test
    void save_ShouldPersistQuotation() {
        // Arrange
        QuotationEntity entity = new QuotationEntity();
        entity.setClientName("Test Client");
        entity.setServiceType(ServiceType.WEB_DEVELOPMENT);
        entity.setHours(10);
        entity.setIsRecurringClient(false);
        entity.setSubtotal(new BigDecimal("800000.00"));
        entity.setDiscountAmount(BigDecimal.ZERO);
        entity.setSurchargeAmount(BigDecimal.ZERO);
        entity.setTotalAmount(new BigDecimal("800000.00"));
        entity.setCreatedAt(LocalDateTime.now());

        // Act
        QuotationEntity saved = repository.save(entity);

        // Assert
        assertNotNull(saved.getId());
        assertEquals("Test Client", saved.getClientName());
    }

    @Test
    void findById_ShouldReturnQuotation() {
        // Arrange
        QuotationEntity entity = new QuotationEntity();
        entity.setClientName("Test Client");
        entity.setServiceType(ServiceType.WEB_DEVELOPMENT);
        entity.setHours(10);
        entity.setIsRecurringClient(false);
        entity.setSubtotal(new BigDecimal("800000.00"));
        entity.setDiscountAmount(BigDecimal.ZERO);
        entity.setSurchargeAmount(BigDecimal.ZERO);
        entity.setTotalAmount(new BigDecimal("800000.00"));
        entity.setCreatedAt(LocalDateTime.now());

        entityManager.persist(entity);
        entityManager.flush();

        // Act
        QuotationEntity found = repository.findById(entity.getId()).orElse(null);

        // Assert
        assertNotNull(found);
        assertEquals(entity.getId(), found.getId());
    }
}
