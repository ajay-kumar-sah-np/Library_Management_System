package com.LMS.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
//@Builder
@Table(name = "wishlist",  schema = "libarary_management_system")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name="book_id",referencedColumnName = "id")
    private Book book;
}
