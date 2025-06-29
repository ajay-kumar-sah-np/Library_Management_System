package com.LMS.controller;


import com.LMS.entity.Book;
import com.LMS.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/books")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookController {

    @Autowired
    private  BookService bookService;

//    @PostMapping("/create")
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody Book book) {
         return bookService.create(book);
    }

    @GetMapping
    public ResponseEntity<Page<Book>> list(
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookService.getBooks(filter, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.update(id, book));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestParam String status) {
            if (!isNumeric(id) || status == null || status.isEmpty()) {
                return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        }
        return bookService.updateBookStatus(id, status);
    }
    public boolean isNumeric(String id) {
        return id != null && id.matches("\\d+");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!isNumeric(id)) {
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        }
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
