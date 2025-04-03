package com.bank.MQRouter.controller;

import com.bank.MQRouter.dto.PartnerCreateDTO;
import com.bank.MQRouter.model.Direction;
import com.bank.MQRouter.model.PartnerEntity;
import com.bank.MQRouter.model.ProcessedFlowType;
import com.bank.MQRouter.service.PartnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping("/addpartner")
    public ResponseEntity<PartnerCreateDTO> createPartner(@Valid @RequestBody PartnerCreateDTO partnerDTO) {
        validateEnums(partnerDTO);
        PartnerEntity savedPartner = partnerService.createPartner(partnerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(partnerService.toDTO(savedPartner));
    }

    @DeleteMapping("/deletepartner/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        boolean isDeleted = partnerService.deletePartner(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private void validateEnums(PartnerCreateDTO dto) {
        try {
            Direction.valueOf(dto.getDirection());  // Vérifie si la direction est valide
            ProcessedFlowType.valueOf(dto.getProcessedFlowType());  // Vérifie si le type est valide
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valeur invalide pour Direction ou ProcessedFlowType");
        }
    }

}