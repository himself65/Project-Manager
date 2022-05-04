package com.splask.Models;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity
@Table(name = "Announcements")
public class Announcements {

    @Id
    @Column(name = "announcement_id", nullable = false)
    private Integer id;

    @Column
    private String message;

    @Column
    private final LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Announcements()
    {
        dateCreated = LocalDateTime.now();
    }


    public String getAnnouncement() {
        return message;
    }

    public void setAnnouncement(String announcement) {
        this.message = announcement;
    }

    public String getDateCreated()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateCreated.format(format);
    }
}
