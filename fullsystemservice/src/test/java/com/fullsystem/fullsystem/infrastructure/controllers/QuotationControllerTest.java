package com.fullsystem.fullsystem.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullsystem.fullsystem.domain.exception.NotFoundException;
import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.domain.model.ServiceType;
import com.fullsystem.fullsystem.domain.ports.in.quotations.CreateQuotationUseCase;
import com.fullsystem.fullsystem.domain.ports.in.quotations.GetQuotationUseCase;
import com.fullsystem.fullsystem.domain.ports.in.quotations.ListQuotationsUseCase;
import com.fullsystem.fullsystem.infrastructure.mappers.quotations.QuotationDtoMapper;
import com.fullsystem.fullsystem.infrastructure.web.dto.quotations.CreateQuotationRequest;
import com.fullsystem.fullsystem.infrastructure.web.dto.quotations.QuotationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuotationController.class)
class QuotationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateQuotationUseCase createQuotationUseCase;

    @MockBean
    private GetQuotationUseCase getQuotationUseCase;

    @MockBean
    private ListQuotationsUseCase listQuotationsUseCase;

    @MockBean
    private QuotationDtoMapper quotationDtoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateQuotationRequest validRequest;
    private Quotation quotation;
    private QuotationResponse response;

    @BeforeEach
    void setUp() {
        validRequest = new CreateQuotationRequest("Client A", ServiceType.WEB_DEVELOPMENT, 10, false);

        quotation = new Quotation();
        quotation.setId(1L);
        quotation.setClientName("Client A");

        response = new QuotationResponse();
        response.setId(1L);
        response.setClientName("Client A");
        response.setSubtotal(new BigDecimal("800000"));
        response.setTotalAmount(new BigDecimal("800000"));
    }

    @Test
    void createQuotation_ShouldReturnCreated() throws Exception {
        when(quotationDtoMapper.toDomain(any())).thenReturn(quotation);
        when(createQuotationUseCase.createQuotation(any())).thenReturn(quotation);
        when(quotationDtoMapper.toResponse(any())).thenReturn(response);

        mockMvc.perform(post("/api/quotations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.clientName").value("Client A"));
    }

    @Test
    void getQuotation_ShouldReturnOk_WhenFound() throws Exception {
        when(getQuotationUseCase.getQuotationById(1L)).thenReturn(quotation);
        when(quotationDtoMapper.toResponse(any())).thenReturn(response);

        mockMvc.perform(get("/api/quotations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void getQuotation_ShouldReturnNotFound_WhenNotFound() throws Exception {
        when(getQuotationUseCase.getQuotationById(99L)).thenThrow(new NotFoundException("Not found"));

        mockMvc.perform(get("/api/quotations/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void listQuotations_ShouldReturnList() throws Exception {
        when(listQuotationsUseCase.listAllQuotations()).thenReturn(Arrays.asList(quotation));
        when(quotationDtoMapper.toResponse(any())).thenReturn(response);

        mockMvc.perform(get("/api/quotations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }
}
