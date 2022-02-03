package Controller;
import View.Field;
import View.View;
import Model.Model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Klasa reprezentujaca kontroler planszy gry.
 *
 */

public class BoardController implements MouseListener {
    private View view;

    /**
     * Konstruktor, tworzy nowy kontroler planszy.
     * @param v
     *          Widok używany w danej rozgrywce.
     */
    public BoardController(View v) {
        view = v;
    }

    /**
     * Obsluga zdarzen generowanych przez nacisniecie myszki.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Field f = (Field) e.getSource();

        if (e.getButton() == MouseEvent.BUTTON1)
            Model.getInstance().updateGame(f.posX(), f.posY());

        else if (e.getButton() == MouseEvent.BUTTON3)
            Model.getInstance().updateFlag(f.posX(), f.posY());
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
