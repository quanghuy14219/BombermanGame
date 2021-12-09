package com.bomberman.entities.bomb;

import com.bomberman.constants.Const;
import com.bomberman.constants.Direction;
import com.bomberman.entities.AnimatedEntity;
import com.bomberman.entities.Entity;
import com.bomberman.control.Map;
import com.bomberman.entities.RectBox;
import com.bomberman.control.Sound;
import com.bomberman.entities.mobileEntites.Player;
import com.bomberman.entities.stacticEntities.Brick;
import graphics.Sprite;
import javafx.scene.image.Image;

public class Bomb extends AnimatedEntity {
    private int countDownTime = 120;            // thởi gian đếm ngược trước khi nổ
    private int removeTime = 30;                // thời gian để xóa???
    private final int explosionTime = 50;       // thời gian để nổ???

    private Player player = Player.getPlayer(); // player

    private boolean allowToPass = true;         // có thể đi qua Bomb???
    private boolean exploded = false;           // đã nổ chưa!???

    private ExplosionDirection[] explosions;    // các flame theo 4 hướng
    private BombExplosion explosion;            // Bomb nổ ở trung tâm



    // khởi tạo bomb
    public Bomb(int x, int y, Image bomb) {
        super(x, y, bomb);
        boundedBox = new RectBox(x, y, Const.SCALED_SIZE, Const.SCALED_SIZE);
    }

    // khởi tạo bomb
    public Bomb(int x, int y) {
        super(x, y, Sprite.bomb);
        boundedBox = new RectBox(x, y, Const.SCALED_SIZE, Const.SCALED_SIZE);
    }

    // cập nhật bomb
    public void update() {
        if (countDownTime > 0) {
            countDownTime--;
        } else {
            if (!exploded) {
                setExplosions();
                exploded = true;
                new Sound("/sound/explosion.wav").play(false);
            }
            if (removeTime > 0) {
                removeTime--;
            } else {
                // xóa dấu bomb trong Map
                Map.mapMatrix[y_node][x_node] = ' ';
                remove();   // xóa bomb khỏi listBomb
            }
        }
        animation();
        playAnimation();
        setAllowToPass();
    }

    // cài đặt khả năng đi qua bomb
    public void setAllowToPass() {
        // nếu mà player đặt bomb xong và đi ra thì sẽ ko đi ngược lại qua bomb đc
        if (!isColliding(Player.getPlayer())) {
            allowToPass = false;
        }
    }

    // chạy hoạt ảnh nổ bom ở trung tâm
    public void playAnimation() {
        if (exploded) {
            image = Sprite.getMoveSprite(Sprite.bomb_exploded
                    , Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 30);
        } else {
            image = Sprite.getMoveSprite(Sprite.bomb
                    , Sprite.bomb_1, Sprite.bomb_2, animate, 50);
        }
    }

    // ??? thiết lập vụ nổ
    public void setExplosions() {
        explosions = new ExplosionDirection[4];
        Entity entity = Map.getStaticEntityAt(x_pos, y_pos);    // vị trí???

        // nếu là Gạch thì sẽ bị nổ
        if (entity instanceof Brick) {
            ((Brick) entity).setExploded();
        }

        // ???
        for (int i = 0; i < explosions.length; i++) {
            // thiết lập flame tại vị trí x_pos, y_pos, hướng Direction.dir[i], bán kính Player.getPlayer().getBombRadius()
            explosions[i] = new ExplosionDirection(x_pos, y_pos, Direction.dir[i], Player.getPlayer().getBombRadius());
            // Map add flame ở mỗi block - hình như bị add hai lần
            for (int j = 0; j < explosions[i].getExplosions().length; j++) {
                Map.getTopLayer().add(explosions[i].getExplosions()[j]);
            }
        }
    }


    public boolean isExploded() {
        return exploded;
    }

    public boolean allowToPass() {
        return allowToPass;
    }
}
