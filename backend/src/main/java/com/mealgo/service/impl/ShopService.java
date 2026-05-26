package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.ShopRequest;
import com.mealgo.dto.response.ShopResponse;
import com.mealgo.entity.Shop;
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

        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

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

        Shop saveShop = shopRepository.save(shop);

        return new ShopResponse(saveShop);
    }

    @Override
    public ShopResponse update(Integer id, ShopRequest request) {

        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

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

        Shop saveShop = shopRepository.save(shop);

        return new ShopResponse(saveShop);
    }

    @Override
    public void delete(Integer id) {

        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        shopRepository.delete(shop);
    }
}