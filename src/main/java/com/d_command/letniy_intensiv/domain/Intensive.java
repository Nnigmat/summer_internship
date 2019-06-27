package com.d_command.letniy_intensiv.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "intensive")
public class Intensive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private LocalDate date_start;
    private LocalDate date_end;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curator_id")
    private User curator;

    public Intensive() {}

    public Intensive(String name, String description, String date_end, String date_start, User user) {
        this.name = name;
        this.description = description;
        try {
            this.date_start = LocalDate.parse(date_start);
        } catch (Exception e) {
            this.date_start = LocalDate.now();
        }
        try {
            this.date_end = LocalDate.parse(date_end);
        } catch (Exception e) {
            this.date_start = LocalDate.now();
        }
        this.curator = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCurator() {
        return curator;
    }

    public void setCurator(User curator) {
        this.curator = curator;
    }

    public LocalDate getDate_start() {
        return date_start;
    }

    public void setDate_start(LocalDate date_start) {
        this.date_start = date_start;
    }

    public LocalDate getDate_end() {
        return date_end;
    }

    public void setDate_end(LocalDate date_end) {
        this.date_end = date_end;
    }

    public void update(String name, String description, String date_start, String date_end) {
        if (!name.isEmpty()) {
            this.name = name;
        }
        if (!description.isEmpty()) {
            this.description = description;
        }
        if (!date_start.isEmpty()) {
            try {
                this.date_start = LocalDate.parse(date_start);
            } catch (Exception e) {
                this.date_start = LocalDate.now();
            }
        }
        if (!date_end.isEmpty()) {
            try {
                this.date_end = LocalDate.parse(date_end);
            } catch (Exception e) {
                this.date_end = LocalDate.now();
            }
        }
    }
}
