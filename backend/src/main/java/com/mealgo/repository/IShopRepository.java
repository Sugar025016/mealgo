package com.mealgo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mealgo.entity.Shop;

@Repository
public interface IShopRepository extends JpaRepository<Shop, Integer> {

    Shop getShopById(Integer id);

    List<Shop> findByIsOpenTrue();

    List<Shop> findByIsOrderableTrue();

    List<Shop> findByBrandContaining(String brand);

    List<Shop> findByUserId(Integer userId);

    Page<Shop> findByBrandContaining(String keyword, Pageable pageable);

    @Query("""
                SELECT s
                FROM Shop s
                WHERE
                    (:keyword IS NULL OR :keyword = '')
                    OR LOWER(s.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(s.branch) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Shop> search(
            @Param("keyword") String keyword,
            Pageable pageable);

}