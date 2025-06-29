package com.LMS.repository;



import com.LMS.entity.Book;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
    @Query("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(b.title) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Book> searchBooksByFilter(@Param("filter") String filter, Pageable pageable);
    Page<Book> findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(
            String author, String title, Pageable pageable
    );
}