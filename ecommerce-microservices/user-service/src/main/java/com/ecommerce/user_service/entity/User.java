package com.ecommerce.user_service.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String phone;
    private String roles;
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt=Instant.now();
        updatedAt=createdAt;

    }
    @PreUpdate
    public void preUpdate(){
        updatedAt=Instant.now();
    }

}
