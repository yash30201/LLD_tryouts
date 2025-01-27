package com.rentalsystem.Model;

import java.time.LocalDate;

public class Range {
    private final LocalDate start;
    private final LocalDate end;

    public Range(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    /**
     * Checks is the `other` range is intersecting with the current one. Same day ending / starting
     * is considered intersecting
     * @param other Checks if `other` is intersecting.
     * @return Returns `true` on intersecting
     */
    public boolean isIntersecting(Range other) {
        return !(start.isAfter(other.getEnd()) || end.isBefore(other.getStart()));
    }

    public int getRangeDays() {
        LocalDate diff = end.minusDays(start.getDayOfYear());
        return diff.getDayOfYear();
    }
}
