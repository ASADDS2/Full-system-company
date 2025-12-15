package com.fullsystem.fullsystem.infrastructure.entities;

import com.fullsystem.fullsystem.domain.model.ServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA Entity for Quotation persistence.
 * Maps to the quotations table in the database.
 */
@Entity
@Table(name = "quotations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false)
    private ServiceType serviceType;

    @Column(name = "hours", nullable = false)
    private Integer hours;

    @Column(name = "is_recurring_client", nullable = false)
    private Boolean isRecurringClient;

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "discount_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "surcharge_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal surchargeAmount;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
