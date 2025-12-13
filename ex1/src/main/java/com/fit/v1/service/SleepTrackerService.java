package com.fit.v1.service;

import com.fit.v1.model.SleepQuality;
import com.fit.v1.model.SleepTracker;
import com.fit.v1.repository.SleepTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class SleepTrackerService {

    @Autowired
    private SleepTrackRepository sleepTrackerRepository;

    // Utility method to calculate duration & quality
    private void calculateDurationAndQuality(SleepTracker tracker) {
        if (tracker.getStartTime() != null && tracker.getEndTime() != null) {
            long minutes = Duration.between(tracker.getStartTime(), tracker.getEndTime()).toMinutes();
            tracker.setSleepDurationInMinutes(minutes);
            tracker.setSleepQuality(SleepQuality.getSleepQuality(minutes));
        }
    }

    // Add new entry
    public SleepTracker addSleepTracker(SleepTracker sleepTracker) {
        calculateDurationAndQuality(sleepTracker);
        return sleepTrackerRepository.save(sleepTracker);
    }

    // Get all entries
    public List<SleepTracker> getAllSleepTracks() {
        return sleepTrackerRepository.findAll();
    }

    // Get by ID
    public Optional<SleepTracker> getSleepTrackerById(Long id) {
        return sleepTrackerRepository.findById(id);
    }

    // Update entry
    public Optional<SleepTracker> updateSleepTracker(Long id, SleepTracker updatedSleepTracker) {
        return sleepTrackerRepository.findById(id).map(existingTracker -> {
            existingTracker.setStartTime(updatedSleepTracker.getStartTime());
            existingTracker.setEndTime(updatedSleepTracker.getEndTime());
            calculateDurationAndQuality(existingTracker);
            return sleepTrackerRepository.save(existingTracker);
        });
    }

    // Delete entry
    public boolean deleteSleepTracker(Long id) {
        if (sleepTrackerRepository.existsById(id)) {
            sleepTrackerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
