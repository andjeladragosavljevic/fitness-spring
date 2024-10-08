package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Basic
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    @Basic
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Basic
    @Column(name = "avatar", nullable = true, length = 255)
    private String avatar;
    @Basic
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Basic
    @Column(name = "is_active", nullable = true)
    private boolean isActive;
    @Basic
    @Column(name="activation_code", length = 36)
    private String activationCode;
    @OneToMany(mappedBy = "user")
    private List<ActivitylogEntity> activitylogs;
    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments;
    @OneToMany(mappedBy = "user")
    private List<FitnessProgramEntity> fitnessprograms;
    @OneToMany(mappedBy = "sender")
    private List<MessageEntity> sentMessages;
    @OneToMany(mappedBy = "receiver")
    private List<MessageEntity> receivedMessages;
    @OneToMany(mappedBy = "user")
    private List<MessagetoadvisorEntity> messagetoadvisors;
    @OneToMany(mappedBy = "user")
    private List<ParticipationEntity> participations;

}
