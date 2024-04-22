import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Stack;

//Observer
public class GridGUI {
    private JFrame frame;
    private JPanel container;
    private JPanel gridPanel;
    private JPanel controlPanel;
    private JButton[][] cellArray;
    private JButton start;
    private JComboBox<String> selectAlgo;

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
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        start = new JButton("Start");
        selectAlgo = new JComboBox<>(new String[] {"BFS" ,"DFS"});
        selectAlgo.setMaximumSize(new Dimension(150, 30));

        start.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectAlgo.setAlignmentX(Component.LEFT_ALIGNMENT);

        controlPanel.add(start);
        controlPanel.add(Box.createVerticalStrut(25));
        controlPanel.add(selectAlgo);
        controlPanel.add(Box.createVerticalGlue());

        container.add(gridPanel);
        container.add(controlPanel);

        frame.add(container);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
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

    private void setVisitedCell(int i, int j){
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

    private void setValidPath(Stack<Integer> validPath, int gridN) throws InterruptedException{
        //need to not paint start and end destination
        validPath.pop();
        while(validPath.size() != 1){
            int cell = validPath.pop();
            cellArray[cell / gridN][cell % gridN].setBackground(Color.YELLOW);
            Thread.sleep(50);
        }
    }

    public void update(){
        clearGrid();
    }

    public void update(int i, int j){
        //Update view with new data
        setVisitedCell(i, j);
    }

    public void update(Stack<Integer> validPath, int gridN) throws InterruptedException{
        setValidPath(validPath, gridN);
    }

    public void setStartEnabled(boolean enabled){
        start.setEnabled(enabled);
    }

    //probably shouldnt be here
    public String getAlgo(){
        return (String) selectAlgo.getSelectedItem();
    }
}
