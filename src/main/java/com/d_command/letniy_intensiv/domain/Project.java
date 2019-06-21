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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "project_user",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> team;

    @ManyToMany(mappedBy = "project_list", fetch = FetchType.EAGER)
    private Set<Intensive> intensive_list;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supervisor_id")
    private User supervisor;

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

    public Set<Intensive> getIntensive_list() {
        return intensive_list;
    }

    public void setIntensive_list(Set<Intensive> intensive_list) {
        this.intensive_list = intensive_list;
    }

    public boolean isIntensiveListEmpty() {
        return intensive_list.isEmpty();
    }

    public void addIntensive(Intensive intensive) {
        intensive_list.add(intensive);
    }

    public void deleteIntensive(Intensive intensive) {
        intensive_list.remove(intensive);
    }

    public Set<User> getTeam() {
        return team;
    }

    public void setTeam(Set<User> team) {
        this.team = team;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public void update(String name, String description) {
        if (!name.isEmpty()) {
            this.name = name;
        }

        if (!description.isEmpty()) {
            this.description = description;
        }
    }

    public void update(User user) {
        this.supervisor = user;
    }

    public void addUser(User user) {
        this.team.add(user);
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
