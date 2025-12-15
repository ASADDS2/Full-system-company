package com.fullsystem.fullsystem.application.usecases.quotations;

import com.fullsystem.fullsystem.domain.exception.NotFoundException;
import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.domain.ports.out.QuotationRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetQuotationUseCaseImplTest {

    @Mock
    private QuotationRepositoryPort quotationRepositoryPort;

    @InjectMocks
    private GetQuotationUseCaseImpl getQuotationUseCase;

    @Test
    void getQuotationById_ShouldReturnQuotation_WhenFound() {
        // Arrange
        Long id = 1L;
        Quotation expected = new Quotation();
        expected.setId(id);
        when(quotationRepositoryPort.findById(id)).thenReturn(Optional.of(expected));

        // Act
        Quotation result = getQuotationUseCase.getQuotationById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getQuotationById_ShouldThrowException_WhenNotFound() {
        // Arrange
        Long id = 1L;
        when(quotationRepositoryPort.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> getQuotationUseCase.getQuotationById(id));
    }
}
