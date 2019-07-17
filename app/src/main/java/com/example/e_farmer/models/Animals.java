package com.example.e_farmer.models;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_farmer.R;


import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_DEFAULT;

@Entity(tableName = "animals", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = SET_DEFAULT))
public class Animals extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String userId;
    private String image;
    private String type;
    private String tag;
    private String colour;
    private String breed;
    private String sex;
    private String horn_type;
    private String weight;
    private String kids;
    private String age;
    private String source;

    public Animals(String userId, String image, String type, String tag, String colour, String breed, String sex, String horn_type, String weight, String kids, String age, String source) {
        this.userId = userId;
        this.image = image;
        this.type = type;
        this.tag = tag;
        this.colour = colour;
        this.breed = breed;
        this.sex = sex;
        this.horn_type = horn_type;
        this.weight = weight;
        this.kids = kids;
        this.age = age;
        this.source = source;
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
    public String getImage() {
        return image;
    }

    @BindingAdapter("image")
    public static void loadImage(ImageView view, String imageUrl) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.ic_launcher_background);

        Glide.with(view.getContext())
                .setDefaultRequestOptions(options)
                .load(imageUrl)
                .into(view);
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Bindable
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Bindable
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Bindable
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Bindable
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Bindable
    public String getHorn_type() {
        return horn_type;
    }

    public void setHorn_type(String horn_type) {
        this.horn_type = horn_type;
    }

    @Bindable
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Bindable
    public String getKids() {
        return kids;
    }

    public void setKids(String kids) {
        this.kids = kids;
    }

    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Bindable
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
