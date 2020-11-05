package org.test.canal.dto.responses;

import org.test.canal.dto.ContractDto;

import java.util.List;

public class ContractsSubscriberResponseDto {
    Long idSubscriber;
    List<ContractDto> contractDtos;

    public ContractsSubscriberResponseDto() {
    }

    public ContractsSubscriberResponseDto(Long idSubscriber, List<ContractDto> contractDtos) {
        this.idSubscriber = idSubscriber;
        this.contractDtos = contractDtos;
    }

    public Long getIdSubscriber() {
        return idSubscriber;
    }

    public void setIdSubscriber(Long idSubscriber) {
        this.idSubscriber = idSubscriber;
    }

    public List<ContractDto> getContractDtos() {
        return contractDtos;
    }

    public void setContractDtos(List<ContractDto> contractDtos) {
        this.contractDtos = contractDtos;
    }
}
