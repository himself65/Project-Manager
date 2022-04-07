package com.splask.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDB extends JpaRepository<User, Integer>{
    @Query(value = "SELECT s FROM User s where s.username = :username")
    List<User> findByUsername(@Param("username") String username);

}
