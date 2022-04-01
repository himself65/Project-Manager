package com.splask.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface teamDB extends JpaRepository<Team, Integer> {
}
