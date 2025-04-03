package com.bank.MQRouter.service;

import com.bank.MQRouter.dto.PartnerCreateDTO;
import com.bank.MQRouter.model.PartnerEntity;
import com.bank.MQRouter.repository.PartnerRepository;
import com.bank.MQRouter.mapper.PartnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, PartnerMapper partnerMapper) {
        this.partnerRepository = partnerRepository;
        this.partnerMapper = partnerMapper;
    }

    public PartnerEntity createPartner(PartnerCreateDTO partnerCreateDTO) {
        PartnerEntity partnerEntity = partnerMapper.toEntity(partnerCreateDTO);
        return partnerRepository.save(partnerEntity);
    }

    public boolean deletePartner(Long id) {
        if (partnerRepository.existsById(id)) {
            partnerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PartnerCreateDTO toDTO(PartnerEntity partnerEntity) {
        return partnerMapper.toDto(partnerEntity);
    }

}
