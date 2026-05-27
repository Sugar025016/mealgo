package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.AddressRequest;
import com.mealgo.dto.response.AddressResponse;
import com.mealgo.entity.Address;
import com.mealgo.entity.User;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.IAddressRepository;
import com.mealgo.repository.IUserRepository;
import com.mealgo.service.IAddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {

    private final IAddressRepository addressRepository;
    private final IUserRepository userRepository;

    @Override
    public List<AddressResponse> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(AddressResponse::new)
                .toList();
    }

    @Override
    public AddressResponse findById(Integer id) {

        Address address = getAddress(id);

        return new AddressResponse(address);
    }

    @Override
    public AddressResponse create(AddressRequest request) {

        User user = getUser(request.getUserId());

        Address address = new Address();

        address.setCity(request.getCity());
        address.setArea(request.getArea());
        address.setStreet(request.getStreet());
        address.setDetail(request.getDetail());
        address.setLat(request.getLat());
        address.setLng(request.getLng());
        address.setUser(user);

        return new AddressResponse(
                addressRepository.save(address));
    }

    @Override
    public AddressResponse update(
            Integer id,
            AddressRequest request) {

        Address address = getAddress(id);
        User user = getUser(request.getUserId());

        address.setCity(request.getCity());
        address.setArea(request.getArea());
        address.setStreet(request.getStreet());
        address.setDetail(request.getDetail());
        address.setLat(request.getLat());
        address.setLng(request.getLng());
        address.setUser(user);

        return new AddressResponse(
                addressRepository.save(address));
    }

    @Override
    public void delete(Integer id) {

        Address address = getAddress(id);

        addressRepository.delete(address);
    }

    private Address getAddress(Integer id) {

        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("地址"));
    }

    private User getUser(Integer id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("使用者"));
    }
}