package com.mealgo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.Address;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Integer> {

    Optional<Address> findByUserIdAndId(int userId, int addressId);

    // boolean existsByIdAndUser_id(int addressId, int userId);

    List<Address> findByUserId(Integer userId);

}
