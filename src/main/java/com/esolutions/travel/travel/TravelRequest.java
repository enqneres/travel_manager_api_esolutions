package com.esolutions.travel.travel;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record TravelRequest(
        @NotBlank @Size(max = 120) String destination,
        @NotBlank @Size(max = 80) String country,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotNull TravelStatus status,
        @Size(max = 500) String notes
) {
    @AssertTrue(message = "endDate must be on or after startDate")
    public boolean isDateRangeValid() {
        return startDate != null && endDate != null && !endDate.isBefore(startDate);
    }
}
