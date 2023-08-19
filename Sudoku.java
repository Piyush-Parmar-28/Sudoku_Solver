package Sudoku_Solver;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Sudoku {
    static List<HashSet<Integer>> rowHs;
    static List<HashSet<Integer>> colHs;
    static List<HashSet<Integer>> boxHs;
    static final int n= 9;
    static JLabel[][] jLabel;
    static int[][] board;

    static class myFrame extends JFrame{
        myFrame(){
            jLabel = new JLabel[n][n];
            board= new int[n][n];

            setVisible(true);
            setSize(500,500);
            System.out.println("n: "+ n);

            setLayout(new GridLayout(n, n, 2, 2));
            setTitle("Sudoku Solver Visualization");
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    jLabel[i][j] = new JLabel();
                    jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                    jLabel[i][j].setOpaque(true);
                    jLabel[i][j].setFont(new Font("Arial", Font.BOLD, 16));
                    jLabel[i][j].setForeground(Color.WHITE);
                    jLabel[i][j].setBackground((i + j) % 2 == 0 ? Color.GRAY : Color.DARK_GRAY);

                    add(jLabel[i][j]);
                }
            }
        }
    }

    public static void main( String args[] ){
        rowHs= new ArrayList<>();
        colHs= new ArrayList<>();
        boxHs= new ArrayList<>();

        myFrame obj= new myFrame();

        for(int i=0; i<9; i++){
            rowHs.add(new HashSet<>());
            colHs.add(new HashSet<>());
            boxHs.add(new HashSet<>());
        }

        board[0][0]= 5;
        board[0][1]= 3;
        board[0][4]= 7;
        jLabel[0][0].setText(5+"");
        jLabel[0][1].setText(3+"");
        jLabel[0][4].setText(7+"");

        board[1][0]= 6;
        board[1][3]= 1;
        board[1][4]= 9;
        board[1][5]= 5;
        jLabel[1][0].setText(6+"");
        jLabel[1][3].setText(1+"");
        jLabel[1][4].setText(9+"");
        jLabel[1][5].setText(5+"");

        board[2][1]= 9;
        board[2][2]= 8;
        board[2][7]= 6;
        jLabel[2][1].setText(9+"");
        jLabel[2][2].setText(8+"");
        jLabel[2][7].setText(6+"");

        board[3][0]= 8;
        board[3][4]= 6;
        board[3][8]= 3;
        jLabel[3][0].setText(8+"");
        jLabel[3][4].setText(6+"");
        jLabel[3][8].setText(3+"");

        board[4][0]= 4;
        board[4][3]= 8;
        board[4][5]= 3;
        board[4][8]= 1;
        jLabel[4][0].setText(4+"");
        jLabel[4][3].setText(8+"");
        jLabel[4][5].setText(3+"");
        jLabel[4][8].setText(1+"");

        board[5][0]= 7;
        board[5][4]= 2;
        board[5][8]= 6;
        jLabel[5][0].setText(7+"");
        jLabel[5][4].setText(2+"");
        jLabel[5][8].setText(6+"");

        board[6][1]= 6;
        board[6][6]= 2;
        board[6][7]= 8;
        jLabel[6][1].setText(6+"");
        jLabel[6][6].setText(2+"");
        jLabel[6][7].setText(8+"");

        board[7][3]= 4;
        board[7][4]= 1;
        board[7][5]= 9;
        board[7][8]= 5;
        jLabel[7][3].setText(4+"");
        jLabel[7][4].setText(1+"");
        jLabel[7][5].setText(9+"");
        jLabel[7][8].setText(5+"");

        board[8][4]= 8;
        board[8][7]= 7;
        board[8][8]= 9;
        jLabel[8][4].setText(8+"");
        jLabel[8][7].setText(7+"");
        jLabel[8][8].setText(9+"");


        int count= 0;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){

                if(board[i][j] != 0){
                    int c= board[i][j];
                    int boxNum= (3*(i/3))+(3+j)/3 -1;

                    rowHs.get(i).add(c);
                    colHs.get(j).add(c);
                    boxHs.get(boxNum).add(c);

                    count++;
                }
            }
        }

        solve(board, count);
    }

    public static boolean solve(int[][] board, int count){
        if(count == 81){
            return true;
        }

        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){

                if(board[i][j] == 0){
                    for(int c= 1; c<= 9; c++){
                        int boardNum= (3*(i/3))+(3+j)/3 -1;

                        if( check(i, j, c) ){
                            rowHs.get(i).add(c);
                            colHs.get(j).add(c);
                            boxHs.get(boardNum).add(c);
                            board[i][j]= c;
                            jLabel[i][j].setText(c+"");

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
}