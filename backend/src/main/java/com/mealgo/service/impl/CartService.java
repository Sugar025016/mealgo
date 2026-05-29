package com.mealgo.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mealgo.dto.request.CartRequest;
import com.mealgo.dto.response.CartResponse;
import com.mealgo.entity.Cart;
import com.mealgo.entity.CartItem;
import com.mealgo.entity.Product;
import com.mealgo.entity.Shop;
import com.mealgo.entity.User;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.ICartRepository;
import com.mealgo.repository.IProductRepository;
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
    private final IProductRepository productRepository;

    // @Override
    // public List<CartResponse> findAll() {

    // List<Cart> carts = cartRepository.findAll();

    // return carts.stream().map(CartResponse::new).toList();
    // }

    @Override
    public List<CartResponse> findAllByUserId(Integer userId) {

        List<Cart> carts = cartRepository.findAllByUserId(userId);

        return carts.stream().map(CartResponse::new).toList();
    }

    @Override
    public CartResponse findByUserIdAndId(Integer userId, Integer id) {

        Cart cart = getCart(userId, id);

        return new CartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse create(Integer userId, CartRequest request) {

        User user = getUser(userId);
        Shop shop = getShop(request.getShopId());
        Product product = productRepository.findByShopIdAndId(request.getShopId(), request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("商品"));

        Cart cart = cartRepository.findByUserIdAndShopId(
                userId,
                request.getShopId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setShop(shop);
                    return newCart;
                });

        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product.getId())
                        && Objects.equals(
                                item.getRemark(),
                                request.getRemark()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setCart(cart);
                    newCartItem.setProduct(product);
                    newCartItem.setRemark(request.getRemark());
                    newCartItem.setQty(0);
                    cart.getCartItems().add(newCartItem);
                    return newCartItem;
                });

        cartItem.setQty(cartItem.getQty() + request.getQty());

        return new CartResponse(
                cartRepository.save(cart));
    }

    @Override
    public void delete(Integer userId, Integer id) {

        Cart cart = getCart(userId, id);

        cartRepository.delete(cart);
    }

    private Cart getCart(Integer userId, Integer id) {

        return cartRepository.findByUserIdAndId(userId, id)
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
