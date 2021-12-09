package com.bomberman.entities;

import javafx.scene.image.Image;

public abstract class AnimatedEntity extends Entity{

    protected int animate = 0;                  // ??? như 1 cái làm mốc đo thời gian
    protected final int MAX_ANIMATE = 7500;     // ???

    public AnimatedEntity(int x, int y, Image image) {
        super(x, y, image);
    }

    // ????
    protected void animation() {
        if (animate < MAX_ANIMATE) {
            animate++;
        } else {
            animate = 0;
        }
    }

    // ??? chạy hiệu ứng hình ảnh ???
    public abstract void playAnimation();
}