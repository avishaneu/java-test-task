package com.avishaneu.testtasks.tls.model;

import javax.validation.constraints.NotNull;
import java.beans.Transient;

/**
 * Created by avishaneu on 7/25/2017.
 */
public class Location {

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private Double x;

    @NotNull
    private Double y;

    private Boolean head = false;

    public Location() {
    }

    public Location(Integer id) {
        this.id = id;
    }

    public Location(String name, Double x, Double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Location(Integer id, Double x, Double y) {
        this.id = id;
        this.x = x;
        this.y = y;
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

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Transient
    public Boolean getHead() {
        return head;
    }

    public void setHead(Boolean head) {
        this.head = head;
    }

}
