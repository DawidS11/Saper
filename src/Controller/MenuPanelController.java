package Controller;
import View.View;
import Model.Model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Klasa reprezentujaca kontroler menu gry.
 *
 */

public class MenuPanelController implements MouseListener {
    private View view;

    /**
     * Konstruktor, tworzy nowy kontroler menu.
     * @param v
     *          Widok używany w danej rozgrywce.
     */
    public MenuPanelController(View v) {
        view = v;
    }

    /**
     * Obsluga zdarzen generowanych przez nacisniecie myszki.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            Model.getInstance().newGame();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
