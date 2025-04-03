package com.bank.MQRouter.service;

import com.bank.MQRouter.dto.PartnerCreateDTO;
import com.bank.MQRouter.mapper.PartnerMapper;
import com.bank.MQRouter.model.PartnerEntity;
import com.bank.MQRouter.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class PartnerServiceTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private PartnerMapper partnerMapper;

    @InjectMocks
    private PartnerService partnerService;

    private PartnerCreateDTO partnerCreateDTO;
    private PartnerEntity partnerEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        partnerCreateDTO = new PartnerCreateDTO();
        partnerCreateDTO.setAlias("TestAlias");
        partnerCreateDTO.setType("TestType");
        partnerCreateDTO.setDirection("INBOUND");
        partnerCreateDTO.setApplication("TestApp");
        partnerCreateDTO.setProcessedFlowType("MESSAGE");
        partnerCreateDTO.setDescription("Test Description");

        partnerEntity = new PartnerEntity();
        partnerEntity.setId(1L);
        partnerEntity.setAlias("TestAlias");
        partnerEntity.setType("TestType");
        partnerEntity.setDirection(com.bank.MQRouter.model.Direction.INBOUND);
        partnerEntity.setApplication("TestApp");
        partnerEntity.setProcessedFlowType(com.bank.MQRouter.model.ProcessedFlowType.MESSAGE);
        partnerEntity.setDescription("Test Description");
    }

    @Test
    void createPartner_ShouldSaveAndReturnPartnerEntity() {
        when(partnerMapper.toEntity(partnerCreateDTO)).thenReturn(partnerEntity);
        when(partnerRepository.save(partnerEntity)).thenReturn(partnerEntity);

        PartnerEntity savedPartner = partnerService.createPartner(partnerCreateDTO);

        assertNotNull(savedPartner);
        assertEquals("TestAlias", savedPartner.getAlias());
        assertEquals("TestType", savedPartner.getType());
        assertEquals(com.bank.MQRouter.model.Direction.INBOUND, savedPartner.getDirection());

        verify(partnerMapper, times(1)).toEntity(partnerCreateDTO);
        verify(partnerRepository, times(1)).save(partnerEntity);
    }
}
