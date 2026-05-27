package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.CartItemRequest;
import com.mealgo.dto.response.CartItemResponse;
import com.mealgo.entity.Cart;
import com.mealgo.entity.CartItem;
import com.mealgo.entity.Product;
import com.mealgo.exception.ResourceNotFoundException;
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

                CartItem cartItem = getCartItem(id);

                return new CartItemResponse(cartItem);
        }

        @Override
        public CartItemResponse create(CartItemRequest request) {

                Cart cart = getCart(request.getCartId());
                Product product = getProduct(request.getProductId());

                CartItem cartItem = new CartItem();

                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQty(request.getQty());
                cartItem.setRemark(request.getRemark());

                return new CartItemResponse(
                                cartItemRepository.save(cartItem));
        }

        @Override
        public CartItemResponse update(Integer id, CartItemRequest request) {

                CartItem cartItem = getCartItem(id);
                Cart cart = getCart(request.getCartId());
                Product product = getProduct(request.getProductId());

                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQty(request.getQty());
                cartItem.setRemark(request.getRemark());

                return new CartItemResponse(
                                cartItemRepository.save(cartItem));
        }

        @Override
        public void delete(Integer id) {

                CartItem cartItem = getCartItem(id);

                cartItemRepository.delete(cartItem);
        }

        private CartItem getCartItem(Integer id) {

                return cartItemRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("購物車商品"));
        }

        private Cart getCart(Integer id) {

                return cartRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("購物車"));
        }

        private Product getProduct(Integer id) {

                return productRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("商品"));
        }
}