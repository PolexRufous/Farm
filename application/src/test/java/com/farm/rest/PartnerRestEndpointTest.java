package com.farm.rest;

import com.farm.database.entities.personality.Partner;
import com.farm.database.entities.personality.PartnerRepository;
import com.farm.database.processes.PartnerProcess;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PartnerRestEndpointTest {

    private PartnerRepository partnerRepository;
    private PartnersRestEndpoint partnersRestEndpoint;

    @Before
    public void setUp() {
        partnerRepository = Mockito.mock(PartnerRepository.class);
        PartnerProcess partnerProcess = new PartnerProcess(partnerRepository);
        partnersRestEndpoint = new PartnersRestEndpoint(partnerProcess);
    }

    @Test
    public void testSaveCorrect() {
        //given
        Partner partner = getDefaultPartner();
        Partner resultPartner = getDefaultPartner();
        resultPartner.setId(1L);
        when(partnerRepository.save(any(Partner.class))).thenReturn(resultPartner);

        //when
        ResponseEntity responseEntity = partnersRestEndpoint.save(partner);
        Partner responsePartner = (Partner) responseEntity.getBody();

        //then
        assertEquals(Long.valueOf(1L), responsePartner.getId());
    }

    private Partner getDefaultPartner() {
        Partner partner = new Partner();
        partner.setName("name");
        partner.setDescription("description");
        return partner;
    }
}
