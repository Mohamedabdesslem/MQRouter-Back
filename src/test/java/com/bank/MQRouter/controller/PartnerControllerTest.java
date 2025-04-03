package com.bank.MQRouter.controller;

import com.bank.MQRouter.dto.PartnerCreateDTO;
import com.bank.MQRouter.model.Direction;
import com.bank.MQRouter.model.PartnerEntity;
import com.bank.MQRouter.model.ProcessedFlowType;
import com.bank.MQRouter.service.PartnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PartnerController.class)
class PartnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartnerService partnerService;

    @Test
    void testCreatePartner() throws Exception {

        PartnerEntity savedPartner = new PartnerEntity();
        savedPartner.setId(1L);
        savedPartner.setAlias("Partner1");
        savedPartner.setType("Type1");
        savedPartner.setDirection(Direction.INBOUND);
        savedPartner.setProcessedFlowType(ProcessedFlowType.MESSAGE);
        savedPartner.setDescription("Description of partner");

        // Mock des services
        when(partnerService.createPartner(any(PartnerCreateDTO.class))).thenReturn(savedPartner);


        // Act et Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/partners/addpartner")
                        .contentType("application/json")
                        .content("{\"alias\": \"Partner1\", \"type\": \"Type1\", \"direction\": \"INBOUND\", \"processedFlowType\": \"MESSAGE\", \"description\": \"Description of partner\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreatePartner_InvalidEnums() throws Exception {
        // Arrange : Création d'un PartnerCreateDTO avec des valeurs invalides
        PartnerCreateDTO partnerCreateDTO = new PartnerCreateDTO();
        partnerCreateDTO.setAlias("Partner1");
        partnerCreateDTO.setType("Type1");
        partnerCreateDTO.setDirection("INVALID_DIRECTION"); // Valeur invalide pour Direction
        partnerCreateDTO.setProcessedFlowType("INVALID_TYPE"); // Valeur invalide pour ProcessedFlowType
        partnerCreateDTO.setDescription("Description of partner");

        // Act et Assert : Vérification que la validation échoue et retourne le code HTTP 400
        mockMvc.perform(MockMvcRequestBuilders.post("/api/partners/addpartner")
                        .contentType("application/json")
                        .content("{\"alias\": \"Partner1\", \"type\": \"Type1\", \"direction\": \"INVALID_DIRECTION\", \"processedFlowType\": \"INVALID_TYPE\", \"description\": \"Description of partner\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeletePartner_Success() throws Exception {
        // Arrange
        Long partnerId = 1L;
        when(partnerService.deletePartner(partnerId)).thenReturn(true);

        // Act et Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/partners/deletepartner/{id}", partnerId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(partnerService, times(1)).deletePartner(partnerId);
    }

    @Test
    void testDeletePartner_NotFound() throws Exception {
        // Arrange
        Long partnerId = 1L;
        when(partnerService.deletePartner(partnerId)).thenReturn(false);

        // Act et Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/partners/deletepartner/{id}", partnerId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(partnerService, times(1)).deletePartner(partnerId);
    }

    @Test
    void testGetPartners() throws Exception {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        List<PartnerCreateDTO> partnerList = IntStream.range(0, size)
                .mapToObj(i -> new PartnerCreateDTO())
                .collect(Collectors.toList());

        Page<PartnerCreateDTO> partnerPage = new PageImpl<>(partnerList, pageable, partnerList.size());

        when(partnerService.getPartners(pageable)).thenReturn(partnerPage);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/partners/listpartners")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(size));

        verify(partnerService, times(1)).getPartners(pageable);
    }
}