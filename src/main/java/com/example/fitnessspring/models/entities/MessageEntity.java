package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "message")
public class MessageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "content", nullable = false, length = 255)
    private String content;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    private Boolean isRead;
    @ManyToOne
    @JoinColumn(name = "sender_user_id", referencedColumnName = "id", nullable = false)
    private UserEntity senderId;
    @ManyToOne
    @JoinColumn(name = "reciever_user_id", referencedColumnName = "id", nullable = false)
    private UserEntity recieverId;

    @Basic
    @Column(name = "is_read", nullable = true)
    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

}
