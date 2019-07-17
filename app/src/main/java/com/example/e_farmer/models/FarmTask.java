package com.example.e_farmer.models;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.SET_DEFAULT;


@Entity(tableName = "tasks", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = SET_DEFAULT))
public class FarmTask extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String userId;
    private String name;
    private String assignee;
    private String supervisor;
    private String start;
    private String due;
    private String description;

    public FarmTask(String userId, String name, String assignee, String supervisor, String start, String due, String description) {
        this.userId = userId;
        this.name = name;
        this.assignee = assignee;
        this.supervisor = supervisor;
        this.start = start;
        this.due = due;
        this.description = description;
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
