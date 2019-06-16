package com.example.e_farmer.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Animals {
    @Id
    long id;
    private int image;
    private String name;
    private int tag;
    private String colour;
    private String breed;
    private String sex;
    private String horn_type;
    private Double weight;
    private int kids;
    private int age;
    private String source;


    public Animals(int image, String name, int tag, String colour, String breed, String sex, String horn_type, Double weight, int kids, int age, String source) {
        this.image = image;
        this.name = name;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHorn_type() {
        return horn_type;
    }

    public void setHorn_type(String horn_type) {
        this.horn_type = horn_type;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getKids() {
        return kids;
    }

    public void setKids(int kids) {
        this.kids = kids;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
