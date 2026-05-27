package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.CartRequest;
import com.mealgo.dto.response.CartResponse;
import com.mealgo.entity.Cart;
import com.mealgo.entity.Shop;
import com.mealgo.entity.User;
import com.mealgo.repository.ICartRepository;
import com.mealgo.repository.IShopRepository;
import com.mealgo.repository.IUserRepository;
import com.mealgo.service.ICartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final ICartRepository cartRepository;
    private final IUserRepository userRepository;
    private final IShopRepository shopRepository;

    @Override
    public List<CartResponse> findAll() {
        return cartRepository.findAll()
                .stream()
                .map(CartResponse::new)
                .toList();
    }

    @Override
    public CartResponse findById(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return new CartResponse(cart);
    }

    @Override
    public CartResponse create(CartRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        Cart cart = cartRepository.findByUserIdAndShopId(
                request.getUserId(),
                request.getShopId())
                .orElseGet(Cart::new);

        cart.setUser(user);
        cart.setShop(shop);

        return new CartResponse(cartRepository.save(cart));
    }

    @Override
    public CartResponse update(Integer id, CartRequest request) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        cart.setUser(user);
        cart.setShop(shop);

        return new CartResponse(cartRepository.save(cart));
    }

    @Override
    public void delete(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartRepository.delete(cart);
    }
}