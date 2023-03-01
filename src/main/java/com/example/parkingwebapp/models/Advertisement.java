package com.example.parkingwebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="advertisement")
public class Advertisement extends BaseModel {

    private Date startDate;
    private Date endDate;
    private String numberPlace;
    private String registrationNumberCar;

    private String text;
    private boolean offer; //предложение
    private boolean approval; //согласие
    private boolean statusAdvertisement;

    @JsonIgnore
    @ManyToMany(fetch= FetchType.EAGER, mappedBy = "advertisements")
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Advertisement that = (Advertisement) o;
        return offer == that.offer && approval == that.approval && statusAdvertisement == that.statusAdvertisement && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(numberPlace, that.numberPlace) && Objects.equals(registrationNumberCar, that.registrationNumberCar) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startDate, endDate, numberPlace, registrationNumberCar, text, offer, approval, statusAdvertisement);
    }
}
