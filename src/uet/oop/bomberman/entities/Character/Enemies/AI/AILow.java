package uet.oop.bomberman.entities.Character.Enemies.AI;


public class AILow extends ai {

    @Override
    public int calculateDirection() {
        // TODO: cài đặt thuật toán tìm đường đi
        return random.nextInt(4);
    }

}