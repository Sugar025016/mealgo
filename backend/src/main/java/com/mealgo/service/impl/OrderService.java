package com.mealgo.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mealgo.dto.request.OrderRequest;
import com.mealgo.dto.response.OrderResponse;
import com.mealgo.entity.Address;
import com.mealgo.entity.Cart;
import com.mealgo.entity.CartItem;
import com.mealgo.entity.Order;
import com.mealgo.entity.OrderItem;
import com.mealgo.entity.Shop;
import com.mealgo.entity.User;
import com.mealgo.enums.OrderStatus;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.IAddressRepository;
import com.mealgo.repository.ICartRepository;
import com.mealgo.repository.IOrderRepository;
import com.mealgo.repository.IUserRepository;
import com.mealgo.service.IOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IUserRepository userRepository;
    private final IAddressRepository addressRepository;
    private final ICartRepository cartRepository;

    @Override
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    @Override
    public OrderResponse findById(Integer id) {
        Order order = getOrder(id);
        return new OrderResponse(order);
    }

    @Override
    public OrderResponse findByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrderResponse(order);
    }

    @Override
    public List<OrderResponse> findByUserId(Integer userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    @Override
    public List<OrderResponse> findByShopId(Integer shopId) {
        return orderRepository.findByShopId(shopId)
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponse create(OrderRequest request) {

        User user = getUser(request.getUserId());
        Cart cart = getCart(request.getCartId());
        if (!cart.getUser().getId().equals(request.getUserId())) {
            throw new RuntimeException("找不到購物車");
        }

        Shop shop = cart.getShop();
        Address address = getAddress(request.getAddressId());
        List<CartItem> cartItemList = cart.getCartItems();
        int deliveryPrice = shop.getDeliveryPrice();
        int subtotal = cartItemList.stream()
                .mapToInt(cartItem -> cartItem.getProduct().getPrice()
                        * cartItem.getQty())
                .sum();

        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setOrderNote(request.getOrderNote());
        order.setDeliveryPrice(shop.getDeliveryPrice());

        order.setSubtotal(subtotal);
        order.setTotalPrice(subtotal + deliveryPrice);

        order.setStatus(OrderStatus.PENDING.getCode());

        order.setPayMethod(request.getPayMethod());

        List<OrderItem> orderItems = cartItemList.stream()
                .map(cartItem -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProduct(cartItem.getProduct());
                    item.setQty(cartItem.getQty());
                    item.setPrice(cartItem.getProduct().getPrice());
                    item.setRemark(cartItem.getRemark());
                    return item;
                })
                .toList();

        order.setOrderItems(orderItems);
        order.setCity(address.getCity());
        order.setArea(address.getArea());
        order.setStreet(address.getStreet());
        order.setDetail(address.getDetail());
        order.setLat(address.getLat());
        order.setLng(address.getLng());

        order.setUser(user);
        order.setShop(shop);

        cartRepository.delete(cart);
        return new OrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse update(Integer id, OrderRequest request) {
        Order order = getOrder(id);

        Address address = getAddress(request.getAddressId());
        order.setOrderNote(request.getOrderNote());

        if (request.getPayMethod() != null) {
            order.setPayMethod(request.getPayMethod());
        }

        order.setCity(address.getCity());
        order.setArea(address.getArea());
        order.setStreet(address.getStreet());
        order.setDetail(address.getDetail());
        order.setLat(address.getLat());
        order.setLng(address.getLng());

        return new OrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse updateStatus(Integer id, Integer statusCode) {

        Order order = getOrder(id);

        OrderStatus.fromCode(statusCode);

        order.setStatus(statusCode);

        return new OrderResponse(orderRepository.save(order));
    }

    @Override
    public void delete(Integer id) {
        Order order = getOrder(id);

        orderRepository.delete(order);
    }

    private Order getOrder(Integer id) {

        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("訂單"));
    }

    private String generateOrderNumber() {

        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        int random = ThreadLocalRandom.current()
                .nextInt(100, 999);

        return "MG" + time + random;
    }

    private User getUser(Integer id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("使用者"));
    }

    private Cart getCart(Integer id) {

        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("購物車"));
    }

    private Address getAddress(Integer id) {

        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("地址"));
    }
}