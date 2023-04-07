package com.ecommerce.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Size(min =3, max = 15, message = "First name has 3-15 characters at least")
    private String firstName;
    @Size(min =3, max = 15, message = "First name has 3-15 characters at least")
    private String lastName;
    private String username;
    private String phoneNumber;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;
}
