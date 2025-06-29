package com.LMS.service;

import com.LMS.entity.Book;
import com.LMS.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class BookService {
    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private NotificationService notificationService;



    public ResponseEntity<?> updateBookStatus(String id, String status) {
        try {
            Optional<Book> optionalBook = bookRepo.findById(Long.valueOf(id));
            if (!optionalBook.isPresent()) {
                return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
            }
            Book book = optionalBook.get();
            String oldStatus = book.getAvailabilityStatus();
            book.setAvailabilityStatus(status);
            bookRepo.save(book);

            if ("Borrowed".equalsIgnoreCase(oldStatus) && "Available".equalsIgnoreCase(status)) {
                CompletableFuture.runAsync(() -> notificationService.notifyWishlistedUsers(book));
            }

            return new ResponseEntity<>("Status Updated", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the book status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> create(Book book) {
        try {
            if (bookRepo.existsByIsbn(book.getIsbn())) {
                return new ResponseEntity<>("ISBN already exists", HttpStatus.BAD_REQUEST);

            }
            bookRepo.save(book);
            return new ResponseEntity<>("Book is created successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while creating the book", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Page<Book> getBooks(String filter, Pageable pageable) {
        try {
            if (filter == null || filter.trim().isEmpty()) {
                return bookRepo.findAll(pageable); // Return all books if no filter is provided
            }
            return bookRepo.searchBooksByFilter(filter, pageable);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching the books");
        }
    }

    public Optional<Book> getById(Long id) {
        try {
            return bookRepo.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching the book by ID");
        }
    }

    public ResponseEntity<?>  delete(String id) {
        try {
            if (!bookRepo.existsById(Long.valueOf(id))) {
                return new ResponseEntity<>("Book does not exist", HttpStatus.BAD_REQUEST);
            }
            bookRepo.deleteById(Long.valueOf(id));
            return new ResponseEntity<>("Book Deleted Sucessfully", HttpStatus.OK);

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the book");
        }
    }

    public Book update(Long id, Book updated) {
        try {
            Book book = bookRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Book not found"));
            book.setTitle(updated.getTitle());
            book.setAuthor(updated.getAuthor());
            book.setIsbn(updated.getIsbn());
            book.setPublishedYear(updated.getPublishedYear());
            book.setAvailabilityStatus(updated.getAvailabilityStatus());
            return bookRepo.save(book);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the book");
        }
    }
}