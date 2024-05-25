package com.baeldung.rws.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.rws.domain.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
}
