package com.aman.socialMedia.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements Serializable {

        @Id
        private Integer roleId;

        private String roleName;

        @ManyToMany(mappedBy = "roles" , fetch = FetchType.EAGER)
        private Set<User> users;
}
