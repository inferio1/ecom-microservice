package com.ecommerce.order.service;


import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService {

    private final CartItemRepository cartItemRepository;


    public boolean addToCart(String userId, CartItemRequest cartItemRequest)
    {
//      Optional<Product> productOpt= productRepository.findById(cartItemRequest.getProductId());
//      if(productOpt.isEmpty())
//      {
//          return false;
//      }
//      Product product=productOpt.get();
//      if(product.getStockQuantity()<cartItemRequest.getQuantity())
//          return false;
//
//      Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));
//      if(userOpt.isEmpty())
//      {
//          return false;
//      }
//      User user =userOpt.get();

      CartItem existingCartItem=cartItemRepository.findByUserIdAndProductId(userId,cartItemRequest.getProductId());
      if(existingCartItem!=null)
      {
          existingCartItem.setQuantity(cartItemRequest.getQuantity()+existingCartItem.getQuantity());
          existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
          cartItemRepository.save(existingCartItem);
      }
      else
      {
          CartItem cartItem=new CartItem();
          cartItem.setUserId(userId);
          cartItem.setProductId(cartItemRequest.getProductId());
          cartItem.setQuantity(cartItemRequest.getQuantity());
          cartItem.setPrice(BigDecimal.valueOf(1000.00));
          cartItemRepository.save(cartItem);
      }
      return true;
    }

    public boolean deleteItemFromCart(String userId,String productId)
    {
        CartItem cartItem=cartItemRepository.findByUserIdAndProductId(userId,productId);
        if(cartItem!=null)
        {
            cartItemRepository.delete(cartItem);
            return true;
        }

//        userOpt.flatMap(user->productOpt.map(product->{cartItemRepository.deleteByUserAndProduct(user,product);
//        return true;}));
        return false;
    }

    public List<CartItem> getCart(String userId)
    {
        return cartItemRepository.findByUserId(userId);
//       Optional<User> userOpt= userRepository.findById(Long.valueOf(userId));
//       if(userOpt.isEmpty())
//       {
//           return null;
//       }
//       User user=userOpt.get();
//      List<CartItem> cartItems= cartItemRepository.findByUserId(user.getId());
//      return cartItems;
    }

    public void clearCart(String userId)
    {
        cartItemRepository.deleteByUserId(userId);
    }
}
