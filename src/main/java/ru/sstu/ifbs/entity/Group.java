package ru.sstu.ifbs.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@JmixEntity
@Table(name = "GWF_GROUP")
@Entity(name = "gwf_Group")
public class Group extends DefaultNamedEntity {

    @OneToMany(mappedBy = "group")
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}