package com.learning.healthpro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u.password from User u where u.phone = ?1")
    Optional<String> findPassWordByPhone(String phone);

    @Query("select count(u) from User u where u.phone = ?1")
    Long countByPhone(String phone);


}
