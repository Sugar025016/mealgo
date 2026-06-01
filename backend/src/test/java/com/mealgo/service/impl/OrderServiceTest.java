package com.mealgo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mealgo.dto.request.OrderRequest;
import com.mealgo.dto.response.OrderResponse;
import com.mealgo.entity.Address;
import com.mealgo.entity.Cart;
import com.mealgo.entity.CartItem;
import com.mealgo.entity.Order;
import com.mealgo.entity.Product;
import com.mealgo.entity.Shop;
import com.mealgo.entity.User;
import com.mealgo.enums.PayMethod;
import com.mealgo.exception.BadRequestException;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.IAddressRepository;
import com.mealgo.repository.ICartRepository;
import com.mealgo.repository.IOrderRepository;
import com.mealgo.repository.IUserRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IAddressRepository addressRepository;

    @Mock
    private ICartRepository cartRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void create_success() {

        Integer userId = 1;

        User user = createUser();
        Address address = createAddress();
        Shop shop = createShop();

        Product product = createProduct();
        CartItem cartItem = createCartItem(product);

        Cart cart = createCart(shop, cartItem);

        OrderRequest request = createRequest();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(cartRepository.findByUserIdAndId(userId, 1))
                .thenReturn(Optional.of(cart));

        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    order.setId(1);
                    return order;
                });

        OrderResponse result = orderService.create(userId, request);

        assertThat(result.getSubtotal()).isEqualTo(240);
        assertThat(result.getDeliveryPrice()).isEqualTo(50);
        assertThat(result.getTotalPrice()).isEqualTo(290);

        verify(orderRepository).save(any());
        verify(cartRepository).delete(cart);
    }

    @Test
    void create_shouldThrowException_whenUserNotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.create(1, createRequest()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(orderRepository, never()).save(any());
    }

    @Test
    void create_shouldThrowException_whenCartNotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(createUser()));

        when(cartRepository.findByUserIdAndId(1, 1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.create(1, createRequest()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(orderRepository, never()).save(any());
    }

    @Test
    void create_shouldThrowException_whenAddressNotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(createUser()));

        Cart cart = createCart(
                createShop(),
                createCartItem(createProduct()));

        when(cartRepository.findByUserIdAndId(1, 1))
                .thenReturn(Optional.of(cart));

        when(addressRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.create(1, createRequest()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(orderRepository, never()).save(any());
    }

    @Test
    void create_shouldThrowException_whenCartEmpty() {

        Cart cart = new Cart();
        cart.setId(1);
        cart.setShop(createShop());
        cart.setCartItems(new ArrayList<>());

        when(userRepository.findById(1))
                .thenReturn(Optional.of(createUser()));

        when(cartRepository.findByUserIdAndId(1, 1))
                .thenReturn(Optional.of(cart));

        when(addressRepository.findById(1))
                .thenReturn(Optional.of(createAddress()));

        assertThatThrownBy(() -> orderService.create(1, createRequest()))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("購物車沒有商品");
    }

    private OrderRequest createRequest() {
        OrderRequest request = new OrderRequest();
        request.setCartId(1);
        request.setAddressId(1);
        request.setOrderNote("不要辣");
        request.setPayMethod(PayMethod.CASH.getCode());
        return request;
    }

    private User createUser() {
        User user = new User();
        user.setId(1);
        return user;
    }

    private Address createAddress() {
        Address address = new Address();
        address.setId(1);
        address.setCity("台南市");
        address.setArea("永康區");
        address.setStreet("中正路");
        address.setDetail("100號");
        address.setLat(23.0);
        address.setLng(120.0);
        return address;
    }

    private Shop createShop() {
        Shop shop = new Shop();
        shop.setId(1);
        shop.setDeliveryPrice(50);
        return shop;
    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(1);
        product.setPrice(120);
        return product;
    }

    private CartItem createCartItem(Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQty(2);
        return cartItem;
    }

    private Cart createCart(
            Shop shop,
            CartItem cartItem) {

        Cart cart = new Cart();
        cart.setId(1);
        cart.setShop(shop);
        cart.setCartItems(List.of(cartItem));

        return cart;
    }
}
