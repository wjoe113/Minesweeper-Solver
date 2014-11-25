package minesweeper;

import junit.framework.TestCase;

/**
 * // -------------------------------------------------------------------------
 * /** This class is designed to test the MineSweeperBoard.
 *
 * @author Joe
 * @version 2013.14.10
 */

public class MineSweeperBoardTest
    extends TestCase

{
    private MineSweeperBoard mineSweeper;


    /**
     * Tests the MineSweeperBoard constructor
     */

    public MineSweeperBoardTest()
    {
        // blank
    }


    /**
     * creates a setup for the tests
     */
    public void setUp()
    {
        mineSweeper = new MineSweeperBoard(4, 4, 2);
    }


    /**
     * Tests the minesweeper board constructor
     */
    public void test()
    {
        assertEquals(4, mineSweeper.width());
        assertEquals(4, mineSweeper.height());
    }


    /**
     * Tests the board.
     *
     * @param theBoard
     *            = the board
     * @param expected
     *            = expected value
     */
    public void assertBoard(MineSweeperBoard theBoard, String... expected)
    {
        MineSweeperBoard expectedBoard =
            new MineSweeperBoard(expected[0].length(), expected.length, 0);
        expectedBoard.loadBoardState(expected);
        assertEquals(expectedBoard, theBoard);
    }


    /**
     * Tests setCell
     */
    public void testSetCell()
    {
        mineSweeper.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        mineSweeper.setCell(1, 2, MineSweeperCell.FLAGGED_MINE);
        mineSweeper.setCell(-1, 2, MineSweeperCell.FLAGGED_MINE);
        assertBoard(mineSweeper, "    ", "OOOO", "OM+O", "OOOO");

    }


    /**
     * Tests flag cell
     */
    public void testFlagCell()
    {
        mineSweeper.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        mineSweeper.flagCell(1, 2);
        assertBoard(mineSweeper, "    ", "OOOO", "OM+O", "OOOO");
        mineSweeper.flagCell(1, 1);
        assertBoard(mineSweeper, "    ", "OFOO", "OM+O", "OOOO");
        mineSweeper.loadBoardState("    ", "OFOO", "OM+O", "OOOO");
        mineSweeper.flagCell(1, 1);
        assertBoard(mineSweeper, "    ", "OOOO", "OM+O", "OOOO");
        mineSweeper.flagCell(1, 2);
        assertBoard(mineSweeper, "    ", "OOOO", "O++O", "OOOO");
        mineSweeper.flagCell(0, 0);
        assertBoard(mineSweeper, "    ", "OOOO", "O++O", "OOOO");

    }


    /**
     * Tests the cell
     */
    public void testGetCell()
    {
        mineSweeper.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        assertEquals(MineSweeperCell.MINE, mineSweeper.getCell(1, 2));

    }


    /**
     * Tests is game won
     */
    public void testIsGameLost()
    {
        mineSweeper.loadBoardState("    ", "OOOO", "O*+O", "OOOO");
        assertEquals(true, mineSweeper.isGameLost());
        mineSweeper.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        assertEquals(false, mineSweeper.isGameLost());

    }


    /**
     * Tests is game won
     */
    public void testIsGameWon()
    {
        mineSweeper.loadBoardState("    ", "    ", " MM ", "    ");
        assertEquals(true, mineSweeper.isGameWon());
        mineSweeper.loadBoardState("O   ", "    ", " MM ", "    ");
        assertEquals(false, mineSweeper.isGameWon());
    }


    /**
     * Tests number of adjacent mines
     */
    public void testNumberOfAdjacentMines()
    {
        mineSweeper.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        assertEquals(2, mineSweeper.numberOfAdjacentMines(1, 1));
    }


    /**
     * Tests reveal board
     */
    public void testRevealBoard()
    {
        mineSweeper.loadBoardState("FOOO", "OOOO", "O++O", "OOOO");
        mineSweeper.revealBoard();
        assertBoard(mineSweeper, "    ", "1221", "1**1", "1221");
    }


    /**
     * Tests the uncoverCell() method.
     */
    public void testUncoverCell()
    {
        mineSweeper.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        mineSweeper.uncoverCell(0, 0);
        mineSweeper.uncoverCell(1, 1);
        mineSweeper.uncoverCell(1, 2);

        assertBoard(mineSweeper, "    ", "O2OO", "O*+O", "OOOO");

    }

}
