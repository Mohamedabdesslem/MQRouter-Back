package com.bank.MQRouter.dto;

import com.bank.MQRouter.model.Direction;
import com.bank.MQRouter.model.ProcessedFlowType;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PartnerCreateDTO {

    private Long id;

    @NotBlank(message = "Le champ Alias est requis")
    private String alias;

    @NotBlank(message = "Le champ type est requis")
    private String type;

    @NotNull(message = "Le champ direction est requis")
    private String  direction;

    private String application;

    @NotNull(message = "Le champ Processed Flow Type est requis")
    private String processedFlowType;

    @NotBlank(message = "Le champ description est requis")
    private String description;
}
