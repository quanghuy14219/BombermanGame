package com.bomberman.entities.mobileEntites.enemies;

import com.bomberman.constants.Direction;
import com.bomberman.entities.mobileEntites.Player;

import java.util.ArrayList;
import java.util.List;

public class Moving {
    private Level level;
    private boolean wallPass;
    private boolean brickPass;
    private Player player;

    public static enum Level {
        LOW, MEDIUM, HIGH;
    }

    class Node {
        private int x, y;
        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
    public Moving(Level level , boolean brickPass, boolean wallPass) {
        this.level = level;
        this.wallPass = wallPass;
        this.brickPass = brickPass;
        player = Player.getPlayer();
    }

    public Direction movingDirection(char[][] matrix, int e_x, int e_y) {
        Direction direction = null;
        switch (level) {
            case LOW:
                direction = randomMoving(matrix, e_x, e_y);
                break;
            case MEDIUM:
                direction = wayToPlayer(matrix, e_x, e_y,
                        player.getX_node(), player.getY_node(),
                        level.MEDIUM);
                break;
            case HIGH:
                direction = wayToPlayer(matrix, e_x, e_y,
                        player.getX_node(), player.getX_node(),
                        level.MEDIUM);
                break;
        }
        return direction;
    }

    private Direction randomMoving(char[][] matrix, int e_x, int e_y) {
        Direction direction;
        do {
            direction = Direction.dir[(int)(Math.random() * 4)];
        } while (!movableDirection(matrix, e_x, e_y, direction));
        return direction;
    }

    private Direction wayToPlayer(char[][] matrix, int e_x, int e_y, int p_x, int p_y, Level level) {
        Direction direction = null;

        char[][] checkingMap = new char[matrix.length][matrix[0].length];
        for (int i = 0; i < checkingMap.length; i++) {
            for (int j = 0; j < checkingMap[0].length; j++) {
                if(movableNode(matrix, j, i)) {
                    checkingMap[i][j] = '1';
                } else {
                    checkingMap[i][j] = '0';
                }
            }
        }

        int tracingRange = 0;

        if (level == Level.MEDIUM || level == Level.HIGH) {
            tracingRange = 36;
        }

        boolean pathExits = false;

        if (Math.abs((e_x - p_x) * (e_y - p_y)) < tracingRange && level != Level.LOW) {
            List<Node> queue = new ArrayList<>();
            queue.add(new Node(p_x, p_y));

            while(!queue.isEmpty()) {
                Node lastNode = queue.remove(0);
                if (lastNode.x - 1 == e_x && lastNode.y == e_y) {
                    direction = Direction.RIGHT;
                    pathExits = true;
                    break;
                }
                if (lastNode.x + 1 == e_x && lastNode.y == e_y) {
                    direction = Direction.LEFT;
                    pathExits = true;
                    break;
                }
                if (lastNode.x == e_x && lastNode.y - 1 == e_y) {
                    direction = Direction.DOWN;
                    pathExits = true;
                    break;
                }
                if (lastNode.x == e_x && lastNode.y + 1 == e_y) {
                    direction = Direction.UP;
                    pathExits = true;
                    break;
                }
                try {
                    checkingMap[lastNode.y][lastNode.x] = '0';
                }
                catch (ArrayIndexOutOfBoundsException ignored) {
                }
                List<Node> neighbors = getNeighborNodes(checkingMap, lastNode);
                queue.addAll(neighbors);
            }
        }

        if (!pathExits) {
            direction = randomMoving(matrix, e_x, e_y);
        }
        return direction;
    }

    private List<Node> getNeighborNodes(char[][] matrix, Node node) {
        List<Node> neighbors = new ArrayList<>();
        if (validNode(matrix, node.x - 1, node.y)) {
            neighbors.add(new Node(node.x - 1, node.y));
        }
        if (validNode(matrix, node.x + 1, node.y)) {
            neighbors.add(new Node(node.x + 1, node.y));
        }
        if (validNode(matrix, node.x, node.y - 1)) {
            neighbors.add(new Node(node.x, node.y - 1));
        }
        if (validNode(matrix, node.x, node.y + 1)) {
            neighbors.add(new Node(node.x, node.y + 1));
        }
        return neighbors;
    }

    private boolean validNode(char[][] matrix, int x, int y) {
        return 0 <= x && x < matrix[0].length
                && 0 <= y && y < matrix.length
                && '0' != matrix[y][x];
    }

    private boolean movableNode(char[][] matrix, int x, int y) {
        return 0 <= x && x < matrix[0].length
                && 0 <= y && y < matrix.length
                && (' ' == matrix[y][x]
                || (brickPass && '*' == matrix[y][x])
                || (wallPass && '#' == matrix[y][x]));
    }

    private boolean movableDirection(char[][] matrix, int x, int y, Direction direction) {
        boolean move = false;
        switch (direction) {
            case UP:
                move = movableNode(matrix, x, y - 1);
                break;
            case DOWN:
                move = movableNode(matrix, x, y + 1);
                break;
            case LEFT:
                move = movableNode(matrix, x - 1, y);
                break;
            case RIGHT:
                move = movableNode(matrix, x + 1, y);
                break;
        }
        return move;
    }
}
