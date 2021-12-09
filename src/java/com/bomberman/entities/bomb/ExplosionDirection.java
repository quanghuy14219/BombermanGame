package com.bomberman.entities.bomb;

import com.bomberman.constants.Const;
import com.bomberman.constants.Direction;
import com.bomberman.entities.Entity;
import com.bomberman.control.Map;
import com.bomberman.entities.stacticEntities.Brick;
import com.bomberman.entities.stacticEntities.Wall;

public class ExplosionDirection {
    private int x_init, y_init;             // vị trí ban đầu
    private int radius;                     // bán kính nổ bomb

    private Direction dir;                  // hướng nổ

    protected BombExplosion[] explosions;   // list các vụ nổ ở mỗi block

    // khởi tạo
    public ExplosionDirection(int x, int y, Direction dir, int r) {
        this.dir = dir;
        x_init = x;
        y_init = y;
        radius = r;
        explosions = new BombExplosion[calculateExplosionRadius()];
        createBombExplosion();  // tạo xong explosions chứa các flame theo hướng dir
    }

    // tính toán bán kính vụ nổ - khi vụ nổ cham tới tường hoặc gạch thì ngừng,và nếu là gạch thì cho nổ gạch
    private int calculateExplosionRadius() {
        int r = 0;
        int x = x_init;
        int y = y_init;
        while (r < radius) {
            switch (dir) {
                case UP:
                    y -= Const.SCALED_SIZE;
                    break;
                case DOWN:
                    y += Const.SCALED_SIZE;
                    break;
                case LEFT:
                    x -= Const.SCALED_SIZE;
                    break;
                case RIGHT:
                    x += Const.SCALED_SIZE;
                    break;
            }
            Entity entity = Map.getStaticEntityAt(x, y);
            if (entity instanceof Wall) {
                break;
            }
            if (entity instanceof Brick) {
                ((Brick) entity).setExploded();
                break;
            }
            r++;
        }
        return r;
    }

    // tạo ra các flame theo hướng dir
    private void createBombExplosion() {
        boolean last = false;
        int x = x_init;
        int y = y_init;

        for (int i = 0; i < explosions.length; i++) {
            last = (i == explosions.length - 1);

            switch (dir) {
                case UP:
                    y -= Const.SCALED_SIZE;
                    break;
                case DOWN:
                    y += Const.SCALED_SIZE;
                    break;
                case LEFT:
                    x -= Const.SCALED_SIZE;
                    break;
                case RIGHT:
                    x += Const.SCALED_SIZE;
                    break;
            }
            explosions[i] = new BombExplosion(x, y, dir, last);
            Map.getTopLayer().add(explosions[i]);

        }

    }

    public BombExplosion[] getExplosions() {
        return explosions;
    }

    // update
    public void update() {
        for (BombExplosion explosion : explosions) {
            explosion.update();
        }
    }

}
