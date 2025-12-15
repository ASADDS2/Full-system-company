package com.fullsystem.fullsystem.application.usecases.quotations;

import com.fullsystem.fullsystem.domain.exception.ValidationException;
import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.domain.model.ServiceType;
import com.fullsystem.fullsystem.domain.ports.out.QuotationRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateQuotationUseCaseImplTest {

    @Mock
    private QuotationRepositoryPort quotationRepositoryPort;

    @InjectMocks
    private CreateQuotationUseCaseImpl createQuotationUseCase;

    private Quotation validQuotation;

    @BeforeEach
    void setUp() {
        validQuotation = new Quotation();
        validQuotation.setClientName("Test Client");
        validQuotation.setServiceType(ServiceType.WEB_DEVELOPMENT); // 80000
        validQuotation.setHours(10);
        validQuotation.setIsRecurringClient(false);
    }

    @Test
    void createQuotation_ShouldCalculateCorrectly_WhenStandardRequest() {
        // Arrange
        // 80,000 * 10 = 800,000
        when(quotationRepositoryPort.save(any(Quotation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Quotation result = createQuotationUseCase.createQuotation(validQuotation);

        // Assert
        assertEquals(new BigDecimal("800000.00"), result.getSubtotal());
        assertEquals(new BigDecimal("0.00"), result.getDiscountAmount());
        assertEquals(new BigDecimal("0.00"), result.getSurchargeAmount());
        assertEquals(new BigDecimal("800000.00"), result.getTotalAmount());
        assertNotNull(result.getCreatedAt());
        verify(quotationRepositoryPort).save(validQuotation);
    }

    @Test
    void createQuotation_ShouldApplyDiscount_WhenRecurringClient() {
        // Arrange
        validQuotation.setIsRecurringClient(true);
        // 80,000 * 10 = 800,000
        // Discount 10% = 80,000
        // Total = 720,000
        when(quotationRepositoryPort.save(any(Quotation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Quotation result = createQuotationUseCase.createQuotation(validQuotation);

        // Assert
        assertEquals(new BigDecimal("800000.00"), result.getSubtotal());
        assertEquals(new BigDecimal("80000.00"), result.getDiscountAmount());
        assertEquals(new BigDecimal("0.00"), result.getSurchargeAmount());
        assertEquals(new BigDecimal("720000.00"), result.getTotalAmount());
    }

    @Test
    void createQuotation_ShouldApplySurcharge_WhenHighDedication() {
        // Arrange
        validQuotation.setHours(50);
        // 80,000 * 50 = 4,000,000
        // Surcharge 15% = 600,000
        // Total = 4,600,000
        when(quotationRepositoryPort.save(any(Quotation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Quotation result = createQuotationUseCase.createQuotation(validQuotation);

        // Assert
        assertEquals(new BigDecimal("4000000.00"), result.getSubtotal());
        assertEquals(new BigDecimal("0.00"), result.getDiscountAmount());
        assertEquals(new BigDecimal("600000.00"), result.getSurchargeAmount());
        assertEquals(new BigDecimal("4600000.00"), result.getTotalAmount());
    }

    @Test
    void createQuotation_ShouldApplyBoth_WhenRecurringAndHighDedication() {
        // Arrange
        validQuotation.setIsRecurringClient(true);
        validQuotation.setHours(50);
        // 80,000 * 50 = 4,000,000
        // Discount 10% = 400,000
        // Surcharge 15% = 600,000
        // Total = 4,000,000 - 400,000 + 600,000 = 4,200,000
        when(quotationRepositoryPort.save(any(Quotation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Quotation result = createQuotationUseCase.createQuotation(validQuotation);

        // Assert
        assertEquals(new BigDecimal("4000000.00"), result.getSubtotal());
        assertEquals(new BigDecimal("400000.00"), result.getDiscountAmount());
        assertEquals(new BigDecimal("600000.00"), result.getSurchargeAmount());
        assertEquals(new BigDecimal("4200000.00"), result.getTotalAmount());
    }

    @Test
    void createQuotation_ShouldThrowException_WhenClientNameMissing() {
        validQuotation.setClientName(null);
        assertThrows(ValidationException.class, () -> createQuotationUseCase.createQuotation(validQuotation));
    }

    @Test
    void createQuotation_ShouldThrowException_WhenHoursInvalid() {
        validQuotation.setHours(0);
        assertThrows(ValidationException.class, () -> createQuotationUseCase.createQuotation(validQuotation));
    }
}
