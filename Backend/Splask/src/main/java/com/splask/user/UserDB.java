package com.splask.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDB extends JpaRepository<User, Integer>{
User findByUsername(String name);

}
