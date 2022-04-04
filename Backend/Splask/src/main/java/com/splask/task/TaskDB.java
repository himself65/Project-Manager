package com.splask.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDB extends JpaRepository<Task, Integer> {


}
