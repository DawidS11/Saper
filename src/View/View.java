package View;
import Model.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa reprezentujaca widok gry Saper. Opakowuje klasy MenuPanel oraz Board.
 *
 */

public class View {
    private JFrame frame;		// okno programu
    private Board board;	    // plansza gry
    private MenuPanel menuPanel;

    /**
     * Konstruktor, tworzy nowy widok.
     */
    public View() {
        frame = new JFrame( "Saper" );

        board = new Board(Model.getInstance().getX(), Model.getInstance().getY());
        menuPanel = new MenuPanel(Model.getInstance().getRemainingMines());

        board.setNewGame(Model.getInstance());
        menuPanel.setNewGame();

        frame.add(board.getBoardPanel(), BorderLayout.SOUTH);
        frame.add(menuPanel.getPanel(), BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     *
     * @return board
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     *
     * @return menu panel
     */
    public MenuPanel getMenuPanel() { return menuPanel; }
}