package com.mealgo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.dto.request.AddressRequest;
import com.mealgo.dto.response.AddressResponse;
import com.mealgo.entity.Address;
import com.mealgo.entity.User;
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
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        return new AddressResponse(address);
    }

    @Override
    public AddressResponse create(AddressRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = new Address();

        address.setCity(request.getCity());
        address.setArea(request.getArea());
        address.setStreet(request.getStreet());
        address.setDetail(request.getDetail());
        address.setLat(request.getLat());
        address.setLng(request.getLng());
        address.setUser(user);

        return new AddressResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse update(Integer id, AddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        address.setCity(request.getCity());
        address.setArea(request.getArea());
        address.setStreet(request.getStreet());
        address.setDetail(request.getDetail());
        address.setLat(request.getLat());
        address.setLng(request.getLng());
        address.setUser(user);

        return new AddressResponse(addressRepository.save(address));
    }

    @Override
    public void delete(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        addressRepository.delete(address);
    }
}