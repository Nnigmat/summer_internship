package com.d_command.letniy_intensiv.domain;

import javax.persistence.*;

@Entity
@Table(name = "intensive")
public class Intensive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String team;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curator_id")
    private User curator;

    public Intensive() {}

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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public User getCurator() {
        return curator;
    }

    public void setCurator(User curator) {
        this.curator = curator;
    }
}
