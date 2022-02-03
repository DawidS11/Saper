package Controller;
import Model.Model;
import View.View;

/**
 * Klasa reprezentujaca kontroler. Opakowuje klasy BoardController
 * oraz MenuPanelController.
 *
 */

public class Controller {
    private View view;
    private BoardController boardController;
    private MenuPanelController menuPanelController;

    /**
     * Konstruktor, tworzy nowy kontroler.
     * @param v
     *          Widok uzywany w danej rozgrywce.
     */
    public Controller (View v) {
        view = v;
        boardController = new BoardController(view);
        menuPanelController = new MenuPanelController(view);
        view.getBoard().setController(boardController);
        view.getMenuPanel().setController(menuPanelController);
        Model.getInstance().addObserver("NEW_GAME", view.getBoard());
        Model.getInstance().addObserver("CHECK", view.getBoard());
        Model.getInstance().addObserver("NEW_GAME", view.getMenuPanel());
        Model.getInstance().addObserver("CHECK", view.getMenuPanel());
    }
}
