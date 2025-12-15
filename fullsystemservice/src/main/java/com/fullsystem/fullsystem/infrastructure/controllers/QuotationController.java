package com.fullsystem.fullsystem.infrastructure.controllers;

import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.domain.ports.in.quotations.CreateQuotationUseCase;
import com.fullsystem.fullsystem.domain.ports.in.quotations.DeleteQuotationUseCase;
import com.fullsystem.fullsystem.domain.ports.in.quotations.GetQuotationUseCase;
import com.fullsystem.fullsystem.domain.ports.in.quotations.ListQuotationsUseCase;
import com.fullsystem.fullsystem.domain.ports.in.quotations.UpdateQuotationUseCase;
import com.fullsystem.fullsystem.infrastructure.mappers.quotations.QuotationDtoMapper;
import com.fullsystem.fullsystem.infrastructure.util.Trace;
import com.fullsystem.fullsystem.infrastructure.web.dto.quotations.CreateQuotationRequest;
import com.fullsystem.fullsystem.infrastructure.web.dto.quotations.QuotationResponse;
import com.fullsystem.fullsystem.infrastructure.web.response.AppResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for quotation operations.
 * Provides endpoints for creating, retrieving, and listing quotations.
 */
@RestController
@RequestMapping("/api/quotations")
@Tag(name = "Quotations", description = "Quotation management endpoints")
public class QuotationController {

    private final CreateQuotationUseCase createQuotationUseCase;
    private final GetQuotationUseCase getQuotationUseCase;
    private final ListQuotationsUseCase listQuotationsUseCase;
    private final DeleteQuotationUseCase deleteQuotationUseCase;
    private final UpdateQuotationUseCase updateQuotationUseCase;
    private final QuotationDtoMapper quotationDtoMapper;

    public QuotationController(CreateQuotationUseCase createQuotationUseCase,
            GetQuotationUseCase getQuotationUseCase,
            ListQuotationsUseCase listQuotationsUseCase,
            DeleteQuotationUseCase deleteQuotationUseCase,
            UpdateQuotationUseCase updateQuotationUseCase,
            QuotationDtoMapper quotationDtoMapper) {
        this.createQuotationUseCase = createQuotationUseCase;
        this.getQuotationUseCase = getQuotationUseCase;
        this.listQuotationsUseCase = listQuotationsUseCase;
        this.deleteQuotationUseCase = deleteQuotationUseCase;
        this.updateQuotationUseCase = updateQuotationUseCase;
        this.quotationDtoMapper = quotationDtoMapper;
    }

    /**
     * Creates a new quotation.
     * 
     * @param request the quotation creation request
     * @return the created quotation with calculated amounts
     */
    @PostMapping
    @Operation(summary = "Create quotation", description = "Creates a new quotation with automatic cost calculation")
    public ResponseEntity<AppResponse<QuotationResponse>> createQuotation(
            @Valid @RequestBody CreateQuotationRequest request) {

        Trace.info("Creating quotation for client: {}", request.getClientName());

        Quotation quotation = quotationDtoMapper.toDomain(request);
        Quotation createdQuotation = createQuotationUseCase.createQuotation(quotation);
        QuotationResponse response = quotationDtoMapper.toResponse(createdQuotation);

        Trace.info("Quotation created successfully with ID: {}", createdQuotation.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AppResponse.success(response, "Quotation created successfully"));
    }

    /**
     * Retrieves a quotation by ID.
     * 
     * @param id the quotation ID
     * @return the quotation if found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get quotation by ID", description = "Retrieves a quotation by its ID")
    public ResponseEntity<AppResponse<QuotationResponse>> getQuotation(@PathVariable Long id) {

        Trace.info("Retrieving quotation with ID: {}", id);

        Quotation quotation = getQuotationUseCase.getQuotationById(id);
        QuotationResponse response = quotationDtoMapper.toResponse(quotation);

        return ResponseEntity.ok(AppResponse.success(response, "Quotation retrieved successfully"));
    }

    /**
     * Lists all quotations.
     * 
     * @return list of all quotations
     */
    @GetMapping
    @Operation(summary = "List all quotations", description = "Retrieves all quotations in the system")
    public ResponseEntity<AppResponse<List<QuotationResponse>>> listQuotations() {

        Trace.info("Retrieving all quotations");

        List<Quotation> quotations = listQuotationsUseCase.listAllQuotations();
        List<QuotationResponse> responses = quotations.stream()
                .map(quotationDtoMapper::toResponse)
                .collect(Collectors.toList());

        Trace.info("Retrieved {} quotations", responses.size());

        return ResponseEntity.ok(AppResponse.success(responses, "Quotations retrieved successfully"));
    }

    /**
     * Updates an existing quotation.
     * 
     * @param id      the quotation ID
     * @param request the update request
     * @return the updated quotation
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update quotation", description = "Updates an existing quotation and recalculates amounts")
    public ResponseEntity<AppResponse<QuotationResponse>> updateQuotation(
            @PathVariable Long id,
            @Valid @RequestBody CreateQuotationRequest request) {

        Trace.info("Updating quotation with ID: {}", id);

        Quotation quotationData = quotationDtoMapper.toDomain(request);
        Quotation updatedQuotation = updateQuotationUseCase.updateQuotation(id, quotationData);
        QuotationResponse response = quotationDtoMapper.toResponse(updatedQuotation);

        return ResponseEntity.ok(AppResponse.success(response, "Quotation updated successfully"));
    }

    /**
     * Deletes a quotation.
     * 
     * @param id the quotation ID
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete quotation", description = "Deletes a quotation by its ID")
    public ResponseEntity<AppResponse<Void>> deleteQuotation(@PathVariable Long id) {

        Trace.info("Deleting quotation with ID: {}", id);

        deleteQuotationUseCase.deleteQuotation(id);

        return ResponseEntity.ok(AppResponse.success(null, "Quotation deleted successfully"));
    }
}
