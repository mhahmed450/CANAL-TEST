package org.test.canal.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.test.canal.utils.Status;

import java.io.Serializable;
import java.util.Objects;

public class AddressDto implements Serializable {
    Long id ;
    String name ;
    Status status;


    public AddressDto() {
    }

    public AddressDto(String name, Status status) {
        this.name = name;
        this.status = status;
    }
    @JsonCreator
    public AddressDto(@JsonProperty("id")Long id,@JsonProperty("name") String name,@JsonProperty("status") Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
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
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void switchStatus(){
    switch (status) {
        case ACTIVE: this.status=Status.INACTIVE; break ;
        case INACTIVE:this.status=Status.ACTIVE; break ;
    }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDto that = (AddressDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }
}
