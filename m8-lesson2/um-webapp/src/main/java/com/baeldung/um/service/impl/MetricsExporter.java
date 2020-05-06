package com.baeldung.um.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
final class MetricsExporter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MeterRegistry meterRegistry;

    @Scheduled(fixedRate = 1000 * 30) // every 30 seconds
    public void exportMetrics() {
        meterRegistry.getMeters()
            .stream()
            .forEach(this::log);
    }

    private void log(Meter meter) {
        StringBuilder result = new StringBuilder();
        for (final Measurement measurement : meter.measure()) {
            result.append("[");
            result.append(measurement.getStatistic()
                .name());
            result.append(" ");
            result.append(measurement.getValue());
            result.append("]");
        }
        logger.info("Reporting metric {}={}", meter.getId()
            .toString(), result.toString());
    }
}
