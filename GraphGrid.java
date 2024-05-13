public class GraphGrid {
    private int gridN;
    private int nodes;
    private int[][] adjMatrix;
    private boolean[][] blocks;
    private int startCell;
    private int destinationCell; 

    public GraphGrid (int gridN){
        this.gridN = gridN;
        nodes = gridN * gridN;
        adjMatrix  = new int[nodes][nodes];
        blocks = new boolean[gridN][gridN];

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

    public void modifyEdges(int i, int j, boolean removeEdges){
        //Formula to map from graph to adjacency representation -> (gridN*i)+j 

        //uses removedEdges parameter to decide whether to remove edges or add them
        int matrixValue = 1;
        if(removeEdges){
            matrixValue = 0;
            addBlock(i, j);
        } else {
            removeBlock(i, j);
        }

        //check if there is a cell above
        if(i > 0){
            if(!(!removeEdges && isBlock(i-1, j))){
                adjMatrix[(gridN*i)+ j][((gridN*i)+ j) - gridN] = matrixValue;
                adjMatrix[((gridN*i)+ j) - gridN][(gridN*i)+ j] = matrixValue;
            }
        }

        //check if there is a cell to the right
        if(j < gridN-1){
            if(!(!removeEdges && isBlock(i, j+1))){
                adjMatrix[(gridN*i)+ j][((gridN*i)+ j) + 1] = matrixValue;
                adjMatrix[((gridN*i)+ j) + 1][(gridN*i)+ j] = matrixValue;
            }
        }

        //check if there is a cell below
        if(i < gridN-1){
            if(!(!removeEdges && isBlock(i+1, j))){
                adjMatrix[(gridN*i)+ j][((gridN*i)+ j) + gridN] = matrixValue;
                adjMatrix[((gridN*i)+ j) + gridN][(gridN*i)+ j] = matrixValue;
            }
        }

        //check if there is a cell to the left
        if(j > 0){
            if(!(!removeEdges && isBlock(i, j-1))){
                adjMatrix[(gridN*i)+ j][((gridN*i)+ j) - 1] = matrixValue;
                adjMatrix[((gridN*i)+ j) - 1][(gridN*i)+ j] = matrixValue;
            }
        }

        //printAdjMatrix();
    }

    private void addBlock(int i, int j){
        blocks[i][j] = true;
    }

    private void removeBlock(int i, int j){
        blocks[i][j] = false;
    }

    private boolean isBlock(int i, int j){
        return blocks[i][j];
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

    public boolean isConnected(int cell1, int cell2){
        return adjMatrix[cell1][cell2] == 1;
    }

    public int getStartCell(){
        return startCell;
    }

    public int getDestinationCell(){
        return destinationCell;
    }
}
