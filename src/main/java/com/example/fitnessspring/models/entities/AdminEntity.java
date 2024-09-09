package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "admin")
public class AdminEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name = "role", nullable = true)
    private Role role;
    @Basic
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Basic
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @OneToMany(mappedBy = "admin")
    private List<MessagetoadvisorEntity> messagetoadvisors;

    public enum Role {
        Admin, Advisor
    }

}
