import javax.swing.*;
import java.awt.event.ActionListener;

public class BattleShipFrame extends JFrame implements ActionListener {

    private BattleShipTile[][] playerBoard = new BattleShipTile[10][10];
    private BattleShipTile[][] opponentBoard = new BattleShipTile[10][10];

    public BattleShipFrame() {
        setTitle("Battle Ship");
        setSize(800,400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBounds(50, 50, 300, 300);

        for(int row=0; row<10; row++){
            for(int col=0; col<10; col++){
                playerBoard[row][col] = new BattleShipTile(row,col);
                playerBoard[row][col].setText(" ");
                playerBoard[row][col].addActionListener(this);
                playerPanel.add(playerBoard[row][col]);
            }
        }

        add(playerPanel);

        JPanel opponentPanel = new JPanel();
        opponentPanel.setLayout(new BoxLayout(opponentPanel, BoxLayout.Y_AXIS));
        opponentPanel.setBounds(450, 50, 300, 300);

        for(int row=0; row<10; row++){
            for(int col=0; col<10; col++){
                opponentBoard[row][col] = new BattleShipTile(row,col);
                opponentBoard[row][col].setText(" ");
                opponentBoard[row][col].addActionListener(this);
                opponentPanel.add(opponentBoard[row][col]);
            }
        }

        add(opponentPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // Handle button clicks here
    }
}
