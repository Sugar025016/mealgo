package com.mealgo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.mealgo.dto.response.ShopResponse;
import com.mealgo.entity.Shop;
import com.mealgo.repository.IShopRepository;

@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {

    @Mock
    private IShopRepository shopRepository;

    @InjectMocks
    private ShopService shopService;

    @Test
    void search_ShouldReturnAllShops_WhenKeywordIsBlank() {

        Pageable pageable = PageRequest.of(0, 10);

        Shop shop = new Shop();
        shop.setId(1);
        shop.setBrand("麥當勞");

        Page<Shop> page = new PageImpl<>(List.of(shop));

        when(shopRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        Page<ShopResponse> result = shopService.search("", 0, 10);

        assertEquals(1, result.getTotalElements());

        verify(shopRepository)
                .findAll(any(Pageable.class));
    }

    @Test
    void search_ShouldSearchByKeyword_WhenKeywordExists() {

        Shop shop = new Shop();
        shop.setId(1);
        shop.setBrand("麥當勞");

        Page<Shop> page = new PageImpl<>(List.of(shop));

        when(shopRepository.search(
                eq("麥"),
                any(Pageable.class)))
                .thenReturn(page);

        Page<ShopResponse> result = shopService.search("麥", 0, 10);

        assertEquals(1, result.getTotalElements());

        verify(shopRepository)
                .search(eq("麥"), any(Pageable.class));

        verify(shopRepository, never())
                .findAll(any(Pageable.class));
    }

    @Test
    void search_ShouldReturnCorrectPageInfo() {

        Shop shop1 = new Shop();
        shop1.setBrand("A");

        Shop shop2 = new Shop();
        shop2.setBrand("B");

        Page<Shop> page = new PageImpl<>(
                List.of(shop1, shop2),
                PageRequest.of(1, 5),
                12);

        when(shopRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        Page<ShopResponse> result = shopService.search("", 1, 5);

        assertEquals(12, result.getTotalElements());
        assertEquals(3, result.getTotalPages());
        assertEquals(1, result.getNumber());
        assertEquals(5, result.getSize());
    }
}
