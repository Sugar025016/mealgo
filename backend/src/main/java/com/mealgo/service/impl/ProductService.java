package com.mealgo.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mealgo.dto.request.ProductRequest;
import com.mealgo.dto.response.ProductResponse;
import com.mealgo.entity.Product;
import com.mealgo.entity.Shop;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.IProductRepository;
import com.mealgo.repository.IShopRepository;
import com.mealgo.service.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IShopRepository shopRepository;

    @Override
    @Cacheable(value = "products")
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::new)
                .toList();
    }

    @Override
    @CacheEvict(value = "shop-products", key = "#shopId")
    public List<ProductResponse> findByShopId(Integer shopId) {
        return productRepository.findByShopId(shopId)
                .stream()
                .map(ProductResponse::new)
                .toList();
    }

    @Override
    @Cacheable(value = "product", key = "#id")
    public ProductResponse findById(Integer id) {

        Product product = getProduct(id);

        return new ProductResponse(product);
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse create(ProductRequest request) {

        Shop shop = getShop(request.getShopId());

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImagePath(request.getImagePath());
        product.setShelve(Boolean.TRUE.equals(request.getIsShelve()));
        product.setShop(shop);

        return new ProductResponse(
                productRepository.save(product));
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "shop-products", key = "#request.shopId")
    })
    public ProductResponse update(Integer id, ProductRequest request) {

        Product product = getProduct(id);
        Shop shop = getShop(request.getShopId());

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImagePath(request.getImagePath());
        product.setShelve(Boolean.TRUE.equals(request.getIsShelve()));
        product.setShop(shop);

        return new ProductResponse(
                productRepository.save(product));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "shop-products", key = "#request.shopId")
    })
    public void delete(Integer shopId, Integer id) {

        Product product = getProductByShopIdAndId(shopId, id);

        productRepository.delete(product);
    }

    private Product getProductByShopIdAndId(Integer shopId, Integer id) {

        return productRepository.findByShopIdAndId(shopId, id)
                .orElseThrow(() -> new ResourceNotFoundException("商品"));
    }

    private Product getProduct(Integer id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("商品"));
    }

    private Shop getShop(Integer id) {

        return shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("店家"));
    }
}