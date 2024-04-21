import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.SwingWorker;


//maybe refactor to Observer pattern
//maybe utilise abstract class or interface for pathfinding algos
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
                        //System.out.println(row + ", " + col);
                    }
                }, row, col);
            }
        }

        int gridMiddleRowIndex = (int) Math.ceil(gridN / 2);
        this.gui.setStartDestination(new int[] {gridMiddleRowIndex, 0}, new int[] {gridMiddleRowIndex, gridN-1});

        this.gui.addStartActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //need to disable the button
                gui.clearGrid();

                //NOTE: eventually could read input from dropdown to determine which pathfinding algo/worker to run
                SwingWorker<Boolean, Integer> pathFindingWorker = createPathFindingWorker();
                pathFindingWorker.execute();
                //then re enable here
            }
        });
    }

    public SwingWorker<Boolean, Integer> createPathFindingWorker(){
        return new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws InterruptedException{
                Stack<Integer> validPath = new Stack<>();
                HashMap<Integer, Integer> parentMap = new HashMap<>(); 
                int nodes = graph.getNodes();

                Stack<Integer> stack = new Stack<>();
                boolean[] visited = new boolean[nodes];

                int currentCell = graph.getStartCell();
                visited[currentCell] = true;

                for(int i=0; i<nodes; i++){
                    if(visited[i] == false && graph.getAdjacencyMatrix()[currentCell][i] == 1){
                        stack.push(i);
                        parentMap.put(i, currentCell);
                    }
                }

                while(!stack.isEmpty()){
                    if(stack.peek() == graph.getDestinationCell()){
                        System.out.println("found");
                        Integer curr = stack.pop();
                        //maybe move this to done function
                        while(curr != null){
                            validPath.push(curr);
                            curr = parentMap.get(curr);
                        }
                        gui.clearGrid();
                        gui.setValidPath(validPath, graph.getGridN());
                        return true;
                    }

                    currentCell = stack.pop();
                    visited[currentCell] = true;
                    publish(currentCell);

                    for(int i=0; i<nodes; i++){
                        if(visited[i] == false && graph.getAdjacencyMatrix()[currentCell][i] == 1){
                            stack.push(i);
                            parentMap.put(i, currentCell);
                        }
                    }

                    Thread.sleep(50);
                }

                System.out.println("not found");
                return false;
            }

            @Override
            protected void process(List<Integer> chunks ){
                for(int visitedCell : chunks){
                    gui.setVisitedCell(visitedCell / graph.getGridN(), visitedCell % graph.getGridN());
                }
            }
        };
    } 

    }
