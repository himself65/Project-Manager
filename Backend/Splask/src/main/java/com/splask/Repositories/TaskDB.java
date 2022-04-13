package com.splask.Repositories;

import com.splask.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDB extends JpaRepository<Task, Integer> {


}
