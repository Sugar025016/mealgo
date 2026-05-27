package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.ShopRequest;
import com.mealgo.dto.response.ShopResponse;
import com.mealgo.entity.Shop;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.IShopRepository;
import com.mealgo.service.IShopService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService implements IShopService {

    private final IShopRepository shopRepository;

    @Override
    public List<ShopResponse> findAll() {
        return shopRepository.findAll()
                .stream()
                .map(ShopResponse::new)
                .toList();
    }

    @Override
    public ShopResponse findById(Integer id) {

        Shop shop = getShop(id);

        return new ShopResponse(shop);
    }

    @Override
    public ShopResponse create(ShopRequest request) {

        Shop shop = new Shop();

        shop.setBrand(request.getBrand());
        shop.setBranch(request.getBranch());
        shop.setPhone(request.getPhone());
        shop.setDescription(request.getDescription());

        shop.setDeliveryKm(request.getDeliveryKm());
        shop.setDeliveryPrice(request.getDeliveryPrice());

        shop.setCity(request.getCity());
        shop.setArea(request.getArea());
        shop.setStreet(request.getStreet());
        shop.setDetail(request.getDetail());

        shop.setOpen(true);
        shop.setOrderable(true);

        return new ShopResponse(
                shopRepository.save(shop));
    }

    @Override
    public ShopResponse update(Integer id, ShopRequest request) {

        Shop shop = getShop(id);

        shop.setBrand(request.getBrand());
        shop.setBranch(request.getBranch());
        shop.setPhone(request.getPhone());
        shop.setDescription(request.getDescription());

        shop.setDeliveryKm(request.getDeliveryKm());
        shop.setDeliveryPrice(request.getDeliveryPrice());

        shop.setCity(request.getCity());
        shop.setArea(request.getArea());
        shop.setStreet(request.getStreet());
        shop.setDetail(request.getDetail());

        return new ShopResponse(
                shopRepository.save(shop));
    }

    @Override
    public void delete(Integer id) {

        Shop shop = getShop(id);

        shopRepository.delete(shop);
    }

    private Shop getShop(Integer id) {

        return shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("店家"));
    }
}