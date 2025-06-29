package com.LMS.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data

//@Builder
@Table(name = "users",  schema = "libarary_management_system")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
}
