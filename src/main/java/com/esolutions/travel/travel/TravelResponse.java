package com.esolutions.travel.travel;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record TravelResponse(Long id, String destination, String country, LocalDate startDate,
                             LocalDate endDate, TravelStatus status, String notes,
                             OffsetDateTime createdAt) {
    static TravelResponse from(Travel travel) {
        return new TravelResponse(travel.getId(), travel.getDestination(), travel.getCountry(),
                travel.getStartDate(), travel.getEndDate(), travel.getStatus(), travel.getNotes(),
                travel.getCreatedAt());
    }
}
