package com.bomberman.entities;

import com.bomberman.constants.Const;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Thực thể
public abstract class Entity {
    protected int x_pos;            // vị trí x
    protected int y_pos;            // vị trí y
    protected int x_node;           // vị trí của x tính theo block size!?
    protected int y_node;           // vị trí của y tính theo block size!?
    protected boolean removed;      // ??? - dự đoán là nếu như player bị loại removed = true
    protected Image image;          // ảnh
    protected RectBox boundedBox;   // mỗi entity được minh họa bằng 1 hình chữ nhật

    // Khởi tạo với vị trí ban đầu là x, y
    public Entity(int x, int y, Image image) {

        x_pos = x;
        y_pos = y;

        x_node = x / Const.BLOCK_SIZE;
        y_node = y / Const.BLOCK_SIZE;

        this.image = image;
        removed = false;
    }

    public abstract void update();

    // đang va chạm!?
    public boolean isColliding(Entity other) {
        RectBox otherBox = other.getBoundedBox();
        return boundedBox.checkCollision(otherBox);
    }

    // vẽ???
    public void render(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(image, x_pos, y_pos);
    }



    // xóa nhân vật
    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public RectBox getBoundedBox() {
        return boundedBox;
    }

    public int getX_pos() {
        return x_pos;
    }

    public int getY_pos() {
        return y_pos;
    }
}