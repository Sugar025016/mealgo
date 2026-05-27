package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.CartRequest;
import com.mealgo.dto.response.CartResponse;
import com.mealgo.entity.Cart;
import com.mealgo.entity.Shop;
import com.mealgo.entity.User;
import com.mealgo.exception.ResourceNotFoundException;
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

                Cart cart = getCart(id);

                return new CartResponse(cart);
        }

        @Override
        public CartResponse create(CartRequest request) {

                User user = getUser(request.getUserId());
                Shop shop = getShop(request.getShopId());

                Cart cart = cartRepository.findByUserIdAndShopId(
                                request.getUserId(),
                                request.getShopId())
                                .orElseGet(Cart::new);

                cart.setUser(user);
                cart.setShop(shop);

                return new CartResponse(
                                cartRepository.save(cart));
        }

        @Override
        public CartResponse update(Integer id, CartRequest request) {

                Cart cart = getCart(id);

                User user = getUser(request.getUserId());
                Shop shop = getShop(request.getShopId());

                cart.setUser(user);
                cart.setShop(shop);

                return new CartResponse(
                                cartRepository.save(cart));
        }

        @Override
        public void delete(Integer id) {

                Cart cart = getCart(id);

                cartRepository.delete(cart);
        }

        private Cart getCart(Integer id) {

                return cartRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("購物車"));
        }

        private User getUser(Integer id) {

                return userRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("使用者"));
        }

        private Shop getShop(Integer id) {

                return shopRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("店家"));
        }
}