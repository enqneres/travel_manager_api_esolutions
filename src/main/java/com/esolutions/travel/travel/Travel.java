package com.esolutions.travel.travel;

import com.esolutions.travel.auth.AppUser;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "travels")
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser owner;

    @Column(nullable = false, length = 120)
    private String destination;

    @Column(nullable = false, length = 80)
    private String country;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TravelStatus status;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected Travel() {
    }

    public Travel(AppUser owner, String destination, String country, LocalDate startDate, LocalDate endDate,
                  TravelStatus status, String notes) {
        this.owner = owner;
        this.destination = destination;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.notes = notes;
    }

    @PrePersist
    void setCreatedAt() {
        createdAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public AppUser getOwner() { return owner; }
    public String getDestination() { return destination; }
    public String getCountry() { return country; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public TravelStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public OffsetDateTime getCreatedAt() { return createdAt; }

    public void update(String destination, String country, LocalDate startDate, LocalDate endDate,
                       TravelStatus status, String notes) {
        this.destination = destination;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.notes = notes;
    }
}
