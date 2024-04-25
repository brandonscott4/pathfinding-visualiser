public class PathFindingVis {
    public static void main(String[] args) {
        //use odd number for n to ensure center row
        Model model = new Model(9);
        GridGUI gui = new GridGUI(model.getGraph().getGridN());
        Controller controller = new Controller(gui, model);
    }
}
