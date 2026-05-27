package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::new)
                .toList();
    }

    @Override
    public List<ProductResponse> findByShopId(Integer shopId) {
        return productRepository.findByShopId(shopId)
                .stream()
                .map(ProductResponse::new)
                .toList();
    }

    @Override
    public ProductResponse findById(Integer id) {

        Product product = getProduct(id);

        return new ProductResponse(product);
    }

    @Override
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

    @Override
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
    public void delete(Integer id) {

        Product product = getProduct(id);

        productRepository.delete(product);
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