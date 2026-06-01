package com.mealgo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mealgo.dto.request.AddressRequest;
import com.mealgo.dto.response.AddressResponse;
import com.mealgo.entity.Address;
import com.mealgo.entity.User;
import com.mealgo.exception.ResourceNotFoundException;
import com.mealgo.repository.IAddressRepository;
import com.mealgo.repository.IUserRepository;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private IAddressRepository addressRepository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    void findByUserId_shouldReturnAddressList() {
        Integer userId = 1;

        User user = createUser(userId);
        Address address = createAddress(1, user);

        when(addressRepository.findByUserId(userId))
                .thenReturn(List.of(address));

        List<AddressResponse> result = addressService.findByUserId(userId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1);
        assertThat(result.get(0).getCity()).isEqualTo("台南市");
        assertThat(result.get(0).getUserId()).isEqualTo(userId);

        verify(addressRepository).findByUserId(userId);
    }

    @Test
    void findByUserIdAndId_shouldReturnAddress() {
        Integer userId = 1;
        Integer addressId = 1;

        User user = createUser(userId);
        Address address = createAddress(addressId, user);

        when(addressRepository.findByUserIdAndId(userId, addressId))
                .thenReturn(Optional.of(address));

        AddressResponse result = addressService.findByUserIdAndId(userId, addressId);

        assertThat(result.getId()).isEqualTo(addressId);
        assertThat(result.getCity()).isEqualTo("台南市");
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(addressRepository).findByUserIdAndId(userId, addressId);
    }

    @Test
    void findByUserIdAndId_shouldThrowException_whenAddressNotFound() {
        Integer userId = 1;
        Integer addressId = 99;

        when(addressRepository.findByUserIdAndId(userId, addressId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressService.findByUserIdAndId(userId, addressId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("找不到地址");

        verify(addressRepository).findByUserIdAndId(userId, addressId);
    }

    @Test
    void create_shouldCreateAddress() {
        Integer userId = 1;

        User user = createUser(userId);
        AddressRequest request = createRequest();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(addressRepository.save(any(Address.class)))
                .thenAnswer(invocation -> {
                    Address address = invocation.getArgument(0);
                    address.setId(1);
                    return address;
                });

        AddressResponse result = addressService.create(userId, request);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getCity()).isEqualTo("台南市");
        assertThat(result.getArea()).isEqualTo("永康區");
        assertThat(result.getStreet()).isEqualTo("中正路");
        assertThat(result.getDetail()).isEqualTo("100號");
        assertThat(result.getLat()).isEqualTo(23.0);
        assertThat(result.getLng()).isEqualTo(120.0);
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(userRepository).findById(userId);
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    void create_shouldThrowException_whenUserNotFound() {
        Integer userId = 99;
        AddressRequest request = createRequest();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressService.create(userId, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("找不到使用者");

        verify(userRepository).findById(userId);
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void update_shouldUpdateAddress() {
        Integer userId = 1;
        Integer addressId = 1;

        User user = createUser(userId);
        Address address = createAddress(addressId, user);
        AddressRequest request = createRequest();

        request.setCity("高雄市");
        request.setArea("左營區");
        request.setStreet("博愛路");
        request.setDetail("200號");
        request.setLat(22.0);
        request.setLng(121.0);

        when(addressRepository.findByUserIdAndId(userId, addressId))
                .thenReturn(Optional.of(address));

        when(addressRepository.save(any(Address.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AddressResponse result = addressService.update(userId, addressId, request);

        assertThat(result.getId()).isEqualTo(addressId);
        assertThat(result.getCity()).isEqualTo("高雄市");
        assertThat(result.getArea()).isEqualTo("左營區");
        assertThat(result.getStreet()).isEqualTo("博愛路");
        assertThat(result.getDetail()).isEqualTo("200號");
        assertThat(result.getLat()).isEqualTo(22.0);
        assertThat(result.getLng()).isEqualTo(121.0);
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(addressRepository).findByUserIdAndId(userId, addressId);
        verify(addressRepository).save(address);
    }

    @Test
    void delete_shouldDeleteAddress() {
        Integer userId = 1;
        Integer addressId = 1;

        User user = createUser(userId);
        Address address = createAddress(addressId, user);

        when(addressRepository.findByUserIdAndId(userId, addressId))
                .thenReturn(Optional.of(address));

        addressService.delete(userId, addressId);

        verify(addressRepository).findByUserIdAndId(userId, addressId);
        verify(addressRepository).delete(address);
    }

    @Test
    void delete_shouldThrowException_whenAddressNotFound() {
        Integer userId = 1;
        Integer addressId = 99;

        when(addressRepository.findByUserIdAndId(userId, addressId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressService.delete(userId, addressId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("找不到地址");

        verify(addressRepository).findByUserIdAndId(userId, addressId);
        verify(addressRepository, never()).delete(any(Address.class));
    }

    private User createUser(Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("測試使用者");
        user.setEmail("test@example.com");
        user.setPassword("123456");
        return user;
    }

    private Address createAddress(Integer id, User user) {
        Address address = new Address();
        address.setId(id);
        address.setCity("台南市");
        address.setArea("永康區");
        address.setStreet("中正路");
        address.setDetail("100號");
        address.setLat(23.0);
        address.setLng(120.0);
        address.setUser(user);
        return address;
    }

    private AddressRequest createRequest() {
        AddressRequest request = new AddressRequest();
        request.setCity("台南市");
        request.setArea("永康區");
        request.setStreet("中正路");
        request.setDetail("100號");
        request.setLat(23.0);
        request.setLng(120.0);
        return request;
    }
}