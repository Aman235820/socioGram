package com.aman.socialMedia.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails , Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Username", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private int age;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Posts> posts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER)                      //The EAGER strategy is a requirement on the persistence provider runtime that data must be eagerly fetched. The FetchType.LAZY strategy is a hint to the persistence provider runtime that data should be fetched lazily when it is first accessed.
    @JoinTable(name = "user_role",
       joinColumns = {
            @JoinColumn(name = "userId" , referencedColumnName = "id")
       },
       inverseJoinColumns = {
            @JoinColumn(name = "roleId" , referencedColumnName = "roleId")
       }

    )
    private Set<Role> roles = new HashSet<>();


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
          List<SimpleGrantedAuthority> authorities = this.roles.stream().map((role)-> new SimpleGrantedAuthority(role.getRoleName())).toList();
          return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
