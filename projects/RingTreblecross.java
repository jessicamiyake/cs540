import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class RingTreblecross {
    public final int k;

    public RingTreblecross(int k) {
        this.k = k;
    }

    /**
       A container class holding the score of a state and the best successor of
       that state which achieves the score. To complete the assignment, you must
       return an instance of this class in your implementation of
       `minValueAndBestSuccessor` and `maxValueAndBestSuccessor`.

       DO NOT MODIFY.
    **/
    public static class Best {
        public final int score;
        public final GameState successor;

        public Best(int score, GameState successor) {
            this.score = score;
            this.successor = successor;
        }
    }

    // this function determines if the given state is the end of the game
    public boolean isTerminalState(GameState state) {
    	/* TO DO
         * You are given a state, and you need to return true if the state is the end of the game
         */
        return false;
    }


    public Best minValueAndBestSuccessor(GameState state) {
    	/* TO DO
         * You are given a state, and you need to use minimax algorithm to compute the score in
         * this state and find the best successor. For tie breaking, use the first successor
         * with the best from getSuccessors as the best successor
         * You need to pack the score and best successor to a Best object and return it
         */

         return null;
    }

    public Best maxValueAndBestSuccessor(GameState state) {
      /* TO DO
         * You are given a state, and you need to use minimax algorithm to compute the score in
         * this state and find the best successor. For tie breaking, use the first successor
         * with the best from getSuccessors as the best successor
         * You need to pack the score and best successor to a Best object and return it
         */

    	 return null;
    }

    // return a list of all successors of this state
    public List<GameState> getSuccessors(GameState state) {
        /* TO DO
         * You need to return a list of successors of this state in the following order:
         * The successor with the left most unoccupied square marked is the first item,
         * and then the one with next unoccupied square marked is the second item, and so on.
         */

    	 return null;
    }

    /***************************************************************************
     ** THERE IS NO NEED TO MODIFY ANY CODE BELOW THIS POINT
     **************************************************************************/

    /**
       Parses command-line arguments and executes appropriate command-line flag.
    **/
    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            System.out.println("Usage: java Treblecross N K OP [List of positions set]");
            System.exit(0);
        }

        int flag = -1;
        int k = -1;
        boolean[] board = null;
        try {
            int boardSize = Integer.parseInt(args[0]);
            k = Integer.parseInt(args[1]);
            flag = Integer.parseInt(args[2]);
            board = new boolean[boardSize];
            Arrays.fill(board, false);
            for (int i = 3; i < args.length; i++) {
                int j = Integer.parseInt(args[i]);
                if(j < 0 || j >= boardSize) {
                    System.err.println("Board position " + j + " out of range.");
                    System.exit(-1);
                }
                board[j] = true;
            }
            if (k <= 0) {
            	throw new Exception();
            }
        } catch (Exception e) {
            System.err.println("Invalid Arguments");
            System.exit(-1);
        }

        RingTreblecross treblecross = new RingTreblecross(k);

        if (flag == 0) {
            // Play a game against a human
            treblecross.playGame(new GameState(board), false);
        } else if (flag == 1) {
            // Print all successors with their score
            treblecross.printSuccessors(new GameState(board), true);
        } else if (flag == 2) {
            // Print optimal trace
            treblecross.printOptimalTrace(new GameState(board), true);
        } else if (flag == 3) {
            // Print best successor
            treblecross.printBestSuccessor(new GameState(board), true);
        }
    }

    public Best computeScoreAndBestSuccessor(GameState state, boolean maxToMove) {
        return maxToMove ? maxValueAndBestSuccessor(state) : minValueAndBestSuccessor(state);
    }

    /* given a state, this function print
     * whether the state is a max node, the board representation, and the score of the state
     */
    public void printStateInfo(GameState state, int score, boolean maxToMove) {
        System.out.println((maxToMove ? "Max Node " : "Min Node ") + state + String.format(" Score: %2d", score));
    }

    /* given initial state, this function print the best successor of initial state
     * if the initial state is max node, it prints the successor that maximize the score
     * else, it prints the successor that minimize the score
     */
    public void printBestSuccessor(GameState initial, boolean maxToMove) {
        Best best = computeScoreAndBestSuccessor(initial, maxToMove);
        printStateInfo(best.successor, best.score, !maxToMove);
    }

    // given initial state, this function print all successors of initial state
    public void printSuccessors(GameState initial, boolean maxToMove) {
        for (GameState successor : getSuccessors(initial)) {
            Best best = computeScoreAndBestSuccessor(successor, !maxToMove);
            printStateInfo(successor, best.score, !maxToMove);
        }
    }

    /* given initial state
     * assume the opponent plays the game optimally,
     * this function print the path from initial state to terminal state which
     * 1. maximize the score if the initial state is set to maximize score
     * 2. minimize the score if the initial state is set to minimize score
     */
    public void printOptimalTrace(GameState initial, boolean maxToMove) {
        GameState curr = initial;
        while (curr != null) {
            Best best = computeScoreAndBestSuccessor(curr, maxToMove);
            printStateInfo(curr, best.score, maxToMove);
            curr = best.successor;
            maxToMove = !maxToMove;
        }
    }

    /* call this function to let the program play a game against you
     * if the initial state is set as max node, the program will play first
     * else, you will play first.
     */
    public void playGame(GameState initial, boolean maxToMove) {
        Scanner scan = new Scanner(System.in);
        GameState curr = initial;
        System.out.println("Initial : " + curr);
        while(!isTerminalState(curr)) {
            if (maxToMove) {
                curr = computeScoreAndBestSuccessor(curr, maxToMove).successor;
                System.out.println("Computer: " + curr);
            } else {
                System.out.println("Index:    " + curr.getPositionString());
                System.out.print("Choose Next Move: ");
                try {
                    GameState state = makeMove(curr, Integer.parseInt(scan.nextLine()));
                    if(state != null)
                        curr = state;
                    else
                        continue;
                } catch (Exception e) {
                    System.err.println("Invalid Input");
                    continue;
                }
                System.out.println("Player  : " + curr);
            }
            maxToMove = !maxToMove;
        }
        scan.close();
    }

    /* This function is only used from playGame function.
     * Given current state and the index of the board to be marked
     * If the move is valid, return a new GameState with updated board
     * else return null
     */
    public GameState makeMove(GameState state, int move) {
        boolean[] board = state.getBoard();
        if(move < 0 || move >= board.length) {
            System.out.println("Board position out of range.");
            return null;
        }
        if(board[move]) {
            System.out.println("Illegal move. Position already occupied.");
            return null;
        }
        board[move] = true;
        return new GameState(board);
    }
}

class GameState {
    final static public char SYMBOL = 'X';
    private final boolean[] board;

    public GameState(boolean[] board) {
        this.board = board.clone();
    }

    // return deep copy of board
    public boolean[] getBoard() {
        return board.clone();
    }

    // return string representation of board. e.g. "| X |   | X | X |   |"
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(" | ");
        for (boolean i : board) {
            str.append(i ? SYMBOL : ' ');
            str.append(" | ");
        }
        return str.toString();
    }

    // return string of index for each position and aligned with toString output
    public String getPositionString() {
    	StringBuilder str = new StringBuilder(" |");
        for (int index = 0; index < board.length; index++) {
            str.append(String.format("%3d|", index));
        }
        return str.toString();
    }
}
