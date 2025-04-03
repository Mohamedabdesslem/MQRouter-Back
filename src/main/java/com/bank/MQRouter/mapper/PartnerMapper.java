package com.bank.MQRouter.mapper;

import com.bank.MQRouter.model.Direction;
import com.bank.MQRouter.model.PartnerEntity;
import com.bank.MQRouter.dto.PartnerCreateDTO;
import com.bank.MQRouter.model.ProcessedFlowType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PartnerMapper {

    // Mapping PartnerCreateDTO -> PartnerEntity
    @Mapping(target = "direction", source = "direction", qualifiedByName = "stringToDirection")
    @Mapping(target = "processedFlowType", source = "processedFlowType", qualifiedByName = "stringToProcessedFlowType")
    PartnerEntity toEntity(PartnerCreateDTO dto);

    // Mapping PartnerEntity -> PartnerDTO
    @Mapping(target = "direction", source = "direction", qualifiedByName = "directionToString")
    @Mapping(target = "processedFlowType", source = "processedFlowType", qualifiedByName = "processedFlowTypeToString")
    PartnerCreateDTO toDto(PartnerEntity entity);

    @Named("stringToDirection")
    default Direction stringToDirection(String direction) {
        return Direction.valueOf(direction.toUpperCase());
    }

    @Named("stringToProcessedFlowType")
    default ProcessedFlowType stringToProcessedFlowType(String type) {
        return ProcessedFlowType.valueOf(type.toUpperCase());
    }

    @Named("directionToString")
    default String directionToString(Direction direction) {
        return direction.name();
    }

    @Named("processedFlowTypeToString")
    default String processedFlowTypeToString(ProcessedFlowType processedFlowType) {
        return processedFlowType.name();
    }
}
