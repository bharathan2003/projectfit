package com.fit.v1.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sleep_tracker")
public class SleepTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private long sleepDurationInMinutes;

    @Enumerated(EnumType.STRING)
    private SleepQuality sleepQuality;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public long getSleepDurationInMinutes() { return sleepDurationInMinutes; }
    public void setSleepDurationInMinutes(long sleepDurationInMinutes) { this.sleepDurationInMinutes = sleepDurationInMinutes; }

    public SleepQuality getSleepQuality() { return sleepQuality; }
    public void setSleepQuality(SleepQuality sleepQuality) { this.sleepQuality = sleepQuality; }
}
