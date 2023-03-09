package com.example.parkingwebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class User  extends BaseModel{

    private String firstName;
    private String lastName;
    private String username;
    private String password;

    //@JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonIgnore
    @Column(name = "is_status_user", columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean statusUser;

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_advertisements",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id")
    )
    private Set<Advertisement> advertisements = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Place> places = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Car> cars = new ArrayList<>();

    public void addPlace(Place place) {
        places.add(place);
        place.setUser(this);
    }

    public void removePlace(Place place) {
//        ArrayList<Place> placeList = new ArrayList<>(places);
//        for (Place place1 : placeList) {
//            if (place1.equals(place)) {
//                places.remove(place);
//            }
//        }
        places.remove(place);
        place.setStatusPlace(false);
    }

    public void addCar(Car car) {
        cars.add(car);
        car.setUser(this);
    }

    public void removeCar(Car car) {
//        ArrayList<Car> carList = new ArrayList<>(cars);
//        for (Car car1 : carList) {
//            if (car1.equals(car)) {
//                cars.remove(car);
//            }
//        }
        cars.remove(car);
        car.setStatusCar(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, username);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", Status=" + statusUser +
                ", roles=" + roles +
                '}';
    }
}
