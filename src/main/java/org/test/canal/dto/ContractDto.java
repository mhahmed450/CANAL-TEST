package org.test.canal.dto;



import java.io.Serializable;

public class ContractDto implements Serializable {
    Long id;
    String name;
    AddressDto address;

    public ContractDto() {
    }

    public ContractDto(Long id, String name, AddressDto address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
