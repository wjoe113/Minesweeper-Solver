package minesweeper;

import sofia.util.Random;

// -------------------------------------------------------------------------
/**
 * This class represents the MineSweeper Board This class extends
 * MineSweeperBoardBase and uses variables including width, height, numMines,
 * and board.
 *
 * @author Joe
 * @version 2013.14.10
 */
public class MineSweeperBoard
    extends MineSweeperBoardBase
{

    private int                 width;
    private int                 height;
    private int                 numMines;
    private MineSweeperCell[][] board;
    private Random              random;  // (private Random random =


    // Random.generator();)

    /**
     * Constructor that creates the board.
     *
     * @param newWidth
     *            = the new width
     * @param newHeight
     *            = the new height
     * @param newNumMines
     *            = the new number of mines on the board
     */
    public MineSweeperBoard(int newWidth, int newHeight, int newNumMines)
    {
        width = newWidth;
        height = newHeight;
        numMines = newNumMines;
        board = new MineSweeperCell[width][height];
        for (int w = 0; w < width; w++)
        {
            for (int h = 0; h < height; h++)
            {
                setCell(w, h, MineSweeperCell.COVERED_CELL);
            }
        }

        int n = 0;
        this.random = new Random();
        // Random r = new sofia.util.Random();
        while (n < numMines)
        {
            int x = random.nextInt(0, width - 1);
            int y = random.nextInt(0, height - 1);
            setCell(x, y, MineSweeperCell.MINE);
            n++;

        }

    }


    /**
     * Method to for flagging a cell. If the cell is covered then place a flag.
     * If the cell already contains a flag then remove the flag without
     * uncovering the cell. If the cell is uncovered or invalid then don't
     * change anything.
     *
     * @param x
     *            = the column of the cell
     * @param y
     *            = the row of the cell
     */

    @Override
    public void flagCell(int x, int y)
    {
        /**
         * Method for getting a cell. Gets the contents of the specified cell.
         *
         * @param x
         *            = the column of the cell.
         * @param y
         *            = the row of the cell.
         * @return the value of the cell.
         */
        switch (getCell(x, y))
        {
            case COVERED_CELL:
                setCell(x, y, MineSweeperCell.FLAG);
                break;

            case FLAGGED_MINE:
                setCell(x, y, MineSweeperCell.MINE);
                break;

            case MINE:
                setCell(x, y, MineSweeperCell.FLAGGED_MINE);
                break;

            case FLAG:
                setCell(x, y, MineSweeperCell.COVERED_CELL);
                break;

            default:
                break;

        }

    }


    /**
     * Gets the cell from the board
     *
     * @param x
     *            =width
     * @param y
     *            =height
     * @return board =board with cell in (x,y)
     */
    @Override
    public MineSweeperCell getCell(int x, int y)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
        {
            return board[x][y];
        }
        else
        {
            return MineSweeperCell.INVALID_CELL;
        }
    }


    /**
     * Gets the number of rows in this MineSweeperBoard
     *
     * @return the number of rows in MineSweeperBoard
     */

    @Override
    public int height()
    {
        return height;
    }


    /**
     * Checks if the game is lost. The game is lost if a player uncovered a
     * mine.
     *
     * @return true if the current game has been lost and false otherwise.
     */

    @Override
    public boolean isGameLost()
    {
        for (int w = 0; w < width; w++)
        {
            for (int h = 0; h < height; h++)
            {
                if (getCell(w, h).equals(MineSweeperCell.UNCOVERED_MINE))
                {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Checks if the game is won. The game is won if all three conditions are
     * satisfied: 1. Flags have been placed on all the mines. 2. No flags have
     * been placed incorrectly. 3. All non-flagged cells have been uncovered.
     *
     * @return true if the current game has been lost and false otherwise.
     */

    @Override
    public boolean isGameWon()
    {
        for (int w = 0; w < width; w++)
        {
            for (int h = 0; h < height; h++)
            {
                if (getCell(w, h).equals(MineSweeperCell.UNCOVERED_MINE)
                    || getCell(w, h) == MineSweeperCell.FLAG
                    || getCell(w, h) == MineSweeperCell.MINE
                    || getCell(w, h).equals(MineSweeperCell.COVERED_CELL))
                {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Count the number of mines that are adjacent to the specified cell.
     *
     * @param x
     *            = the column of the cell.
     * @param y
     *            = the row of the cell.
     * @return the number of mines adjacent to the specified cell.
     */

    @Override
    public int numberOfAdjacentMines(int x, int y)
    {
        int a = 0;
        for (int w = x - 1; w <= x + 1; w++)
        {
            for (int h = y - 1; h <= y + 1; h++)
            {
                if ((w != x || h != y)
                    && getCell(w, h).equals(MineSweeperCell.MINE)
                    || getCell(w, h).equals(MineSweeperCell.UNCOVERED_MINE)
                    || getCell(w, h).equals(MineSweeperCell.FLAGGED_MINE))
                {
                    a++;
                }
            }
        }
        return a;
    }


    /**
     * Uncovers all of the cells on the board.
     */

    @Override
    public void revealBoard()
    {
        for (int w = 0; w < width; w++)
        {
            for (int h = 0; h < height; h++)
            {
                if (getCell(w, h).equals(MineSweeperCell.FLAG))
                {
                    setCell(
                        w,
                        h,
                        MineSweeperCell.adjacentTo
                        (numberOfAdjacentMines(w, h)));
                }
                else if (getCell(w, h).equals(MineSweeperCell.MINE))
                {
                    setCell(w, h, MineSweeperCell.UNCOVERED_MINE);
                }
                else
                {
                    this.uncoverCell(w, h);
                }
            }
        }

    }


    /**
     * Sets the contents of the specified cell on this MineSweeperBoard.
     *
     * @param x
     *            = the column of the cell
     * @param y
     *            = the row of the cell
     * @param value
     *            = the value to place in the cell
     */
    @Override
    protected void setCell(int x, int y, MineSweeperCell value)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
        {
            board[x][y] = value;
        }
    }


    /**
     * Uncovers the specified cell. If the cell contains a flag it should not be
     * uncovered. If there is no mine under the cell then the value of the cell
     * is changed to how many mines are adjacent to it. If there is a mine under
     * the cell then the game is over. If the cell is already uncovered or
     * invalid then don't do anything.
     *
     * @param x
     *            = the column of the cell.
     * @param y
     *            = the row of the cell.
     */

    @Override
    public void uncoverCell(int x, int y)
    {
        switch (getCell(x, y))
        {
            case MINE:
                setCell(x, y, MineSweeperCell.UNCOVERED_MINE);
                break;

            case COVERED_CELL:
                setCell(
                    x,
                    y,
                    MineSweeperCell.adjacentTo(numberOfAdjacentMines(x, y)));
                break;
            default:
                break;

        }
    }


    /**
     * Gets the number of columns in this MineSweeperBoard
     *
     * @return the number of columns in MineSweeperBoard
     */

    @Override
    public int width()
    {
        return width;
    }

}
