package com.farm.database.processes;

import com.farm.database.entities.personality.Partner;
import com.farm.database.entities.personality.PartnerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PartnerProcess {

    @Resource
    private PartnerRepository partnerRepository;

    public Partner save(Partner partner){
        return partnerRepository.save(partner);
    }

    public Partner findById(long id){
        return partnerRepository.findOne(id);
    }

    public Partner findByName(String name){
        return partnerRepository.findByName(name);
    }

    public Partner update(Partner partner){
        Partner oldPartner = partnerRepository.findOne(partner.getId());
        oldPartner.setName(partner.getName());
        oldPartner.setDescription(partner.getDescription());
        return oldPartner;
    }

    public List<Partner> findAll() {
        return partnerRepository.findAll();
    }


}
