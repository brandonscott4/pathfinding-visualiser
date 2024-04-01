public class PathFindingVis {
    public static void main(String[] args) {
        //use odd number for n to ensure center row
        Graph graph = new Graph(3);
        GridGUI gui = new GridGUI(graph.getGridN());
        Controller controller = new Controller(gui, graph);
    }
}
