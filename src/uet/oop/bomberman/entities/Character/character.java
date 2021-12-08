package uet.oop.bomberman.entities.Character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;

/**
 * Bao gồm Bomber và Enemy
 */
public abstract class character extends AnimatedEntity {
    protected boolean Moving = false;
    protected boolean alive = true;
    protected int direction = -1;
    protected int speed;
    public int timeAfter = 40;

    public character(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public abstract void update();

    @Override
    public abstract void render(GraphicsContext gc);

    /**
     * Tính toán hướng đi
     */
    protected abstract void calculateMove();

    protected abstract void move(double xa, double ya);

    /**
     * Được gọi khi đối tượng bị tiêu diệt
     */
    public abstract void kill();

    /**
     * Xử lý hiệu ứng bị tiêu diệt
     */
    protected abstract void afterKill();

    /**
     * Kiểm tra xem đối tượng có di chuyển tới vị trí đã tính toán hay không
     * @param x
     * @param y
     * @return
     */
    protected abstract boolean canMove(double x, double y);

    //cập nhật ảnh
    public abstract Image getUpImage();

    public abstract Image getDownImage();

    public abstract Image getRightImage();

    public abstract Image getLeftImage();
}
