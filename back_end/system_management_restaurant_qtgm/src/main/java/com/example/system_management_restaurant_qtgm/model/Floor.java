package com.example.system_management_restaurant_qtgm.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "floor")
    private Set<Section> sectionSet;

    public Floor() {
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

    public Set<Section> getSectionSet() {
        return sectionSet;
    }

    public void setSectionSet(Set<Section> sectionSet) {
        this.sectionSet = sectionSet;
    }
}
