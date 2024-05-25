package com.baeldung.rws.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.rws.domain.model.Campaign;

public interface CampaignService {

    List<Campaign> findCampaigns();

    Optional<Campaign> findById(String id);

    Campaign save(Campaign campaign);

    Optional<Campaign> updateCampaign(String id, Campaign campaign);
}
