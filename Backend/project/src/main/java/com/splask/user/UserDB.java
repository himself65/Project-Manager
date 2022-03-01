package com.splask.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//public interface UserDB extends JpaRepository<User, Long>{
public interface UserDB extends JpaRepository<User, Integer>{


}
