public class Graph {
    private int[][] adjMatrix;
    private int gridN;
    private int nodes;

    public Graph (int gridN){
        this.gridN = gridN;
        nodes = gridN * gridN;
        adjMatrix = new int[nodes][nodes];
    }

    public void initialiseMatrix(){
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

        printAdjMatrix();
    }

    private void printAdjMatrix(){
        for(int i=0; i<nodes; i++){
            System.out.println("");
            for(int j=0; j<nodes; j++){
                System.out.print("-" + adjMatrix[i][j] + "-");
            }
        }
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

        printAdjMatrix();
    }


    //need to account for start and end destination 

    public static void main(String[] args) {
        Graph g = new Graph(3);
        g.initialiseMatrix();
        System.out.println();
        g.modifyEdges(1, 0, true);

    }
}
