package com.splask.Repositories;

import com.splask.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface projectDB extends JpaRepository<Project, Integer> {


}
