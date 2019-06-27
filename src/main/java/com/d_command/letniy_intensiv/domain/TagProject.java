package com.d_command.letniy_intensiv.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tag_for_project")
public class TagProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private Set<Project> projects;

    public TagProject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
