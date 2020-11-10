package org.test.canal.dto;

import java.util.Date;
import java.util.Objects;

public class MovementDto {
    Long id;
    Date date;
    AddressDto addressDto;

    public MovementDto() {
    }

    public MovementDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    public MovementDto(Long id, Date date, AddressDto addressDto) {
        this.id = id;
        this.date = date;
        this.addressDto = addressDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovementDto that = (MovementDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(addressDto, that.addressDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, addressDto);
    }
}
