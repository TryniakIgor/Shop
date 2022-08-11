package com.example.shop.repository;

import com.example.shop.model.Location;
import com.example.shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {
    //@Query("select u from User u where userName =:userName and u.isDeleted = false ")
    User findByUserNameAndIsDeletedIsFalse(String userName);

    @Query("select u from Department d \n" +
            "left join d.users u\n" +
            "where d.location = :location")
    List<User> findByLocation(Location location);

    @Query("select u from User u where u.isDeleted = false ")
    Page<User> findAll(Pageable pageable);

    @Query("select u from User u where u.isDeleted = false ")
    List<User> findAll();

    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.userName =:userName")
    void markAsDeleted(String userName);

}
