package com.example.tastyhotplace;

import android.graphics.Bitmap;
import android.net.Uri;

public class CardItem {

    public int foodImage;
    public String name;
    public String location;
    public String menu;
    public String side;
    public String price;
    public String time;
    public String tel;
    public String review;
    public String note;

    public int getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CardItem(int foodImage, String name, String location, String menu, String side, String price, String time, String tel, String review, String note) {
        this.foodImage = foodImage;
        this.name = name;
        this.location = location;
        this.menu = menu;
        this.side = side;
        this.price = price;
        this.time = time;
        this.tel = tel;
        this.review = review;
        this.note = note;
    }

    @Override
    public String toString() {
        return "CardItem{" +
                "foodImage=" + foodImage +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", menu='" + menu + '\'' +
                ", side='" + side + '\'' +
                ", price='" + price + '\'' +
                ", time='" + time + '\'' +
                ", tel='" + tel + '\'' +
                ", review='" + review + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
