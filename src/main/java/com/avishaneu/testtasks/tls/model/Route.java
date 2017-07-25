package com.avishaneu.testtasks.tls.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by avishaneu on 7/25/2017.
 */
public class Route {

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private Integer head;

    @NotNull
    @NotEmpty
    private List<Integer> locations;

    public Route() {
    }

    public Route(String name, Integer head, List<Integer> locations) {
        this.name = name;
        this.head = head;
        this.locations = locations;
    }

    public Route(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHead() {
        return head;
    }

    public void setHead(Integer head) {
        this.head = head;
    }

    public List<Integer> getLocations() {
        return locations;
    }

    public void setLocations(List<Integer> locations) {
        this.locations = locations;
    }
}
