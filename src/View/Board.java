package View;
import Model.Model;
import Controller.BoardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Klasa wchodzaca w sklad widoku gry. Reprezentuje plansze gry.
 * Sklada sie z obiektow klasy Field.
 *
 */

public class Board implements PropertyChangeListener {
    private Field[][] field;
    private JPanel panel;           // Panel na fieldy.

    /**
     * Konstruktor, tworzy nowy widok planszy.
     * @param a
     *          rozmiar a
     * @param b
     *          rozmiar b
     */
    public Board(int a, int b) {
        panel = new JPanel( new BorderLayout() );
        field = new Field[a][b];

        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                Field f = new Field(i, j);
                f.setMnemonic(KeyEvent.VK_P);
                panel.add(f);
                field[i][j] = f;
            }
        }
        panel.setLayout(new GridLayout(Model.getInstance().getX(), Model.getInstance().getY(), 0, 0));
        panel.setPreferredSize(new Dimension(500, 500));
    }

    /**
     * Funkcja ustawiajaca kontroler.
     * @param boardController
     */
    public void setController(BoardController boardController) {
        for (int i = 0; i < Model.getInstance().getX(); i++)
            for (int j = 0; j < Model.getInstance().getX(); j++)
                field[i][j].addMouseListener(boardController);
    }

    @Override
    public void propertyChange( PropertyChangeEvent evt) {
        switch( evt.getPropertyName())
        {
            case "NEW_GAME":
                setNewGame( Model.getInstance() );
                break;

            case "CHECK":
                setGameCheck( Model.getInstance() );
                break;
        }
    }

    /**
     * Ustawia pola na nieodkryte.
     *
     */
    public void setNewGame(Model model) {
        for(int i = 0; i < model.getX(); i++) {
            for(int j = 0; j < model.getY(); j++) {
                field[i][j].setNumber(9);
            }
        }
    }

    /**
     * Aktualizuje wyglad planszy.
     *
     */
    private void setGameCheck(Model model) {
        int result = model.checkSolution();
        if(result == 1)
            gameOver(model, true);
        else if(result == -1)
            gameOver(model, false);
        else {
            int[][] game = model.getGame();

            for (int i = 0; i < model.getX(); i++)
                for (int j = 0; j < model.getY(); j++)
                    if (game[i][j] == -2)
                        field[i][j].setFlag();
                    else
                        field[i][j].setNumber(game[i][j]);
        }
    }

    /**
     * Odkrywa cala plansze na dwa sposoby - w zaleznosci od wyniku rozgrywki.
     *
     */
    private void gameOver(Model model, boolean win) {
        int [][] solution = model.getSolution();
        if(win)
            for (int i = 0; i < model.getX(); i++)
                for (int j = 0; j < model.getY(); j++)
                    if (solution[i][j] == -1)
                        field[i][j].setFlag();
                    else
                        field[i][j].setNumber(solution[i][j]);
        else
            for (int i = 0; i < model.getX(); i++)
                for (int j = 0; j < model.getY(); j++)
                    if (solution[i][j] == -1)
                        field[i][j].setMine();
                    else if (solution[i][j] == -4)
                        field[i][j].setRedMine();
                    else
                        field[i][j].setNumber(solution[i][j]);

        panel.setEnabled(false);
    }

    /**
     *
     * @return panel z polami
     */
    public JPanel getBoardPanel() {
        return panel;
    }
}