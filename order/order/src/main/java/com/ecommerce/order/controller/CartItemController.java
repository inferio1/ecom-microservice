package com.ecommerce.order.controller;


import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequest cartItemRequest)
    {
        if(!cartItemService.addToCart(userId,cartItemRequest))
        {
            return ResponseEntity.badRequest().body("Product out of stock or user not found or product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> deleteItemFromCart(@RequestHeader("X-User-ID") String userId, @PathVariable String productId)
    {
        boolean deleted= cartItemService.deleteItemFromCart(userId,productId);
        return deleted?ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-User-ID") String userId)
    {
        return ResponseEntity.ok(cartItemService.getCart(userId));
//        List<CartItem> list=cartItemService.getCart(userId);
//        return ResponseEntity.ok(list);
    }
}
