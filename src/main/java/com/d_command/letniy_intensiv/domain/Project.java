package com.d_command.letniy_intensiv.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String date_created;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    @ElementCollection(targetClass = ProjectType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "project_type", joinColumns = @JoinColumn(name = "project_id"))
    @Enumerated(EnumType.STRING)
    private Set<ProjectType> type;

    public Project() {}

    public Project(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.creator = user;
        this.date_created = LocalDate.now().toString();
        this.type = new HashSet<ProjectType>() {{ add(ProjectType.NEW);}};
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public void update(String name, String description) {
        if (!name.isEmpty()) {
            this.name = name;
        }

        if (!description.isEmpty()) {
            this.description = description;
        }
    }

    public Set<ProjectType> getType() {
        return type;
    }

    public void setType(Set<ProjectType> type) {
        this.type = type;
    }

    public boolean isCreator(User user) {
        return this.getCreator().getId().equals(user.getId());
    }
}
