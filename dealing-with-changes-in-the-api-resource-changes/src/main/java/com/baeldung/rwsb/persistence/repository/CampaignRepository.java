package com.baeldung.rwsb.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.rwsb.domain.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
}
