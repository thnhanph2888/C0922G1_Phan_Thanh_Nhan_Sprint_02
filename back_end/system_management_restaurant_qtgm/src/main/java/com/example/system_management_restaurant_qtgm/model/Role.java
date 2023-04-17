package com.example.system_management_restaurant_qtgm.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "role")
    Set<AccountRole> accountRoleSet;

    public Role() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AccountRole> getAccountRoleSet() {
        return accountRoleSet;
    }

    public void setAccountRoleSet(Set<AccountRole> accountRoleSet) {
        this.accountRoleSet = accountRoleSet;
    }
}
