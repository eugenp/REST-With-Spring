package com.baeldung.rwsb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.rwsb.domain.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
