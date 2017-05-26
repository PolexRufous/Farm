package com.farm.processes;

import com.farm.database.entities.personality.Partner;
import com.farm.database.entities.personality.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerProcess {
    private PartnerRepository partnerRepository;
    private AddressProcess addressProcess;

    @Autowired
    public PartnerProcess(PartnerRepository partnerRepository, AddressProcess addressProcess) {
        this.partnerRepository = partnerRepository;
        this.addressProcess = addressProcess;
    }

    public Partner save(Partner partner) {
        return partnerRepository.save(partner);
    }

    public Partner findById(long id) {
        return partnerRepository.findOne(id);
    }

    public Partner update(Partner partner) {
        Partner oldPartner = partnerRepository.findOne(partner.getId());
        oldPartner.update(partner);
        oldPartner.getAddresses().forEach(addressProcess::update);
        return partnerRepository.save(oldPartner);
    }

    public List<Partner> findAll() {
        return partnerRepository.findAll();
    }

    public void delete(Long id) {
        partnerRepository.delete(id);
    }
}
