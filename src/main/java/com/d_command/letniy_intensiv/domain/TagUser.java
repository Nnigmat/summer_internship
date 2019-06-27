package com.d_command.letniy_intensiv.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tag_for_user")
public class TagUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private Set<User> users;

    public TagUser() {
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
