import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//maybe utilise abstract class or interface for pathfinding algos
public class Controller {
    private GridGUI gui;
    private Model model;

    public Controller(GridGUI gui, Model model){
        this.model = model;
        this.gui = gui;
        this.model.addObserver(this.gui);

        int gridN = model.getGraph().getGridN();

        for(int i=0; i<gridN; i++){
            for(int j=0; j<gridN; j++){
                final int row = i;
                final int col = j;
                this.gui.addCellActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        boolean isBlock = gui.setBlockCell(row, col);
                        model.getGraph().modifyEdges(row, col, isBlock);
                        //System.out.println(row + ", " + col);
                    }
                }, row, col);
            }
        }

        int gridMiddleRowIndex = (int) Math.ceil(gridN / 2);
        this.gui.setStartDestination(new int[] {gridMiddleRowIndex, 0}, new int[] {gridMiddleRowIndex, gridN-1});

        this.gui.addStartActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gui.setGUIEnabled(false);
                gui.clearGrid();

                String algo = gui.getAlgo();
            
                Thread dfsThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            switch (algo) {
                                case "DFS":
                                    model.dfs();
                                    break;
                                case "BFS":
                                    model.bfs();
                                    break;
                                case "Dijkstra's":
                                    model.dijkstras();
                                    break;
                                case "A*":
                                    model.astar();
                                    break;
                            }
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } finally {
                            gui.setGUIEnabled(true);
                        }
                    }
                });

                dfsThread.start();
            }
        });
    }

    }
