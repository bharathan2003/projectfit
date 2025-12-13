package com.fit.v1.model;

public enum SleepQuality {
    POOR, AVERAGE, GOOD, EXCELLENT;

    public static SleepQuality getSleepQuality(long minutes) {
        if (minutes < 240) return POOR;          // < 4 hours
        else if (minutes < 360) return AVERAGE;  // 4–6 hours
        else if (minutes < 480) return GOOD;     // 6–8 hours
        else return EXCELLENT;                   // > 8 hours
    }
}
