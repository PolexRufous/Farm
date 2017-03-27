package com.farm.processes;

import com.farm.database.entities.personality.PartnerEntity;
import com.farm.database.entities.personality.PartnerEntity;
import com.farm.database.entities.personality.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerProcess {
    private PartnerRepository partnerRepository;

    @Autowired
    public PartnerProcess(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public PartnerEntity save(PartnerEntity partnerEntity){
        return partnerRepository.save(partnerEntity);
    }

    public PartnerEntity findById(long id){
        return partnerRepository.findOne(id);
    }

    public PartnerEntity findByName(String name){
        return partnerRepository.findByName(name);
    }

    public PartnerEntity update(PartnerEntity partnerEntity){
        PartnerEntity oldPartnerEntity = partnerRepository.findOne(partnerEntity.getId());
        oldPartnerEntity.update(partnerEntity);
        return partnerRepository.save(oldPartnerEntity);
    }

    public List<PartnerEntity> findAll() {
        return partnerRepository.findAll();
    }

    public void delete(Long id) {
        partnerRepository.delete(id);
    }
}
