package com.LMS.service;



import com.LMS.entity.Book;
import com.LMS.entity.Wishlist;
import com.LMS.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private  WishlistRepository wishlistRepo;

    @Async
    public void notifyWishlistedUsers(Book book) {
        try {
            List<Wishlist> wishlists = wishlistRepo.findByBookId(book.getId());
            for (Wishlist wishlist : wishlists) {
                System.out.println("Notification prepared for user_id: " + wishlist.getUser().getId() +
                        ": Book [" + book.getTitle() + "] is now available.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while notifying wishlisted users: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
