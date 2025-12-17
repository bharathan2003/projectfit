package com.fit.v1.repository;

import com.fit.v1.model.SleepTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepTrackRepository extends JpaRepository<SleepTracker, Long> {
}
