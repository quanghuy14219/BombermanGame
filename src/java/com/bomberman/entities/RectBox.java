package com.bomberman.entities;

import javafx.geometry.Rectangle2D;

public class RectBox {
    int x;                          // The x coordinate of the upper-left corner of the Rectangle2D
    int y;                          // The y coordinate of the upper-left corner of the Rectangle2D
    int width;                      // chiều ngang
    int height;                     // chiều dọc
    Rectangle2D boundedBox;         // một hình chữ nhật

    public RectBox(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        boundedBox = new Rectangle2D(x, y, width, height);
    }

    // kiểm tra 2 hình chữ nhật có giao nhau!?
    public boolean checkCollision(RectBox other) {
        return other.getBoundedBox().intersects(boundedBox);
    }

    public void setPosition(int x, int y) {
        boundedBox = new Rectangle2D(x, y, width, height);
    }

    public Rectangle2D getBoundedBox() {
        return boundedBox;
    }
}
