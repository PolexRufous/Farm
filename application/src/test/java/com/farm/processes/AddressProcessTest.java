package com.farm.processes;

import com.farm.database.entities.address.Address;
import com.farm.database.entities.address.AddressRepository;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class AddressProcessTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressProcess addressProcess;

    @Captor
    private ArgumentCaptor<Address> addressArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Parameters
    @TestCaseName("should just delegate saving to repository ({0})")
    public void save(Address savedAddress) throws Exception {
        //given
        when(addressRepository.save(addressArgumentCaptor.capture())).thenReturn(savedAddress);

        //when
        Address result = addressProcess.save(savedAddress);

        //then
        if (Objects.nonNull(savedAddress)) {
            verify(addressRepository).save(savedAddress);
            assertEquals(savedAddress, addressArgumentCaptor.getValue());
        }
        assertEquals(savedAddress, result);
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    @Parameters
    @TestCaseName("should just delegate finding all")
    public void getAll(List<Address> addressList) throws Exception {
        //given
        when(addressRepository.findAll()).thenReturn(addressList);

        //when
        List<Address> returnedAddressesList = addressProcess.getAll();

        //then
        assertEquals(addressList, returnedAddressesList);
        assertTrue(CollectionUtils.isEqualCollection(addressList, returnedAddressesList));
        verify(addressRepository, only()).findAll();
    }

    @Test
    @Parameters
    @TestCaseName("should correct update ({0})")
    public void update(Address enterAddress, Address savedAddress) throws Exception {
        //given
        if (Objects.nonNull(enterAddress) && Objects.nonNull(enterAddress.getId())) {
            when(addressRepository.findOne(eq(enterAddress.getId()))).thenReturn(savedAddress);
        }
        when(addressRepository.save(addressArgumentCaptor.capture())).thenReturn(new Address());

        //when
        Address resultAddress = addressProcess.update(enterAddress);

        //then
        if (Objects.nonNull(enterAddress)) {
            if (Objects.nonNull(enterAddress.getId())) {
                verify(addressRepository).findOne(enterAddress.getId());
            }
            verify(addressRepository).save(any(Address.class));
            assertEquals(enterAddress.getTown(), addressArgumentCaptor.getValue().getTown());
        } else {
            assertNull(resultAddress);
        }

        verifyNoMoreInteractions(addressRepository);

    }

    @Test
    @Parameters
    @TestCaseName("should just delegate deleting ({0})")
    public void delete(Long id) throws Exception {
        //given
        doNothing().when(addressRepository).delete(longArgumentCaptor.capture());

        //when
        addressProcess.delete(id);

        //then
        if (Objects.nonNull(id)) {
            assertEquals(id, longArgumentCaptor.getValue());
            verify(addressRepository, only()).delete(id);
        } else {
            verifyNoMoreInteractions(addressRepository);
        }
    }

    @Test
    @Parameters
    @TestCaseName("should just delegate finding one ({0})")
    public void getOne(Long id, Address savedAddress) throws Exception {
        //given
        when(addressRepository.findOne(longArgumentCaptor.capture())).thenReturn(savedAddress);

        //when
        Address foundAddress = addressProcess.getOne(id);

        //then
        if (Objects.nonNull(id)) {
            assertEquals(id, longArgumentCaptor.getValue());
            verify(addressRepository, only()).findOne(id);
            assertEquals(savedAddress, foundAddress);
        } else {
            assertNull(foundAddress);
            verifyNoMoreInteractions(addressRepository);
        }
    }

    private Object[] parametersForSave() {
        return new Object[]{
                new Address().setId(1L).setTown("Kharkov"),
                null
        };
    }

    private Object[] parametersForGetAll() {
        return new Object[]{
                Arrays.asList(
                        new Address().setId(1L).setTown("Kharkov"),
                        new Address().setId(2L).setTown("Kiev")),
                Collections.emptyList()
        };
    }

    private Object[] parametersForUpdate() {
        return new Object[]{
                new Object[]{
                        new Address().setId(1L).setTown("Kharkov"),
                        new Address().setId(1L).setTown("Kiev")
                },
                new Object[]{
                        new Address().setTown("Kharkov"),
                        null
                },
                new Object[]{
                        null,
                        new Address().setId(2L).setTown("Kiev")
                },
                new Object[]{
                        null,
                        null
                },
                new Object[]{
                        new Address().setId(1L).setTown("Kharkov"),
                        null
                },
        };
    }

    private Object[] parametersForDelete() {
        return new Object[]{
                1L,
                null
        };
    }

    private Object[] parametersForGetOne() {
        return new Object[]{
                new Object[]{
                        1L,
                        null
                },
                new Object[]{
                        1L,
                        new Address().setId(1L).setTown("Kharkov")
                },
                new Object[]{
                        null,
                        null
                },

        };
    }

}