import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GridGUI {
    private JFrame frame;
    private JPanel gridPanel;
    private JButton[][] cellArray;

    public GridGUI(int gridN){

        frame = new JFrame("PathFinding Visualiser");

        gridPanel = new JPanel(new GridLayout(gridN, gridN));

        cellArray = new JButton[gridN][gridN];

        for(int i=0; i<gridN; i++){
            final int indexI = i;
            for(int j=0; j<gridN; j++){
                final int indexJ = j;
                JButton gridCell = new JButton();
                gridCell.setPreferredSize(new Dimension(50,50));
                cellArray[indexI][indexJ] = gridCell;

                gridPanel.add(cellArray[indexI][indexJ]);
            }
        }
        
        frame.add(gridPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void addCellActionListener(ActionListener listener, int i, int j){
        cellArray[i][j].addActionListener(listener);
    }

    public boolean setBlockCell(int i, int j){
        if(cellArray[i][j].getBackground() == Color.BLACK){
            cellArray[i][j].setBackground(null);
            return false;
        } else {
            cellArray[i][j].setBackground(Color.BLACK);
            return true;
        }
    }

    public void setStartDestination(int[] start, int[] destination){
        cellArray[start[0]][start[1]].setBackground(Color.BLUE);
        cellArray[start[0]][start[1]].setEnabled(false);

        cellArray[destination[0]][destination[1]].setBackground(Color.GREEN);
        cellArray[destination[0]][destination[1]].setEnabled(false);
    }

}
