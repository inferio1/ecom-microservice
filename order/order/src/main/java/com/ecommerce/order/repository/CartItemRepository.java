package com.ecommerce.order.repository;


import com.ecommerce.order.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByUserIdAndProductId(String userId, String productId);
    long deleteByUserIdAndProductId(String userId,String productId);
    List<CartItem> findByUserId(String userId);
//    List<CartItem> findByUserId(Long id);
    void deleteByUserId(String userId);
}
