package Sudoku_Solver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Sudoku {
    static List<HashSet<Integer>> rowHs;
    static List<HashSet<Integer>> colHs;
    static List<HashSet<Integer>> boxHs;
    static final int n= 9;
    static int count;
    static JLabel[][] jLabel;
    static int[][] board;

    static class myFrame extends JFrame{
        myFrame(){
            jLabel = new JLabel[n][n];
            board= new int[n][n];

            setVisible(true);
            setSize(500,500);

            setLayout(new GridLayout(n, n));
            setTitle("Sudoku Solver Visualization");
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    jLabel[i][j] = new JLabel();
                    jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                    jLabel[i][j].setOpaque(true);
                    jLabel[i][j].setFont(new Font("Arial", Font.BOLD, 16));
                    jLabel[i][j].setForeground(Color.WHITE);
                    jLabel[i][j].setBackground((i + j) % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);

                    //  Adding borders
                    int top = i % 3 == 0 ? 3 : 1;
                    int left = j % 3 == 0 ? 3 : 1;
                    int bottom = i == n - 1 ? 3 : 0;
                    int right = j == n - 1 ? 3 : 0;
                    jLabel[i][j].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                    add(jLabel[i][j]);
                }
            }
        }
    }

    public static void main( String args[] ){
        count= 0;
        rowHs= new ArrayList<>();
        colHs= new ArrayList<>();
        boxHs= new ArrayList<>();

        for(int i=0; i<9; i++){
            rowHs.add(new HashSet<>());
            colHs.add(new HashSet<>());
            boxHs.add(new HashSet<>());
        }

        myFrame obj= new myFrame();

        //  Adding the API array values to board array
        addToBoard(args[0]);

        solve(board, count);
    }

    public static void addToBoard(String s){
        s=s.replace("[","");
        s=s.substring(0,s.length()-2);
        String s1[]=s.split("],");

        for(int i=0; i<9; i++){
            s1[i]=s1[i].trim();
            String val[]=s1[i].split("[,]");

            for(int j=0; j<9; j++){
                if(val[j].equals("0") == false){
                    board[i][j]= Integer.parseInt(val[j]);
                    jLabel[i][j].setText(val[j]);

                    int c= board[i][j];
                    int boxNum= (3*(i/3))+(3+j)/3 -1;

                    rowHs.get(i).add(c);
                    colHs.get(j).add(c);
                    boxHs.get(boxNum).add(c);

                    count++;
                }
            }
        }
    }

    public static boolean solve(int[][] board, int count){
        if(count == 81){
            return true;
        }

        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){

                if(board[i][j] == 0){
                    for(int c= 1; c<= 9; c++){
                        wait(1);

                        int boardNum= (3*(i/3))+(3+j)/3 -1;

                        if( check(i, j, c) ){
                            rowHs.get(i).add(c);
                            colHs.get(j).add(c);
                            boxHs.get(boardNum).add(c);
                            board[i][j]= c;
                            jLabel[i][j].setText(c+"");
                            jLabel[i][j].setForeground(Color.RED);

                            if(solve(board, count+1)){
                                return true;
                            }

                            jLabel[i][j].setText("");
                            board[i][j]= 0;
                            rowHs.get(i).remove(c);
                            colHs.get(j).remove(c);
                            boxHs.get(boardNum).remove(c);
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }

    public static boolean check(int x, int y, int c){
        int boardNum= (3*(x/3))+((3+y)/3) -1;

        if( rowHs.get(x).contains(c) || colHs.get(y).contains(c) || boxHs.get(boardNum).contains(c)){
            return false;
        }
        return true;
    }

    public static void wait(int time_ms){
        try {
            Thread.sleep(time_ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}