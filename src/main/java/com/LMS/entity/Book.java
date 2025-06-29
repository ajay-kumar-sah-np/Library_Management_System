package com.LMS.entity;



import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "books",  schema = "libarary_management_system")
@Data
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;

    @Column(name = "isbn",unique = true)
    @NotEmpty(message = "isbn is mandatory")
    private String isbn;
    @Min(value = 1500, message = "Published year must not be earlier than 1500.")
    @Max(value = 2100, message = "Published year must not be later than 2025.")
    @Column(name = "published_year")
    private Integer publishedYear;
    @Column(name = "availability_status")
    private String availabilityStatus;
    @Column(name = "deleted")
    private boolean deleted = false;

}

