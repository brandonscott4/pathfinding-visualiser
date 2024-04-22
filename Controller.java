import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//maybe utilise abstract class or interface for pathfinding algos
public class Controller {
    private GridGUI gui;
    private Graph graph;

    public Controller(GridGUI gui, Graph graph){
        this.graph = graph;
        this.gui = gui;
        this.graph.addObserver(this.gui);

        int gridN = graph.getGridN();

        for(int i=0; i<gridN; i++){
            for(int j=0; j<gridN; j++){
                final int row = i;
                final int col = j;
                this.gui.addCellActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        boolean isBlock = gui.setBlockCell(row, col);
                        graph.modifyEdges(row, col, isBlock);
                        //System.out.println(row + ", " + col);
                    }
                }, row, col);
            }
        }

        int gridMiddleRowIndex = (int) Math.ceil(gridN / 2);
        this.gui.setStartDestination(new int[] {gridMiddleRowIndex, 0}, new int[] {gridMiddleRowIndex, gridN-1});

        this.gui.addStartActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //disable grid?
                gui.setStartEnabled(false);
                gui.clearGrid();

                //NOTE: eventually could read input from dropdown to determine which pathfinding algo/worker to run
                String algo = gui.getAlgo();
            
                Thread dfsThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            switch (algo) {
                                case "DFS":
                                    graph.dfs();
                                    break;
                                case "BFS":
                                    graph.bfs();
                                    break;
                            }
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } finally {
                            //enable grid?
                            gui.setStartEnabled(true);
                        }
                    }
                });

                dfsThread.start();
            }
        });
    }

    }
