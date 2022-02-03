import Model.Model;
import Controller.Controller;
import View.View;

public class Main {
    public static void main(String[] args) {
        Model.getInstance();
        new Controller(new View());
    }
}
