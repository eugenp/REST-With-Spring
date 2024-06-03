package com.baeldung.rwsb.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.baeldung.rwsb.domain.model.Campaign;
import com.baeldung.rwsb.persistence.repository.CampaignRepository;
import com.baeldung.rwsb.service.CampaignService;

@Service
public class DefaultCampaignService implements CampaignService {

    private CampaignRepository campaignRepository;

    public DefaultCampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public Optional<Campaign> findById(String id) {
        return campaignRepository.findById(UUID.fromString(id));
    }

    @Override
    public Campaign save(Campaign campaign) {
        campaign.setId((String) null);
        return campaignRepository.save(campaign);
    }

    @Override
    public List<Campaign> findCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
    public Optional<Campaign> updateCampaign(String id, Campaign campaign) {
        return campaignRepository.findById(UUID.fromString(id))
            .map(base -> updateFields(base, campaign))
            .map(campaignRepository::save);
    }

    private Campaign updateFields(Campaign base, Campaign updatedCampaign) {
        base.setName(updatedCampaign.getName());
        base.setDescription(updatedCampaign.getDescription());
        return base;
    }
}
