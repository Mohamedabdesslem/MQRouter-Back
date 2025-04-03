package com.bank.MQRouter.controller;

import com.bank.MQRouter.dto.PartnerCreateDTO;
import com.bank.MQRouter.model.Direction;
import com.bank.MQRouter.model.PartnerEntity;
import com.bank.MQRouter.model.ProcessedFlowType;
import com.bank.MQRouter.service.PartnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> createPartner(@Valid @RequestBody PartnerCreateDTO partnerDTO,  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            bindingResult.getFieldErrors().forEach(error ->
                    errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ")
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }

        // Validate enums manually (Direction, ProcessedFlowType)
        try {
            Direction.valueOf(partnerDTO.getDirection());
            ProcessedFlowType.valueOf(partnerDTO.getProcessedFlowType());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( "Invalid value for Direction or ProcessedFlowType");
        }

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

    // Méthode pour récupérer la liste des partenaires avec pagination
    @GetMapping("/listpartners")
    public ResponseEntity<Page<PartnerCreateDTO>> getPartners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PartnerCreateDTO> partners = partnerService.getPartners(pageable);

        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

}