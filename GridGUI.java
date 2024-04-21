import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Stack;

public class GridGUI {
    private JFrame frame;
    private JPanel container;
    private JPanel gridPanel;
    private JPanel controlPanel;
    private JButton[][] cellArray;
    private JButton start;

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
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        controlPanel = new JPanel();
        start = new JButton("Start");
        controlPanel.add(start);
        
        container.add(gridPanel);
        container.add(controlPanel);

        frame.add(container);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void addCellActionListener(ActionListener listener, int i, int j){
        cellArray[i][j].addActionListener(listener);
    }

    public void addStartActionListener(ActionListener listener){
        start.addActionListener(listener);
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

    public void setVisitedCell(int i, int j){
        cellArray[i][j].setBackground(Color.RED);
    }

    public void clearGrid(){
        for(int i=0; i<cellArray.length; i++){
            for(int j=0; j<cellArray.length; j++){
                if(cellArray[i][j].getBackground() == Color.RED || cellArray[i][j].getBackground() == Color.YELLOW){
                    cellArray[i][j].setBackground(null);
                }
            }
        }
    }

    public void setValidPath(Stack<Integer> validPath, int gridN) throws InterruptedException{
        //need to not paint start and end destination
        validPath.pop();
        while(validPath.size() != 1){
            int cell = validPath.pop();
            cellArray[cell / gridN][cell % gridN].setBackground(Color.YELLOW);
            Thread.sleep(50);
        }
    }
}
