package com.mealgo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private ICartRepository cartRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IShopRepository shopRepository;

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void create_shouldCreateNewCartAndAddCartItem() {
        Integer userId = 1;
        Integer shopId = 1;
        Integer productId = 1;

        User user = createUser(userId);
        Shop shop = createShop(shopId);
        Product product = createProduct(productId, shop);
        CartRequest request = createRequest(shopId, productId, 2, "不要辣");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(shopRepository.findById(shopId))
                .thenReturn(Optional.of(shop));

        when(productRepository.findByShopIdAndId(shopId, productId))
                .thenReturn(Optional.of(product));

        when(cartRepository.findByUserIdAndShopId(userId, shopId))
                .thenReturn(Optional.empty());

        when(cartRepository.save(any(Cart.class)))
                .thenAnswer(invocation -> {
                    Cart cart = invocation.getArgument(0);
                    cart.setId(1);
                    return cart;
                });

        CartResponse result = cartService.create(userId, request);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getShopId()).isEqualTo(shopId);
        assertThat(result.getCartItems()).hasSize(1);
        assertThat(result.getCartItems().get(0).getQty()).isEqualTo(2);
        assertThat(result.getCartItems().get(0).getRemark()).isEqualTo("不要辣");

        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void create_shouldAddQty_whenSameProductAndSameRemarkExists() {
        Integer userId = 1;
        Integer shopId = 1;
        Integer productId = 1;

        User user = createUser(userId);
        Shop shop = createShop(shopId);
        Product product = createProduct(productId, shop);

        Cart cart = createCart(1, user, shop);
        CartItem cartItem = createCartItem(1, cart, product, 3, "不要辣");
        cart.setCartItems(new ArrayList<>(List.of(cartItem)));

        CartRequest request = createRequest(shopId, productId, 2, "不要辣");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(shopRepository.findById(shopId))
                .thenReturn(Optional.of(shop));

        when(productRepository.findByShopIdAndId(shopId, productId))
                .thenReturn(Optional.of(product));

        when(cartRepository.findByUserIdAndShopId(userId, shopId))
                .thenReturn(Optional.of(cart));

        when(cartRepository.save(cart))
                .thenReturn(cart);

        CartResponse result = cartService.create(userId, request);

        assertThat(result.getCartItems()).hasSize(1);
        assertThat(result.getCartItems().get(0).getQty()).isEqualTo(5);
        assertThat(result.getCartItems().get(0).getRemark()).isEqualTo("不要辣");

        verify(cartRepository).save(cart);
    }

    @Test
    void create_shouldAddNewCartItem_whenSameProductButDifferentRemark() {
        Integer userId = 1;
        Integer shopId = 1;
        Integer productId = 1;

        User user = createUser(userId);
        Shop shop = createShop(shopId);
        Product product = createProduct(productId, shop);

        Cart cart = createCart(1, user, shop);
        CartItem oldItem = createCartItem(1, cart, product, 3, "不要辣");
        cart.setCartItems(new ArrayList<>(List.of(oldItem)));

        CartRequest request = createRequest(shopId, productId, 2, "加辣");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(shopRepository.findById(shopId))
                .thenReturn(Optional.of(shop));

        when(productRepository.findByShopIdAndId(shopId, productId))
                .thenReturn(Optional.of(product));

        when(cartRepository.findByUserIdAndShopId(userId, shopId))
                .thenReturn(Optional.of(cart));

        when(cartRepository.save(cart))
                .thenReturn(cart);

        CartResponse result = cartService.create(userId, request);

        assertThat(result.getCartItems()).hasSize(2);
        assertThat(result.getCartItems())
                .anyMatch(item -> item.getQty() == 3 && item.getRemark().equals("不要辣"));
        assertThat(result.getCartItems())
                .anyMatch(item -> item.getQty() == 2 && item.getRemark().equals("加辣"));

        verify(cartRepository).save(cart);
    }

    @Test
    void create_shouldThrowException_whenUserNotFound() {
        Integer userId = 99;
        CartRequest request = createRequest(1, 1, 2, "不要辣");

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.create(userId, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("使用者");

        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void create_shouldThrowException_whenShopNotFound() {
        Integer userId = 1;
        Integer shopId = 99;

        User user = createUser(userId);
        CartRequest request = createRequest(shopId, 1, 2, "不要辣");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(shopRepository.findById(shopId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.create(userId, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("店家");

        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void create_shouldThrowException_whenProductNotFound() {
        Integer userId = 1;
        Integer shopId = 1;
        Integer productId = 99;

        User user = createUser(userId);
        Shop shop = createShop(shopId);
        CartRequest request = createRequest(shopId, productId, 2, "不要辣");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(shopRepository.findById(shopId))
                .thenReturn(Optional.of(shop));

        when(productRepository.findByShopIdAndId(shopId, productId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.create(userId, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("商品");

        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void delete_shouldDeleteCart() {
        Integer userId = 1;
        Integer cartId = 1;

        User user = createUser(userId);
        Shop shop = createShop(1);
        Cart cart = createCart(cartId, user, shop);

        when(cartRepository.findByUserIdAndId(userId, cartId))
                .thenReturn(Optional.of(cart));

        cartService.delete(userId, cartId);

        verify(cartRepository).findByUserIdAndId(userId, cartId);
        verify(cartRepository).delete(cart);
    }

    @Test
    void delete_shouldThrowException_whenCartNotFound() {
        Integer userId = 1;
        Integer cartId = 99;

        when(cartRepository.findByUserIdAndId(userId, cartId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.delete(userId, cartId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("購物車");

        verify(cartRepository).findByUserIdAndId(userId, cartId);
        verify(cartRepository, never()).delete(any(Cart.class));
    }

    private CartRequest createRequest(Integer shopId, Integer productId, Integer qty, String remark) {
        CartRequest request = new CartRequest();
        request.setShopId(shopId);
        request.setProductId(productId);
        request.setQty(qty);
        request.setRemark(remark);
        return request;
    }

    private User createUser(Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("測試使用者");
        user.setEmail("test@example.com");
        user.setPassword("123456");
        return user;
    }

    private Shop createShop(Integer id) {
        Shop shop = new Shop();
        shop.setId(id);
        shop.setBrand("測試店家");
        shop.setBranch("台南店");
        return shop;
    }

    private Product createProduct(Integer id, Shop shop) {
        Product product = new Product();
        product.setId(id);
        product.setName("雞腿便當");
        product.setPrice(120);
        product.setShop(shop);
        return product;
    }

    private Cart createCart(Integer id, User user, Shop shop) {
        Cart cart = new Cart();
        cart.setId(id);
        cart.setUser(user);
        cart.setShop(shop);
        cart.setCartItems(new ArrayList<>());
        return cart;
    }

    private CartItem createCartItem(
            Integer id,
            Cart cart,
            Product product,
            Integer qty,
            String remark) {

        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQty(qty);
        cartItem.setRemark(remark);
        return cartItem;
    }
}