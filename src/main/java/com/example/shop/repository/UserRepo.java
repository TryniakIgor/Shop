package com.example.shop.repository;

import com.example.shop.model.Location;
import com.example.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserName(String userName);

    @Query("select u from Department d left join d.users where d.location = :location")
    List<User> findByLocation(Location location);

    @Query ("UPDATE users u SET is_deleted = true WHERE u.name =:userName ")
    User delete(String userName);
}
