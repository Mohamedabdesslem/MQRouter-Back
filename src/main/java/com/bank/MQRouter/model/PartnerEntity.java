package com.bank.MQRouter.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "partners")
@Data
public class PartnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Alias is required")
    @Column(name = "alias", nullable = false)
    private String alias;

    @NotBlank(message = "Type is required")
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull(message = "Direction is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false)
    private Direction direction;

    @Column(name = "application")
    private String application;

    @Enumerated(EnumType.STRING)
    @Column(name = "processed_flow_type")
    private ProcessedFlowType processedFlowType;

    @NotBlank(message = "Description is required")
    @Column(name = "description", nullable = false)
    private String description;
}
