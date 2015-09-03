package enumeration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

/**
 * Created by chuck on 8/8/15.
 */
public class PaintersPain {

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
            Brick[][] wall = new Brick[n + 2][n + 2];
            init(wall);
            for (int r = 1; r <= n; r++) {
                String line = scan.nextLine();
                for (int c = 1; c <= n; c++) {
                    if (line.charAt(c - 1) == 'w') {
                        wall[r][c].color = 1;
                    }
                }
            }
            for (int r = 1; r<= n; r++) {
                for (int c = 1;c <= n; c++) {
                    checkInfluence(wall, r, c);
                }
            }
            Brick brick = pick(wall);
            int sum = 0;
            while (brick.influence > 0) {
                brush(wall, brick.r, brick.c);
                sum++;
                brick = pick(wall);
            }
            System.out.println(sum);
        }
        scan.close();
    }

    private static class Brick {
        public int r = 0;
        public int c = 0;
        public int color = 0;  //0:yellow, 1:white
        public int influence = 0;

        public Brick(int r, int c) {
            this.r = r;
            this.c = c;
        }

    }

    public static void init(Brick[][] wall) {
        for (int i = 0;i < wall.length;i ++) {
            for (int j = 0;j < wall[i].length;j ++) {
                wall[i][j] = new Brick(i, j);
            }
        }
    }

    public static void checkInfluence(Brick[][] wall, int i, int j) {
        if (wall[i - 1][j].color== 1) {
            wall[i][j].influence++;
        }
        if (wall[i + 1][j].color == 1) {
            wall[i][j].influence++;
        }
        if (wall[i][j - 1].color == 1) {
            wall[i][j].influence++;
        }
        if (wall[i][j + 1].color == 1) {
            wall[i][j].influence++;
        }
    }

    public static void brush(Brick[][] wall, int i, int j) {

        switchColor(wall, i - 1, j);
        switchColor(wall, i + 1, j);
        switchColor(wall, i, j - 1);
        switchColor(wall, i, j + 1);
        switchColor(wall, i, j);
        wall[i][j].influence = 0;
    }

    public static void switchColor(Brick[][] wall, int i, int j) {

        if (wall[i][j].color == 1) {
            wall[i - 1][j].influence--;
            wall[i + 1][j].influence--;
            wall[i][j - 1].influence--;
            wall[i][j + 1].influence--;
        }
        wall[i][j].color = 0;
    }

    public static Brick pick(Brick[][] wall) {
        Brick brick = wall[1][1];
        for (int i = 1;i < wall.length - 1; i ++) {
            for (int j = 1;j < wall[i].length - 1; j ++) {
                if (wall[i][j].influence - brick.influence > 0) {
                    brick = wall[i][j];
                }
            }
        }
        return brick;
    }

}
