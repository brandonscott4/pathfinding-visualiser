import java.util.Stack;

public class Graph {
    private int gridN;
    private int nodes;
    private int[][] adjMatrix;
    private int startCell;
    private int destinationCell;  

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

    public boolean dfs(){
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[nodes];

        int currentCell = startCell;
        visited[currentCell] = true;

        for(int i=0; i<nodes; i++){
            if(visited[i] == false && adjMatrix[currentCell][i] == 1){
                stack.push(i);
            }
        }

        while(!stack.isEmpty()){
            if(stack.peek() == destinationCell){
                System.out.println("found");
                return true;
            }

            currentCell = stack.pop();
            visited[currentCell] = true;

            for(int i=0; i<nodes; i++){
                if(visited[i] == false && adjMatrix[currentCell][i] == 1){
                    stack.push(i);
                }
            }
        }

        System.out.println("not found");
        return false;
    }
}
