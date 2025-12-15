package com.fullsystem.fullsystem.infrastructure.web.dto.quotations;

import com.fullsystem.fullsystem.domain.model.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for quotation response.
 * Contains all calculated quotation information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationResponse {

    private Long id;
    private String clientName;
    private ServiceType serviceType;
    private Integer hours;
    private Boolean isRecurringClient;
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal surchargeAmount;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}
