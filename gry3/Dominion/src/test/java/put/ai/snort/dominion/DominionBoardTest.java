/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.dominion;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.powermock.reflect.Whitebox;
import put.ai.snort.game.Player;

/**
 *
 * @author smaug
 */
public class DominionBoardTest {

    private DominionBoard b;

    @Before
    public void before() {
        b = new DominionBoard();
    }

    @Test
    public void fullBoard1() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1},
            {Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1},
            {Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER2, Player.Color.PLAYER2},
            {Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2},
            {Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2},};
        Whitebox.setInternalState(b, state);
        assertEquals(Player.Color.PLAYER1, b.getWinner());
    }

    @Test
    public void fullBoard2() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1},
            {Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1},
            {Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2},
            {Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2},
            {Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2},};
        Whitebox.setInternalState(b, state);
        assertEquals(Player.Color.PLAYER2, b.getWinner());
    }

    @Test
    public void fullBoard3() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1},
            {Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1},
            {Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2},
            {Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2},};
        Whitebox.setInternalState(b, state);
        assertEquals(Player.Color.EMPTY, b.getWinner());
    }

    @Test
    public void notFullBoard() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1},
            {Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1},
            {Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2},
            {Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        assertEquals(null, b.getWinner());
    }
}
