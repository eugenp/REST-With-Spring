package com.baeldung.common.metric.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/*
 * Updated to use Micrometer in Spring Boot 2
 * As per the new structure, it now exports a matrix of Meter IDs and Meter Measurements at each minute
 */

@Service
class ActuatorMetricService implements IActuatorMetricService {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private MeterRegistry registry;

    private final List<List<String>> metersByMinute;
    private final List<String> meters;

    public ActuatorMetricService() {
        metersByMinute = new ArrayList<List<String>>();
        meters = new ArrayList<String>();
    }

    // API

    @Override
    public Object[][] getGraphData() {
        final int colCount = meters.size() + 1;
        final int rowCount = metersByMinute.size() + 1;
        final Object[][] result = new Object[rowCount][colCount];

        final Date current = new Date();
        result[0][0] = "Time";
        int j = 1;
        for (final String meter : meters) {
            result[0][j] = meter;
            j++;
        }

        List<String> minuteOfStatuses;
        for (int i = 1; i < rowCount; i++) {
            minuteOfStatuses = metersByMinute.get(i - 1);
            result[i][0] = dateFormat.format(new Date(current.getTime() - (60000 * (rowCount - i))));
            for (j = 1; j <= minuteOfStatuses.size(); j++) {
                result[i][j] = minuteOfStatuses.get(j - 1);
            }
        }
        return result;
    }

    // Non - API

    @Scheduled(fixedDelay = 60000)
    private void exportMetrics() {
        final List<String> lastMinuteMetrics = new ArrayList<>();

        initializeStatuses(lastMinuteMetrics);
        updateMetrics(lastMinuteMetrics);

        metersByMinute.add(lastMinuteMetrics);
    }

    private final void updateMetrics(final List<String> lastMinuteMetrics) {
        String meterId;
        int indexOfMeter;
        for (final Meter meter : registry.getMeters()) {
            meterId = meter.getId()
                .toString();
            if (!meters.contains(meterId)) {
                meters.add(meterId);
                lastMinuteMetrics.add("");
            }
            StringBuilder meterMeasurements = new StringBuilder();
            for (final Measurement measurement : meter.measure()) {
                meterMeasurements.append(measurement.getStatistic()
                    .name() + " " + measurement.getValue());
            }
            indexOfMeter = meters.indexOf(meterId);
            lastMinuteMetrics.set(indexOfMeter, meterMeasurements.toString());
        }
    }

    private final void initializeStatuses(final List<String> measurements) {
        for (int i = 0; i < meters.size(); i++) {
            measurements.add("");
        }
    }

}