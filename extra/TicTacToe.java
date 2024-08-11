// simulates a game of TicTacToe but offers no methods for asking players to choose a cell
public class TicTacToe {
    public static final int X = 1, O = -1;  // identify the gamers
    public static final int EMPTY = 0;      // identify a free position
    private int board[][] = new int[3][3];  // any size is valid, but you'll need to make some changes
    private int player;                     // which player has to play

    public TicTacToe(){
        clearBoard();
    }

    public void clearBoard(){
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                board[i][j] = EMPTY;
        player = X; // arbitrary choice
    }

    public void putMark(int i, int j) throws IllegalArgumentException {
        if(i < 0 || i > 2 || j < 0 || j > 2)
            throw new IllegalArgumentException("Not a valild position");
        if(board[i][j] != EMPTY)
            throw new IllegalArgumentException("Board position already occupied");
        board[i][j] = player;
        player = - player;      // the other player, remember that X = - O
    }

    public boolean isWinner(int mark) {
        return ((board[0][0] + board[0][1] + board[0][2]) == mark * 3)
            || ((board[1][0] + board[1][1] + board[1][2]) == mark * 3)
            || ((board[2][0] + board[2][1] + board[2][2]) == mark * 3)
            || ((board[0][0] + board[1][0] + board[2][0]) == mark * 3)
            || ((board[0][1] + board[1][1] + board[2][1]) == mark * 3)
            || ((board[0][2] + board[1][2] + board[2][2]) == mark * 3)
            || ((board[0][0] + board[1][1] + board[2][2]) == mark * 3)
            || ((board[2][0] + board[1][1] + board[0][2]) == mark * 3);
    }

    public int winner() {
        if(isWinner(X))
            return X;
        else if(isWinner(O))
            return O;
        else
            return EMPTY;
    }

    // simplest gui possible
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                switch(board[i][j]){
                case X: sb.append("X"); break;
                case O: sb.append("O"); break;
                case EMPTY: sb.append(" "); break;
                }
                if(j < 2) sb.append("|");       // end of row
            }
            if(i < 2) sb.append("\n-----\n");   // end of column
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        /* X moves */               /* O moves */
        game.putMark(1, 1);     game.putMark(0, 2);
        game.putMark(2, 2);     game.putMark(0, 0);
        game.putMark(0, 1);     game.putMark(2, 1);
        game.putMark(1, 2);     game.putMark(1, 0);
        game.putMark(2, 0);

        System.out.println(game);   // can also be done after every single move, maybe after waiting a second
        int winner = game.winner();
        String[] outcome = {"0 wins", "Tie", "X wins"};
        System.out.println(outcome[1 + winner]);
    }
}
