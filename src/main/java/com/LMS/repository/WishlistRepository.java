package com.LMS.repository;



        import com.LMS.entity.Wishlist;
        import org.springframework.data.jpa.repository.JpaRepository;

        import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByBookId(Long bookId);
}
