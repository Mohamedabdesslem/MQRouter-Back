package com.bank.MQRouter.controller;

import com.bank.MQRouter.dto.PartnerCreateDTO;
import com.bank.MQRouter.exception.ValidationException;
import com.bank.MQRouter.model.Direction;
import com.bank.MQRouter.model.PartnerEntity;
import com.bank.MQRouter.model.ProcessedFlowType;
import com.bank.MQRouter.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/partners")
@CrossOrigin(origins = "http://localhost:4200")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @Operation(summary = "Ajouter un partenaire", description = "Ajoute un nouveau partenaire au système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Partenaire ajouté avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides pour le partenaire"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping("/addpartner")
    public ResponseEntity<?> createPartner(@Valid @RequestBody PartnerCreateDTO partnerDTO,  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Erreur : le formulaire contient des champs invalides. ");
            bindingResult.getFieldErrors().forEach(error ->
                    errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ")
            );
            throw new ValidationException(errorMessage.toString());
        }

        Direction.valueOf(partnerDTO.getDirection());
        ProcessedFlowType.valueOf(partnerDTO.getProcessedFlowType());

        PartnerEntity savedPartner = partnerService.createPartner(partnerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(partnerService.toDTO(savedPartner));
    }

    @Operation(summary = "Supprimer un partenaire", description = "Supprime un partenaire en fonction de son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partenaire supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Partenaire non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
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
    @Operation(summary = "Obtenir la liste des partenaires", description = "Récupère tous les partenaires avec pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des partenaires récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/listpartners")
    public ResponseEntity<Page<PartnerCreateDTO>> getPartners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PartnerCreateDTO> partners = partnerService.getPartners(pageable);

        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

}