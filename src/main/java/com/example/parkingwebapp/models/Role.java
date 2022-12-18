package com.example.parkingwebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="role")
public class Role extends BaseModel implements GrantedAuthority {

    @NonNull @Column(unique = true)
    private String name;
    @JsonIgnore
    @ManyToMany(fetch= FetchType.EAGER, mappedBy = "roles")
    private List<User> users = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(name = "role_privileges",
//            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
//    private List<Privilege> privileges;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

//    @Override
//    public String toString() {
//        return "Role{" +
//                "name='" + name + '\'' +
//                ", users=" + users +
//                ", privileges=" + privileges +
//                '}';
//    }


    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", users=" + users +
                '}';
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
