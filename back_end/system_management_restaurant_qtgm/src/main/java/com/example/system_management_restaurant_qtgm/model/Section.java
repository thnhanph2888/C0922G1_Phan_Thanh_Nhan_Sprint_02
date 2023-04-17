package com.example.system_management_restaurant_qtgm.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "floor_id", referencedColumnName = "id")
    private Floor floor;
    @OneToMany(mappedBy = "section")
    private Set<DiningTable> diningTableSet;

    public Section() {
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

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Set<DiningTable> getDiningTableSet() {
        return diningTableSet;
    }

    public void setDiningTableSet(Set<DiningTable> diningTableSet) {
        this.diningTableSet = diningTableSet;
    }
}
