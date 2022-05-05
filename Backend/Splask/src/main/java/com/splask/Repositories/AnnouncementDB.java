package com.splask.Repositories;


import com.splask.Models.Announcements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementDB extends JpaRepository<Announcements,Integer> {
}
