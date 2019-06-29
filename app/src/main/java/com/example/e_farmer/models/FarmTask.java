package com.example.e_farmer.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class FarmTask extends BaseObservable {

    @Id
    private long id;
    public ToOne<User> user;

    private String name;
    private String assignee;
    private String supervisor;
    private String start;
    private String due;
    private String description;

    public FarmTask() {
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Bindable
    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Bindable
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    @Bindable
    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
