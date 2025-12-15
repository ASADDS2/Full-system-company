package com.fullsystem.fullsystem.application.usecases.quotations;

import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.domain.ports.out.QuotationRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListQuotationsUseCaseImplTest {

    @Mock
    private QuotationRepositoryPort quotationRepositoryPort;

    @InjectMocks
    private ListQuotationsUseCaseImpl listQuotationsUseCase;

    @Test
    void listAllQuotations_ShouldReturnList() {
        // Arrange
        Quotation q1 = new Quotation();
        Quotation q2 = new Quotation();
        when(quotationRepositoryPort.findAll()).thenReturn(Arrays.asList(q1, q2));

        // Act
        List<Quotation> result = listQuotationsUseCase.listAllQuotations();

        // Assert
        assertEquals(2, result.size());
    }
}
