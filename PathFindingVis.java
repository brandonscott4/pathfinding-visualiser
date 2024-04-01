public class PathFindingVis {
    public static void main(String[] args) {
        Graph graph = new Graph(3);
        GridGUI gui = new GridGUI(graph.getGridN());
        Controller controller = new Controller(gui, graph);
    }
}
