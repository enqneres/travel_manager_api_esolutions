package com.esolutions.travel.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TravelControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    void createsTravelWithValidPayload() throws Exception {
        TravelRequest request = new TravelRequest("Lisboa", "Portugal",
                LocalDate.of(2026, 10, 10), LocalDate.of(2026, 10, 17),
                TravelStatus.PLANNED, "Conhecer Alfama");

        mockMvc.perform(post("/api/travels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.destination").value("Lisboa"));
    }

    @Test
    void rejectsInvalidDateRange() throws Exception {
        TravelRequest request = new TravelRequest("Lisboa", "Portugal",
                LocalDate.of(2026, 10, 17), LocalDate.of(2026, 10, 10),
                TravelStatus.PLANNED, null);

        mockMvc.perform(post("/api/travels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.dateRangeValid").exists());
    }
}
