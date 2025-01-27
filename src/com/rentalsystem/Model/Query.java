package com.rentalsystem.Model;

import java.time.LocalDate;
import java.util.List;

public class Query {
    private final List<VehicleType> types;
    private final Integer maxPerDayPrice;
    private final Range dateRange;

    public Query(List<VehicleType> types, Integer maxPerDayPrice, Range dateRange) {
        this.types = types;
        this.maxPerDayPrice = maxPerDayPrice;
        this.dateRange = dateRange;
    }

    public static Query of(List<VehicleType> types, Integer maxPerDayPrice, LocalDate startDate, LocalDate endDate) {
        return new Query(types, maxPerDayPrice, new Range(startDate, endDate));
    }

    public boolean isVehicleType(VehicleType type) {
        return types.contains(type);
    }

    public Integer getMaxPerDayPrice() {
        return maxPerDayPrice;
    }

    public Range getDateRange() {
        return dateRange;
    }
}
