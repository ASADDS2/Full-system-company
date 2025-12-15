package com.fullsystem.fullsystem.domain.model;

/**
 * Enum representing the types of services offered by Full System company.
 * Each service type has an associated hourly rate in COP (Colombian Pesos).
 */
public enum ServiceType {

    WEB_DEVELOPMENT(80000),
    GRAPHIC_DESIGN(60000),
    TECHNICAL_SUPPORT(40000);

    private final int hourlyRate;

    ServiceType(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    /**
     * Gets the hourly rate for this service type.
     * 
     * @return the hourly rate in COP
     */
    public int getHourlyRate() {
        return hourlyRate;
    }
}
