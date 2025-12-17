package com.fit.v1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fit.v1.model.SleepTracker;
import com.fit.v1.service.SleepTrackerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sleep-tracker")
public class SleepTrackerController {

    @Autowired
    private SleepTrackerService sleepTrackerService;

    // Add new sleep tracker entry
    @PostMapping
    public ResponseEntity<SleepTracker> addSleepTracker(@RequestBody SleepTracker sleepTracker) {
        SleepTracker createdSleepTracker = sleepTrackerService.addSleepTracker(sleepTracker);
        return new ResponseEntity<>(createdSleepTracker, HttpStatus.CREATED);
    }

    // Get all entries
    @GetMapping
    public ResponseEntity<List<SleepTracker>> getAllSleepTracks() {
        return ResponseEntity.ok(sleepTrackerService.getAllSleepTracks());
    }

    // Get entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<SleepTracker> getSleepTrackerById(@PathVariable Long id) {
        Optional<SleepTracker> sleepTracker = sleepTrackerService.getSleepTrackerById(id);
        return sleepTracker.map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Update entry
    @PutMapping("/{id}")
    public ResponseEntity<SleepTracker> updateSleepTracker(@PathVariable Long id, @RequestBody SleepTracker sleepTracker) {
        Optional<SleepTracker> updatedSleepTracker = sleepTrackerService.updateSleepTracker(id, sleepTracker);
        return updatedSleepTracker.map(ResponseEntity::ok)
                                  .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Delete entry
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSleepTracker(@PathVariable Long id) {
        boolean isDeleted = sleepTrackerService.deleteSleepTracker(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
