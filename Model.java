import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

//Subject
public class Model {
    private GraphGrid graph;
    private GridGUI view;

    public Model (int gridN){
        graph = new GraphGrid(gridN);
    }

    public GraphGrid getGraph(){
        return graph;
    }

    public void addObserver(GridGUI view){
        this.view = view;
    }

    public void notifyObserver(){
        if(view != null){
            view.update();
        }
    }
    
    public void notifyObserver(int visitedCell){
        if(view != null){
            view.update(visitedCell / graph.getGridN(), visitedCell % graph.getGridN());
        }
    }

    public void notifyObserver(Stack<Integer> validPath, int gridN) throws InterruptedException{
        if(view != null){
            view.update(validPath, gridN);
        }
    }

    //look into InterruptedException being necessary due to thread sleep
    public boolean dfs() throws InterruptedException{
        Stack<Integer> validPath = new Stack<>();
        HashMap<Integer, Integer> parentMap = new HashMap<>(); 

        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[graph.getNodes()];

        int currentCell = graph.getStartCell();
        visited[currentCell] = true;

        for(int i=0; i<graph.getNodes(); i++){
            if(visited[i] == false && graph.getAdjacencyMatrix()[currentCell][i] == 1){
                stack.push(i);
                parentMap.put(i, currentCell);
            }
        }

        while(!stack.isEmpty()){
            if(stack.peek() == graph.getDestinationCell()){
                System.out.println("found");
                Integer curr = stack.pop();

                while(curr != null){
                    validPath.push(curr);
                    curr = parentMap.get(curr);
                }
                notifyObserver();
                notifyObserver(validPath, graph.getGridN());
                return true;
            }

            currentCell = stack.pop();
            visited[currentCell] = true;
            notifyObserver(currentCell);

            for(int i=0; i<graph.getNodes(); i++){
                if(visited[i] == false && graph.getAdjacencyMatrix()[currentCell][i] == 1){
                    stack.push(i);
                    parentMap.put(i, currentCell);
                }
            }

            Thread.sleep(50);
        }

        System.out.println("Not found");
        return false;
    }

    public boolean bfs() throws InterruptedException {
        Stack<Integer> validPath = new Stack<>();
        HashMap<Integer, Integer> parentMap = new HashMap<>(); 

        Queue<Integer> queue = new LinkedList<Integer>();
        boolean[] visited = new boolean[graph.getNodes()];

        //add start node to be explored queue
        int currentCell = graph.getStartCell();
        visited[currentCell] = true;

        //visit all of the start nodes adjacent nodes
        for(int i=0; i<graph.getNodes(); i++){
            if(visited[i] == false && graph.getAdjacencyMatrix()[currentCell][i] == 1){
                queue.add(i);
                parentMap.put(i, currentCell);
                visited[i] = true;
            }
        }

        while(!queue.isEmpty()){
            currentCell = queue.remove();

            //check for destination cell
            if(currentCell == graph.getDestinationCell()){
                Integer curr = currentCell;
                System.out.println("Reached destination");
                while(curr != null){
                    validPath.push(curr);
                    curr = parentMap.get(curr);
                }
                notifyObserver();
                notifyObserver(validPath, graph.getGridN());
                return true;
            }

            notifyObserver(currentCell);
            Thread.sleep(50);

            visited[currentCell] = true;

            //explore the current cells neighbours
            for(int i=0; i<graph.getNodes(); i++){
                if(visited[i] == false && graph.getAdjacencyMatrix()[currentCell][i] == 1){
                    visited[i] = true;
                    queue.add(i);
                    parentMap.put(i, currentCell);
                }
            }

        }

        System.out.println("Not found");
        return false;
    }

}
