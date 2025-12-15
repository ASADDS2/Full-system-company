package com.fullsystem.fullsystem.infrastructure.web.dto.quotations;

import com.fullsystem.fullsystem.domain.model.ServiceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating a new quotation request.
 * Contains validation rules for input fields.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuotationRequest {

    @NotBlank(message = "Client name is required")
    private String clientName;

    @NotNull(message = "Service type is required")
    private ServiceType serviceType;

    @NotNull(message = "Hours is required")
    @Min(value = 1, message = "Hours must be greater than 0")
    private Integer hours;

    @NotNull(message = "Recurring client flag is required")
    private Boolean isRecurringClient;
}
