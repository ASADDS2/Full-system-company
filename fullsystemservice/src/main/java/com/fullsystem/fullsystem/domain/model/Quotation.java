package com.fullsystem.fullsystem.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain model representing a quotation for a service request.
 * This is a pure POJO with no framework dependencies.
 */
public class Quotation {

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
    private LocalDateTime updatedAt;

    public Quotation() {
    }

    public Quotation(Long id, String clientName, ServiceType serviceType, Integer hours,
            Boolean isRecurringClient, BigDecimal subtotal, BigDecimal discountAmount,
            BigDecimal surchargeAmount, BigDecimal totalAmount, LocalDateTime createdAt) {
        this.id = id;
        this.clientName = clientName;
        this.serviceType = serviceType;
        this.hours = hours;
        this.isRecurringClient = isRecurringClient;
        this.subtotal = subtotal;
        this.discountAmount = discountAmount;
        this.surchargeAmount = surchargeAmount;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Boolean getIsRecurringClient() {
        return isRecurringClient;
    }

    public void setIsRecurringClient(Boolean isRecurringClient) {
        this.isRecurringClient = isRecurringClient;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getSurchargeAmount() {
        return surchargeAmount;
    }

    public void setSurchargeAmount(BigDecimal surchargeAmount) {
        this.surchargeAmount = surchargeAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
