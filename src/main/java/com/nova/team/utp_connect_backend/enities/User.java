package com.nova.team.utp_connect_backend.enities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    //@Column(nullable = false)
    private String faculty;

    //@Column(nullable = false)
    private int cycle;

    //@Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String phone;
    private String profilePicture;
    private String urlWebsite;
    private String presentation;
    private List<String> skills;
    private List<String> interests;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Embedded
    private Audit audit = new Audit();

}
