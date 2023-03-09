package com.example.parkingwebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="place")
public class Place extends BaseModel{
    private String numberPlace;
    private boolean isEmpty;
    private boolean statusPlace;
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Place place = (Place) o;
        return isEmpty == place.isEmpty && statusPlace == place.statusPlace && Objects.equals(numberPlace, place.numberPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberPlace);
    }
}
