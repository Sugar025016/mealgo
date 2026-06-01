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

import com.mealgo.dto.request.CartItemRequest;
import com.mealgo.dto.response.CartItemResponse;
import com.mealgo.entity.Cart;
import com.mealgo.entity.CartItem;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.ICartItemRepository;
import com.mealgo.repository.ICartRepository;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @Mock
    private ICartItemRepository cartItemRepository;

    @Mock
    private ICartRepository cartRepository;

    @InjectMocks
    private CartItemService cartItemService;

    @Test
    void update_shouldUpdateCartItem() {
        Integer userId = 1;
        Integer cartItemId = 1;

        Cart cart = createCart(1);
        CartItem cartItem = createCartItem(cartItemId, cart);

        CartItemRequest request = createRequest(5, "多一點醬");

        // 模擬cartItemRepository.findById()回傳的CartItem物件
        when(cartItemRepository.findById(cartItemId))
                .thenReturn(Optional.of(cartItem));

        // 模擬cartItemRepository.save()回傳的CartItem物件
        when(cartItemRepository.save(any(CartItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CartItemResponse result = cartItemService.update(userId, cartItemId, request);

        assertThat(result.getId()).isEqualTo(cartItemId);
        assertThat(result.getQty()).isEqualTo(5);
        assertThat(result.getRemark()).isEqualTo("多一點醬");

        verify(cartItemRepository).findById(cartItemId);
        verify(cartItemRepository).save(cartItem);
    }

    @Test
    void update_shouldThrowException_whenCartItemNotFound() {
        Integer userId = 1;
        Integer cartItemId = 99;

        CartItemRequest request = createRequest(5, "多一點醬");

        when(cartItemRepository.findById(cartItemId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartItemService.update(userId, cartItemId, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("購物車商品");

        verify(cartItemRepository).findById(cartItemId);
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    void delete_shouldDeleteCartItemOnly_whenCartStillHasItems() {
        Integer userId = 1;
        Integer cartItemId = 1;

        Cart cart = createCart(1);

        CartItem cartItem1 = createCartItem(1, cart);
        CartItem cartItem2 = createCartItem(2, cart);

        cart.setCartItems(new ArrayList<>(List.of(cartItem1, cartItem2)));

        when(cartItemRepository.findById(cartItemId))
                .thenReturn(Optional.of(cartItem1));

        cartItemService.delete(userId, cartItemId);

        assertThat(cart.getCartItems()).hasSize(1);
        assertThat(cart.getCartItems()).contains(cartItem2);

        verify(cartItemRepository).findById(cartItemId);
        verify(cartItemRepository).delete(cartItem1);
        verify(cartRepository, never()).delete(any(Cart.class));
    }

    @Test
    void delete_shouldDeleteCart_whenCartHasNoItemsAfterDelete() {
        Integer userId = 1;
        Integer cartItemId = 1;

        Cart cart = createCart(1);
        CartItem cartItem = createCartItem(cartItemId, cart);

        cart.setCartItems(new ArrayList<>(List.of(cartItem)));

        when(cartItemRepository.findById(cartItemId))
                .thenReturn(Optional.of(cartItem));

        cartItemService.delete(userId, cartItemId);

        assertThat(cart.getCartItems()).isEmpty();

        verify(cartItemRepository).findById(cartItemId);
        verify(cartItemRepository).delete(cartItem);
        verify(cartRepository).delete(cart);
    }

    @Test
    void delete_shouldThrowException_whenCartItemNotFound() {
        Integer userId = 1;
        Integer cartItemId = 99;

        when(cartItemRepository.findById(cartItemId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartItemService.delete(userId, cartItemId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("購物車商品");

        verify(cartItemRepository).findById(cartItemId);
        verify(cartItemRepository, never()).delete(any(CartItem.class));
        verify(cartRepository, never()).delete(any(Cart.class));
    }

    private CartItemRequest createRequest(Integer qty, String remark) {
        CartItemRequest request = new CartItemRequest();
        request.setQty(qty);
        request.setRemark(remark);
        return request;
    }

    private Cart createCart(Integer id) {
        Cart cart = new Cart();
        cart.setId(id);
        cart.setCartItems(new ArrayList<>());
        return cart;
    }

    private CartItem createCartItem(Integer id, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        cartItem.setQty(2);
        cartItem.setRemark("不要辣");
        cartItem.setCart(cart);
        return cartItem;
    }
}