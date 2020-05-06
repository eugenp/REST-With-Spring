package com.baeldung.um.service.impl;

import org.springframework.scheduling.annotation.Scheduled;

final class MetricsExporterDropwizard {

    @Scheduled(fixedRate = 1000 * 30) // every 30 second
    public void exportMetrics() {

    }
}
