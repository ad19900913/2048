package tech.sisyphus.controller;


import tech.sisyphus.config.Recorder;
import tech.sisyphus.model.Block;
import tech.sisyphus.view.MainPanel;

import java.util.ArrayList;
import java.util.List;

/*
 * 方块移动时的策略   分为两大步1.跨越空白格  2.融合
 * 方块B向某个方向上移动，如果该方向上下一步为空，则互相交换数值
 * 								     下一步碰到边界，不动
 *                          	     下一步碰到方块如果数值不同，不动
 *                          				       如果数值相同，融合
 *
 */
public class GameControl {
    //移动方向
    public int direction;
    //是否有方块移动
    public boolean anyblockMove = false;
    //表示当前自动填充数字的方块坐标，每次移动后刷新
    private int x, y;
    private MainPanel mainPanel;

    public GameControl(MainPanel mainPanel) {
        super();
        this.mainPanel = mainPanel;
    }

    /*
     * 此方法是合法移动后才会执行的
     * 如果在按键方向上没有任何方块被移动，则不会生成新方块
     * flag用来标识这次移动是不是有效的移动
     */
    public boolean setBlock(boolean flag) {
        if (flag) {
            List<Block> list = new ArrayList<Block>();
            x = (int) (Math.random() * 4 + 1);
            y = (int) (Math.random() * 4 + 1);
            for (Block block : mainPanel.blocks) {
                if (block.value == 0) {
                    list.add(block);
                }
            }
            int index = (int) (Math.random() * list.size());
            list.get(index).value = 2;
            clearMergeFlag();
            anyblockMove = false;
            return true;
        }
        return false;
    }

    private void clearMergeFlag() {
        for (Block block : mainPanel.blocks) {
            block.canMerge = true;
        }
    }

    //交换的时候只需要交换VALUE
    public void exchange(Block b1, Block b2) {
        b2.value = b1.value;
        b1.value = 0;
    }

    /**
     * b1向b2融合
     * 但是在一次移动中
     * 每一行或者每一列只能有一次融合
     *
     * @param b1
     * @param b2
     * @param flag 标记本次移动是否计分
     */
    public void merge(Block b1, Block b2, boolean flag) {

        if (b1.canMerge && b2.canMerge) {
            b1.canMerge = false;
            b2.canMerge = false;
            b1.value = 0;
            b2.value = 2 * b2.value;
            if (flag) {
                Recorder.S_MAX_NUM = Math.max(Recorder.S_MAX_NUM, b2.value);
                Recorder.S_SCORES = Recorder.S_SCORES + b2.value;
            }
        }

    }

    //找到下一块特定坐标的砖块，与方向有关
    public Block getBlock(Block block, int dir) {
        int targetX = block.x;//用来标识要找的目标块的坐标
        int targetY = block.y;
        switch (dir) {
            case 1:
                while (getBlock(targetX, targetY - 1).value == 0) {
                    targetY -= 1;
                    //进入while循环意味着方块至少移动了一次
                    anyblockMove = true;
                }
                return getBlock(targetX, targetY);
            case 2:
                while (getBlock(targetX, targetY + 1).value == 0) {
                    targetY += 1;
                    anyblockMove = true;
                }
                return getBlock(targetX, targetY);
            case 3:
                while (getBlock(targetX - 1, targetY).value == 0) {
                    targetX -= 1;
                    anyblockMove = true;
                }
                return getBlock(targetX, targetY);
            case 4:
                while (getBlock(targetX + 1, targetY).value == 0) {
                    targetX += 1;
                    anyblockMove = true;
                }
                return getBlock(targetX, targetY);
            default:
                return null;
        }

    }

    //获取特定坐标的Block对象
    public Block getBlock(int x, int y) {
        for (Block block : mainPanel.blocks) {
            if (block.x == x && block.y == y) {
                return block;
            }
        }
        Block temp = new Block(5, 5);
        temp.value = 100;
        return temp;
    }

    //处理方块运动的方法
    public void move(int direction, boolean flag) {
        anyblockMove = false;
        /*
         * 下面是对于每一个有数字的方块进行尝试移动
         * 要实现一次按键可以使得方块从最左边移动到最右边，
         *
         * 而且需要注意的是 一次有效的移动过程中  每一行或者每一列上最好只能有一次
         * 数值相同的融合，否则游戏难度会降低。
         * 就是 2 2 4 8        移动后应该变成
         *     0 4 4 8        而不是直接变成
         *     0 0 0 16
         *
         */
        //如果向上移动，那么是从最上面一行开始逐行往下计算 方块的移动
        if (direction == 1) {
            for (int j = 1; j < 5; j++) {
                for (int j2 = 1; j2 < 5; j2++) {
                    Block blockOne = getBlock(j2, j);
                    if (blockOne.value != 0) {//只尝试移动有数值的方块
                        /*
                         * 下面一步应该能够跨越方块  比如向右的移动中
                         * 能从 2 0 0 4 的2直接移动成 0 0 2 4 的格局
                         * 实现一次性移动两格
                         * 下面这一句的功能是直接定位
                         */
                        Block blockTwo = getBlock(blockOne, direction);
                        //先交换一下，在处理
                        if (!blockOne.equals(blockTwo)) {
                            exchange(blockOne, blockTwo);//交换完成后主块变成block_2
                            anyblockMove = true;
                        }

                        Block temp = getBlock(blockTwo.x, blockTwo.y - 1);
                        if (blockTwo.value == temp.value) {
                            merge(blockTwo, temp, flag);
                            anyblockMove = true;
                        }
                    }
                }
            }
        }

        if (direction == 2) {
            for (int j = 4; j > 0; j--) {
                for (int j2 = 1; j2 < 5; j2++) {
                    Block blockOne = getBlock(j2, j);
                    if (blockOne.value != 0) {//只尝试移动有数值的方块
                        Block blockTwo = getBlock(blockOne, direction);
                        if (!blockOne.equals(blockTwo)) {
                            exchange(blockOne, blockTwo);//交换完成后主块变成block_2
                            anyblockMove = true;
                        }

                        Block temp = getBlock(blockTwo.x, blockTwo.y + 1);
                        if (blockTwo.value == temp.value) {
                            merge(blockTwo, temp, flag);
                            anyblockMove = true;
                        }
                    }
                }
            }
        }

        if (direction == 4) {
            for (int j = 4; j > 0; j--) {
                for (int j2 = 1; j2 < 5; j2++) {
                    Block blockOne = getBlock(j, j2);
                    if (blockOne.value != 0) {//只尝试移动有数值的方块
                        Block blockTwo = getBlock(blockOne, direction);
                        if (!blockOne.equals(blockTwo)) {
                            exchange(blockOne, blockTwo);//交换完成后主块变成block_2
                            anyblockMove = true;
                        }

                        Block temp = getBlock(blockTwo.x + 1, blockTwo.y);
                        if (blockTwo.value == temp.value) {
                            merge(blockTwo, temp, flag);
                            anyblockMove = true;
                        }
                    }
                }
            }
        }

        if (direction == 3) {
            for (int j = 1; j < 5; j++) {
                for (int j2 = 1; j2 < 5; j2++) {
                    Block blockOne = getBlock(j, j2);
                    if (blockOne.value != 0) {//只尝试移动有数值的方块
                        Block blockTwo = getBlock(blockOne, direction);
                        if (!blockOne.equals(blockTwo)) {
                            exchange(blockOne, blockTwo);//交换完成后主块变成block_2
                            anyblockMove = true;
                        }

                        Block temp = getBlock(blockTwo.x - 1, blockTwo.y);
                        if (blockTwo.value == temp.value) {
                            merge(blockTwo, temp, flag);
                            anyblockMove = true;
                        }
                    }
                }
            }
        }
    }

}
