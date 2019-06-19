package com.d_command.letniy_intensiv.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "intensive")
public class Intensive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String date_start;
    private String date_end;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curator_id")
    private User curator;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "intensive_project",
            joinColumns = @JoinColumn(name = "intensive_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private Set<Project> project_list;

    public Intensive() {}

    public Intensive(String name, String description, String date_end, String date_start, User user) {
        this.name = name;
        this.description = description;
        this.date_start = date_start;
        this.date_end = date_end;
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

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public Set<Project> getProject_list() {
        return project_list;
    }

    public void setProject_list(Set<Project> project_list) {
        this.project_list = project_list;
    }

    public void addProject(Project project) {
        project_list.add(project);
    }

    public void deleteProject(Project project) {
        project_list.remove(project);
    }
}
