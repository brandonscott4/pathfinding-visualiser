import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private GridGUI gui;
    private Graph graph;

    public Controller(GridGUI gui, Graph graph){
        this.gui = gui;
        this.graph = graph;

        int gridN = graph.getGridN();

        for(int i=0; i<gridN; i++){
            for(int j=0; j<gridN; j++){
                final int row = i;
                final int col = j;
                this.gui.addCellActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        boolean isBlock = gui.setBlockCell(row, col);
                        graph.modifyEdges(row, col, isBlock);
                        System.out.println(row + ", " + col);
                    }
                }, row, col);
            }
        }
    }
}
