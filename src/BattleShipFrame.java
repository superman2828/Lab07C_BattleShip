import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BattleShipFrame extends JFrame implements ActionListener {

    private BattleShipTile[][] board = new BattleShipTile[10][10];
    private String[][] gameBoard = new String[10][10];


    private JButton quitButton, resetButton;
    private JPanel statsPanel;
    private JLabel missLbl, strikeLbl, totalMissLbl, totalHitLbl;
    private JTextField missTxt, strikeTxt, totalMissTxt, totalHitTxt;
    private int[] ships = {5,4,3,3,2};
    private int shipsRemaining = 5;
    private int[][] shipBoard = new int[10][10];   // stores ship IDs
    private int[] shipHealth = {5,4,3,3,2};
    int miss = 0, strike = 0, totalMiss = 0, totalHits = 0;

    public BattleShipFrame() {
        setTitle("Battle Ship");
        setSize(600,600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(10,10));

        for(int row=0; row<10; row++){
            for(int col=0; col<10; col++){
                board[row][col] = new BattleShipTile(row,col);
                board[row][col].setFont(new Font("Arial",Font.BOLD,24));
                board[row][col].setText("~");
                board[row][col].setForeground(Color.BLUE);
                board[row][col].addActionListener(this);
                boardPanel.add(board[row][col]);
                gameBoard[row][col] = " ";
                shipBoard[row][col] = -1;
            }
        }
        add(boardPanel,BorderLayout.CENTER);

        createStatsPanel();
        add(statsPanel,BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        resetButton = new JButton("Play Again");
        resetButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset the game?", "Reset Game", JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
                resetGame();
            }
        });
        buttonPanel.add(resetButton);
        buttonPanel.add(quitButton);

        add(buttonPanel,BorderLayout.SOUTH);

        placeShips();

        setVisible(true);
    }

    public void createStatsPanel(){
        statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2,4));
        statsPanel.setSize(600,50);
        missLbl = new JLabel("Miss:");
        missTxt = new JTextField(0 + "");
        missTxt.setEditable(false);
        strikeLbl = new JLabel("Strike:");
        strikeTxt = new JTextField(0 + "");
        strikeTxt.setEditable(false);
        totalMissLbl = new JLabel("Total Misses:");
        totalMissTxt = new JTextField(0 + "");
        totalMissTxt.setEditable(false);
        totalHitLbl = new JLabel("Total Hits:");
        totalHitTxt = new JTextField(0 + "");
        totalHitTxt.setEditable(false);
        statsPanel.add(missLbl);
        statsPanel.add(missTxt);
        statsPanel.add(strikeLbl);
        statsPanel.add(strikeTxt);
        statsPanel.add(totalMissLbl);
        statsPanel.add(totalMissTxt);
        statsPanel.add(totalHitLbl);
        statsPanel.add(totalHitTxt);
    }

    public void resetGame(){
        dispose();
        new BattleShipFrame();
    }


    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // Handle button clicks here
        BattleShipTile tile = (BattleShipTile) e.getSource();

        int row = tile.getRow();
        int col = tile.getCol();

        if(gameBoard[row][col].equals("X") || gameBoard[row][col].equals("M")){
            JOptionPane.showMessageDialog(this,"Illegal move!");
            return;
        }

        if(gameBoard[row][col].equals("S")){

            tile.setText("X");
            tile.setForeground(Color.RED);
            gameBoard[row][col] = "X";

            int shipID = shipBoard[row][col];
            shipHealth[shipID]--;

            totalHits++;
            miss = 0;

            totalHitTxt.setText(totalHits+"");
            missTxt.setText(miss+"");

            if(shipHealth[shipID] == 0){
                JOptionPane.showMessageDialog(this,"Ship sunk!");
                shipsRemaining--;
            }

            if(shipsRemaining == 0){
                int option = JOptionPane.showConfirmDialog(this,
                        "You win! Play again?", "Play Again", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    resetGame();
                }else{
                    System.exit(0);
                }
            }

        }
        else{

            gameBoard[row][col] = "M";
            tile.setText("M");
            tile.setForeground(Color.YELLOW);

            miss++;
            totalMiss++;

            missTxt.setText(miss+"");
            totalMissTxt.setText(totalMiss+"");

            if(miss == 5){
                strike++;
                miss = 0;

                strikeTxt.setText(strike+"");
                missTxt.setText(miss+"");

                if(strike == 3){
                    int option = JOptionPane.showConfirmDialog(this,
                            "You lost! Play again?", "Play Again", JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.YES_OPTION){
                        resetGame();
                    }else{
                        System.exit(0);
                    }
                }
            }
        }
    }

    public void placeShips(){

        int[] shipSizes = {5,4,3,3,2};

        for(int shipID = 0; shipID < shipSizes.length; shipID++){

            int size = shipSizes[shipID];
            boolean placed = false;

            while(!placed){

                int row = (int)(Math.random()*10);
                int col = (int)(Math.random()*10);
                boolean horizontal = Math.random() < 0.5;

                if(horizontal){

                    if(col + size > 10) continue;

                    boolean valid = true;

                    for(int i=0;i<size;i++){
                        if(shipBoard[row][col+i] != -1){
                            valid = false;
                            break;
                        }
                    }

                    if(valid){
                        for(int i=0;i<size;i++){
                            shipBoard[row][col+i] = shipID;
                            gameBoard[row][col+i] = "S";
                        }
                        placed = true;
                    }

                } else {

                    if(row + size > 10) continue;

                    boolean valid = true;

                    for(int i=0;i<size;i++){
                        if(shipBoard[row+i][col] != -1){
                            valid = false;
                            break;
                        }
                    }

                    if(valid){
                        for(int i=0;i<size;i++){
                            shipBoard[row+i][col] = shipID;
                            gameBoard[row+i][col] = "S";
                        }
                        placed = true;
                    }
                }
            }
        }
    }

    public void checkShipSunk(){
        int[] shipSizes = {5,4,3,3,2};

        for(int size : shipSizes){

            int hits = 0;

            for(int r=0;r<10;r++){
                for(int c=0;c<10;c++){
                    if(gameBoard[r][c].equals("X")){
                        hits++;
                    }
                }
            }

            if(hits == size){
                JOptionPane.showMessageDialog(this,"Ship sunk!");
            }
        }
    }

}
