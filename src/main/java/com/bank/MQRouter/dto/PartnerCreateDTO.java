package com.bank.MQRouter.dto;

import com.bank.MQRouter.model.Direction;
import com.bank.MQRouter.model.ProcessedFlowType;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PartnerCreateDTO {

    @NotBlank(message = "Alias is required")
    private String alias;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Direction is required")
    private String  direction;

    private String application;

    @NotNull(message = "Processed Flow Type is required")
    private String processedFlowType;

    @NotBlank(message = "Description is required")
    private String description;
}
