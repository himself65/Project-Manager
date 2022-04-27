package com.splask.Repositories;

import com.splask.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface roleDB extends  JpaRepository<Role, Integer>{

}
