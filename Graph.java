import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

//Subject
public class Graph {
    private int gridN;
    private int nodes;
    private int[][] adjMatrix;
    private int startCell;
    private int destinationCell;  
    
    private GridGUI view;

    public Graph (int gridN){
        this.gridN = gridN;
        nodes = gridN * gridN;
        adjMatrix  = new int[nodes][nodes];

        startCell = gridN * ((int) Math.ceil(gridN/2));
        destinationCell = (gridN * ((int) Math.ceil(gridN/2))) + (gridN - 1);

        initialiseMatrix();
    }

    private void initialiseMatrix(){
        int adjIndex = 0;
        for(int i=0; i<gridN; i++){
            for(int j=0; j<gridN; j++){
                    //check if there is a cell above
                    if(i > 0){
                        adjMatrix[adjIndex][adjIndex-gridN] = 1;
                    }

                    //check if there is a cell to the right
                    if(j < gridN-1){
                        adjMatrix[adjIndex][adjIndex+1] = 1;
                    }

                    //check if there is a cell below
                    if(i < gridN-1){
                        adjMatrix[adjIndex][adjIndex+gridN] = 1;
                    }

                    //check if there is a cell to the left
                    if(j > 0){
                        adjMatrix[adjIndex][adjIndex-1] = 1;
                    }

                    adjIndex++;
            }
        }

        //printAdjMatrix();
    }

    private void printAdjMatrix(){
        System.out.println();
        for(int i=0; i<nodes; i++){
            System.out.println("");
            for(int j=0; j<nodes; j++){
                System.out.print("-" + adjMatrix[i][j] + "-");
            }
        }
        
        System.out.println();
    }

    public void modifyEdges(int i, int j, boolean removeEdges){
        //Formula to map from graph to adjacency representation -> (gridN*i)+j 

        //uses removedEdges parameter to decide whether to remove edges or add them
        int matrixValue = 1;
        if(removeEdges){
            matrixValue = 0;
        }

        //check if there is a cell above
        if(i > 0){
            adjMatrix[(gridN*i)+ j][((gridN*i)+ j) - gridN] = matrixValue;
            adjMatrix[((gridN*i)+ j) - gridN][(gridN*i)+ j] = matrixValue;
        }

        //check if there is a cell to the right
        if(j < gridN-1){
            adjMatrix[(gridN*i)+ j][((gridN*i)+ j) + 1] = matrixValue;
            adjMatrix[((gridN*i)+ j) + 1][(gridN*i)+ j] = matrixValue;
        }

        //check if there is a cell below
        if(i < gridN-1){
            adjMatrix[(gridN*i)+ j][((gridN*i)+ j) + gridN] = matrixValue;
            adjMatrix[((gridN*i)+ j) + gridN][(gridN*i)+ j] = matrixValue;
        }

        //check if there is a cell to the left
        if(j > 0){
            adjMatrix[(gridN*i)+ j][((gridN*i)+ j) - 1] = matrixValue;
            adjMatrix[((gridN*i)+ j) - 1][(gridN*i)+ j] = matrixValue;
        }

        //printAdjMatrix();
    }

    public int getGridN(){
        return gridN;
    }

    public int getNodes(){
        return nodes;
    }

    public int[][] getAdjacencyMatrix(){
        return adjMatrix;
    }

    public int getStartCell(){
        return startCell;
    }

    public int getDestinationCell(){
        return destinationCell;
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
            view.update(visitedCell / gridN, visitedCell % gridN);
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
        boolean[] visited = new boolean[nodes];

        int currentCell = startCell;
        visited[currentCell] = true;

        for(int i=0; i<nodes; i++){
            if(visited[i] == false && adjMatrix[currentCell][i] == 1){
                stack.push(i);
                parentMap.put(i, currentCell);
            }
        }

        while(!stack.isEmpty()){
            if(stack.peek() == destinationCell){
                System.out.println("found");
                Integer curr = stack.pop();

                while(curr != null){
                    validPath.push(curr);
                    curr = parentMap.get(curr);
                }
                notifyObserver();
                notifyObserver(validPath, gridN);
                return true;
            }

            currentCell = stack.pop();
            visited[currentCell] = true;
            notifyObserver(currentCell);

            for(int i=0; i<nodes; i++){
                if(visited[i] == false && adjMatrix[currentCell][i] == 1){
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
        boolean[] visited = new boolean[nodes];

        //add start node to be explored queue
        int currentCell = startCell;
        visited[currentCell] = true;

        //visit all of the start nodes adjacent nodes
        for(int i=0; i<nodes; i++){
            if(visited[i] == false && adjMatrix[currentCell][i] == 1){
                queue.add(i);
                parentMap.put(i, currentCell);
                visited[i] = true;
            }
        }

        while(!queue.isEmpty()){
            currentCell = queue.remove();

            //check for destination cell
            if(currentCell == destinationCell){
                Integer curr = currentCell;
                System.out.println("Reached destination");
                while(curr != null){
                    validPath.push(curr);
                    curr = parentMap.get(curr);
                }
                notifyObserver();
                notifyObserver(validPath, gridN);
                return true;
            }

            notifyObserver(currentCell);
            Thread.sleep(50);

            visited[currentCell] = true;

            //explore the current cells neighbours
            for(int i=0; i<nodes; i++){
                if(visited[i] == false && adjMatrix[currentCell][i] == 1){
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
