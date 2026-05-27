package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.CartItemRequest;
import com.mealgo.dto.response.CartItemResponse;
import com.mealgo.entity.Cart;
import com.mealgo.entity.CartItem;
import com.mealgo.entity.Product;
import com.mealgo.repository.ICartItemRepository;
import com.mealgo.repository.ICartRepository;
import com.mealgo.repository.IProductRepository;
import com.mealgo.service.ICartItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final ICartItemRepository cartItemRepository;
    private final ICartRepository cartRepository;
    private final IProductRepository productRepository;

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
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        return new CartItemResponse(cartItem);
    }

    @Override
    public CartItemResponse create(CartItemRequest request) {
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();

        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQty(request.getQty());
        cartItem.setRemark(request.getRemark());

        return new CartItemResponse(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemResponse update(Integer id, CartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQty(request.getQty());
        cartItem.setRemark(request.getRemark());

        return new CartItemResponse(cartItemRepository.save(cartItem));
    }

    @Override
    public void delete(Integer id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(cartItem);
    }
}