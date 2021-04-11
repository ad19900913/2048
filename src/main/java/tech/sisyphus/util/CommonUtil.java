package tech.sisyphus.util;

import tech.sisyphus.model.Block;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

    public static Block[][] list2arr(List<Block> blocks) {
        Block[][] arr = new Block[4][4];
        for (Block block : blocks) {
            arr[block.y - 1][block.x - 1] = block;
        }
        return arr;
    }

    public static List<Block> arr2list(int[][] arr) {
        List<Block> blocks = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                Block block = new Block(i + 1, j + 1, arr[i][j]);
                blocks.add(block);
            }
        }
        return blocks;
    }

    //得到clone的集合
    public static List<Block> getCloneList(List<Block> blocks) {
        ArrayList<Block> temp = new ArrayList<>();
        for (Block block : blocks) {
            try {
                temp.add((Block) block.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

}
