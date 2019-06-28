package com.d_command.letniy_intensiv.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String surname;
    private String avatar;
    private boolean active;

    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
    private Set<Team> project_intensive_list;

    @ManyToMany(mappedBy = "who_liked", fetch = FetchType.EAGER)
    private Set<Project> liked_projects;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<TagUser> tags;

    @OneToMany(mappedBy = "creator", fetch = FetchType.EAGER)
    private Set<Project> createdProjects;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

//    @OneToMany
//    @JoinTable(name = "user_tags", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private Set<String> tags;

    public User() {}

    public User(String username, String password, String name, String surname) {
        this.username = username;

        if (!password.equals("")) {
            this.password = password;
        } else {
            this.password = "123";
        }

        if (!name.equals("")) {
            this.name = name;
        } else {
            this.name = "none";
        }

        if (!surname.equals("")) {
            this.surname = surname;
        } else {
            this.surname = "none";
        }

        this.roles = Collections.singleton(Role.USER);
        this.active = true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    //--------------------------------------------------------------------------------------------
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Team> getProject_intensive_list() {
        return project_intensive_list;
    }

    public void setProject_intensive_list(Set<Team> project_intensive_list) {
        this.project_intensive_list = project_intensive_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<Project> getCreatedProjects() {
        return createdProjects;
    }

    public void setCreatedProjects(Set<Project> createdProjects) {
        this.createdProjects = createdProjects;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Project> getLiked_projects() {
        return liked_projects;
    }

    public void setLiked_projects(Set<Project> liked_projects) {
        this.liked_projects = liked_projects;
    }

    public Set<TagUser> getTags() {
        return tags;
    }

    public void setTags(Set<TagUser> tags) {
        this.tags = tags;
    }

    //--------------------------------------------------------------------------------------------
    public boolean isCurator() {
        return roles.contains(Role.CURATOR);
    }

    public boolean isModerator() {
        return roles.contains(Role.MODERATOR);
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public boolean isBanned() {
        return roles.contains(Role.BAN);
    }

    public boolean isUser() {
        return roles.contains(Role.USER);
    }

    public void update(String username, String password, String name, String surname) {
        if (username != "") {
            this.username = username;
        }
        if (password != "") {
            this.password = password;
        }
        if (name != "") {
            this.name = name;
        }
        if (surname != "") {
            this.surname = surname;
        }
    }

    public void addTag(TagUser tag) {
        this.tags.add(tag);
    }

    public boolean containsTag(String tag) {
        boolean flag = false;
        for (TagUser item : tags) {
            if (item.getText().equals(tag)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    //
//    public Set<String> getTags() {
//        return tags;
//    }
//
//    public void setTags(Set<String> tags) {
//        this.tags = tags;
//    }
}
