package enumeration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

/**
 * Created by chuck on 8/22/15.
 */
public class PainterPain2 {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        try {
            scan = new Scanner(new FileInputStream(
                    new File("./data/enumeration/painters_pain")));
        }catch (Exception e) {
            e.printStackTrace();
        }
        int cases = Integer.valueOf(scan.nextLine());
        for (int k = 0;k < cases;k++) {
            int n = Integer.valueOf(scan.nextLine());
            int[][] wall = new int[n + 2][n + 2];
            for (int r = 1; r <= n; r++) {
                String line = scan.nextLine();
                for (int c = 1; c <= n; c++) {
                    if (line.charAt(c - 1) == 'y') {
                        wall[r][c] = 1;
                    }
                }
            }

            int result = guess(wall, n);
            if (result != Integer.MAX_VALUE) {
                System.out.println(result);
            } else {
                System.out.println("inf");
            }
        }
        scan.close();
    }

    /**
     * given a wall and it's size n,
     * generate 2^n possible enumeration of the first row of bricks on the wall and
     * (when we say possible enumeration, it means brush-or-not actions)
     * then check if each enumeration leads to an "totally yellow wall"
     * if so, cache the minimal times of brush action.
     *
     * @param wall 2 dimensions integer array to represent color of each brick on the wall
     * @param n size of the wall (n * n)
     * @return minimal times of brush need to take
     */
    public static int guess(int[][] wall, int n) {

        int result = Integer.MAX_VALUE;
        for (int i = 0; i < Math.pow(2, n); i++) {
            int[][] copy = new int[wall.length][];
            for (int m = 0; m < wall.length; m++) {
                copy[m] = new int[wall[m].length];
                System.arraycopy(wall[m], 0, copy[m], 0, wall[m].length);
            }
            int count = enum1stLine(copy, i);
            count = check(copy, count);
            if (result > count) {
                result = count;
            }
        }
        return result;
    }

    /**
     * As the brush-or-not actions of the first row of bricks on wall were enumerated,
     * now check if this enumerated situation could be the answer we want.
     *
     * @param wall 2 dimensions integer array
     * @param count times of brush that has taken on the first row of bricks on the wall
     * @return minimal times of brush need to take
     */
    public static int check(int[][] wall, int count) {

        for (int i = 2; i < wall.length - 1; i++) {
            for (int j = 1; j < wall[i].length - 1; j++) {
                if (wall[i - 1][j] == 0) {
                    brush(wall, i, j);
                    count++;
                }
            }
        }
        for (int c = 1; c < wall[wall.length - 2].length - 1; c++) {
            if (wall[wall.length - 2][c] == 0) {
                return Integer.MAX_VALUE;
            }
        }
        return count;
    }

    /**
     * simulate a possible situation of the first row of the wall to brush,
     * based on a given simulation-generator key.
     * (on extreme situation, this key could be an int no bigger than n and
     * use its binary code to represent the simulation sequence)
     *
     * @param wall 2 dimensions integer array
     * @param k simulation-generator key, which helps represent a simulation sequence
     * @return times of brush that has taken on the first row of bricks on the wall
     */
    public static int enum1stLine(int[][] wall, int k) {

        int count = 0;
        for (int c = 1; k > 0; c++) {
            if (k % 2 == 1) {
                brush(wall, 1, c);
                count++;
            }
            k /= 2;
        }
        return count;
    }

    /**
     * simulate the brush's effects,
     * switch the color of 5 bricks(center, up, down, left and right)
     * @param wall 2 dimensions integer array
     * @param x coordinator-x of the brick to be brushed
     * @param y coordinator-y of the brick to be brushed
     */
    public static void brush(int[][] wall, int x, int y) {

        wall[x][y] = switchColor(wall[x][y]);
        wall[x - 1][y] = switchColor(wall[x - 1][y]);
        wall[x + 1][y] = switchColor(wall[x + 1][y]);
        wall[x][y - 1] = switchColor(wall[x][y - 1]);
        wall[x][y + 1] = switchColor(wall[x][y + 1]);
    }

    /**
     * switch a brick's original color to the other(white to yellow or yellow to white)
     * @param origin original color(0: white, 1: yellow)
     * @return switched color
     */
    public static int switchColor(int origin) {

        return origin == 0 ? 1 : 0;
    }
}
