package com.baeldung.rws.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.rws.domain.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
