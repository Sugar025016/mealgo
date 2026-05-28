package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mealgo.dto.request.CartItemRequest;
import com.mealgo.dto.response.CartItemResponse;
import com.mealgo.entity.Cart;
import com.mealgo.entity.CartItem;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.ICartItemRepository;
import com.mealgo.repository.ICartRepository;
import com.mealgo.service.ICartItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final ICartItemRepository cartItemRepository;
    private final ICartRepository cartRepository;

    @Override
    public List<CartItemResponse> findAll() {
        return cartItemRepository.findAll()
                .stream()
                .map(CartItemResponse::new)
                .toList();
    }

    @Override
    public List<CartItemResponse> findByCartId(Integer cartId) {
        return cartItemRepository.findByCartId(cartId)
                .stream()
                .map(CartItemResponse::new)
                .toList();
    }

    @Override
    public CartItemResponse findById(Integer id) {

        CartItem cartItem = getCartItem(id);

        return new CartItemResponse(cartItem);
    }

    @Override
    public CartItemResponse update(Integer id, CartItemRequest request) {

        CartItem cartItem = getCartItem(id);

        cartItem.setQty(request.getQty());
        cartItem.setRemark(request.getRemark());

        return new CartItemResponse(
                cartItemRepository.save(cartItem));
    }

    @Override
    @Transactional
    public void delete(Integer id) {

        CartItem cartItem = getCartItem(id);
        Cart cart = cartItem.getCart();
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            cartRepository.delete(cart);
        }
    }

    private CartItem getCartItem(Integer id) {

        return cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("購物車商品"));
    }
}