package com.farm.processes;

import com.farm.database.personality.Partner;
import com.farm.database.personality.PartnerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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


}
