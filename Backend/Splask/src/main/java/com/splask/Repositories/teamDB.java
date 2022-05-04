package com.splask.Repositories;

import com.splask.Models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface teamDB extends JpaRepository<Team, Integer> {
}
