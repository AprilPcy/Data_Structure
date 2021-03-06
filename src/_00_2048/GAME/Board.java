package _00_2048.GAME;

import java.util.Random;

public class Board {

    public Tile[][] board;

    int grids = 4;

    int border = 0;

    public int score = 0;


    /**
     * Default constructor for the Board - sets up a 4x4 matrix
     */
    public Board()
    {
        board = new Tile[4][4];
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                board[i][j] = new Tile();
            }
        }
    }


    /**
     * Constructor for the board - sets up a matrix with a specified grid size -
     * Not used in this version, but hopefully implement in the future
     *
     * @param grids
     */
    public Board( int grids )
    {
        this.grids = grids;
        board = new Tile[grids][grids];
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                board[i][j] = new Tile();
            }
        }
    }


    /**
     * Board constructor with 2 parameters - sets up a board where the player
     * has already lost (for testing purposes)
     *
     * @param lose
     * @param grids
     */
    public Board( int lose, int grids )
    {
        this();
    }


    /**
     *
     * Getter method that returns the board
     *
     * @return board
     */
    public Tile[][] getBoard()
    {
        return board;
    }


    /**
     *
     * Getter method that returns the score
     *
     * @return score
     */
    public int getScore()
    {
        return score;
    }


    /**
     *
     * Finds the the highest tile on the board and returns it
     *
     * @return high
     */
    public int getHighTile()
    {
        int high = board[0][0].getValue();
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > high )
                {
                    high = board[i][j].getValue();
                }
            }
        }
        return high;
    }


    /**
     *
     * Prints out the board on the console - for testing purposes
     */
    public void print()
    {
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                String s = board[i][j].toString() + " ";
                System.out.print( s );
            }
            System.out.println( "" );
        }
        System.out.println( "Score: " + score );
    }


    /**
     * Returns the board as a String - used in the GUI
     */
    public String toString()
    {
        String s = "";
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                s += board[i][j].toString() + " ";
            }
            s += "\n";
        }
        return s;
    }


    /**
     *
     * Spawns a 2 at an empty space every time a move is made
     */
    public void spawn()
    {
        boolean flag=true;
        while(flag){
            Random random=new Random();
            int row=random.nextInt(4); // 0-3
            int col=random.nextInt(4);
            int r3=random.nextInt(4);
            if(board[row][col].getValue()==0){
                flag=false;
                if(r3==2) {
                    board[row][col] = new Tile(4);
                } else {
                    board[row][col] = new Tile(2);
                }
            }
            }

    }




    /**
     *
     * Checks to see if the board is completely blacked out and if it is, it
     * will give a suggestion to the player - nudging them to restart
     *
     * @return boolean
     */
    public boolean blackOut() {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getValue() > 0) {
                    count++;
                }

            }
        }
        return count == 16 ?true:false;
    }



    /**
     *
     * Checks to see if the game is over - that is, checks if any tile (that
     * isn't a 0) is able to combine with the tiles next to it - If not, the
     * game is over
     *
     * @return boolean
     */
    public boolean gameOver() {


        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getValue() > 0) {
                    int value = board[i][j].getValue();
                    if (j == 3 && i != 3) {
                        if (board[i + 1][j].getValue() != value) count++;
                    } else if (i == 3 && j != 3) {
                        if (board[i][j + 1].getValue() != value) count++;

                    } else if (i == 3 && j == 3) {
                        count++;
                    } else {
                        if (board[i + 1][j].getValue() != value && board[i][j + 1].getValue() != value) count++;
                    }
                }
            }
        }
        return count == 16 ? true : false;
    }







    /**
     *
     * This method is called when a 'w' or up arrow is pressed - goes through
     * the entire board and calls verticalMove with an "up" parameter for each
     * tile
     */
    public void up()
    {
        for ( int i = 0; i < grids; i++ )
        {
            border = 0;
            for ( int j = 0; j < grids; j++ )
            {
                if ( board[j][i].getValue() != 0 )
                 {
                    verticalMove( j, i, "up" );

                }
            }
        }
    }


    /**
     *
     * This method is called when a 's' or down arrow is pressed - goes through
     * the entire board and calls verticalMove with a "down" parameter for each
     * tile
     */
    public void down()
    {
        for ( int i = 0; i < grids; i++ )
        {
            border = ( grids - 1 );
            for ( int j = grids - 1; j >= 0; j-- )
            {
                if ( board[j][i].getValue() != 0 )
                {
                    if ( border >= j )
                    {
                        verticalMove( j, i, "down" );
                    }
                }
            }
        }
    }


    /**
     *
     * Compares two tile's values together and if they are the same or if one is
     * equal to 0 (plain tile) - their values are added (provided that the tiles
     * we are comparing are two different tiles and they are moving towards the
     * appropriate direction) - Uses recursion to go through the entire column
     *
     * @param row
     *            row that the compare tile is currently on
     * @param col
     *            column that the compare tile is currently on
     * @param direction
     *            direction (up or down) that the tile is moving in
     */
    private void verticalMove( int row, int col, String direction )
    {
        Tile initial = board[border][col];
        Tile compare = board[row][col];
        if ( initial.getValue() == 0 || initial.getValue() == compare.getValue() )
        {
            if ( row > border || ( direction.equals( "down" ) && ( row < border ) ) )
            {
                int addScore = initial.getValue() + compare.getValue();
                if ( initial.getValue() != 0 )
                {
                    score += addScore;
                }
                initial.setValue( addScore );
                compare.setValue( 0 );
            }
        }
        else
        {
            if ( direction.equals( "down" ) )
            {
                border--;
            }
            else
            {
                border++;
            }
            verticalMove( row, col, direction );
        }
    }

    public void left() {
        for (int i = 0; i < board.length; i++) {
            border = 0;
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getValue() == 0) continue;
                movingHorizon(i, j, "left");
            }
        }

    }

    public void right() {
        for (int i = 0; i < board.length; i++) {
            border = 3;
            for (int j = board[i].length - 1; j >= 0; j--) {
                if (board[i][j].getValue() == 0) continue;
                movingHorizon(i, j, "right");
            }
        }


    }

    private void movingHorizon(int row, int col, String direction) {
        Tile initial = board[row][border];
        Tile compare = board[row][col];
        if (initial.getValue() == 0 || initial.getValue() == compare.getValue()) {
            // two tiles can be merged
            if (col > border || (direction.equals("right") && col < border)) {
                int total = initial.getValue() + compare.getValue();
                score += total;
                initial.setValue(total);
                compare.setValue(0);
            }
        } else {
            // two tiles cannot be merged
            if (direction.equals("right")) border--;
            else border++;
            movingHorizon(row, col, direction);
        }
    }

}