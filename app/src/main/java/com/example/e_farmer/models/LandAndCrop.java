package com.example.e_farmer.models;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_farmer.R;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class LandAndCrop extends BaseObservable {
    @Id(assignable = true)
    private long id;
    public ToOne<User> user;

    private String name;
    private String square_number;
    private String task;
    private String start_date;
    private String completion_date;
    private String description;
    private String landImage;

    public LandAndCrop() {
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @BindingAdapter("landImage")
    public static void loadImage(ImageView view, String imageUrl) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.trac)
                .error(R.drawable.ic_launcher_background);

        Glide.with(view.getContext())
                .setDefaultRequestOptions(options)
                .load(imageUrl)
                .into(view);
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getLandImage() {
        return landImage;
    }

    public void setLandImage(String landImage) {
        this.landImage = landImage;
    }

    @Bindable
    public String getSquare_number() {
        return square_number;
    }

    public void setSquare_number(String square_number) {
        this.square_number = square_number;
    }

    @Bindable
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Bindable
    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    @Bindable
    public String getCompletion_date() {
        return completion_date;
    }

    public void setCompletion_date(String completion_date) {
        this.completion_date = completion_date;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
