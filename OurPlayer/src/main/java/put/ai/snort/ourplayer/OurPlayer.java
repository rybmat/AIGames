package put.ai.snort.ourplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import put.ai.snort.game.Board;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;
import put.ai.snort.game.TypicalBoard;

public class OurPlayer extends Player {
    public static final int INF = Integer.MAX_VALUE;
    public static int MAX_DEPTH = 3;

    private Random random = new Random(System.currentTimeMillis());

    private static int WEIGHT_ARRAY[][];/* = {
      {0, 0, 0, 0, 0, 0, 0, 0},
      {0, 1, 1, 1, 1, 1, 1, 0},
      {0, 1, 2, 2, 2, 2, 1, 0},
      {0, 1, 2, 3, 3, 2, 1, 0},
      {0, 1, 2, 3, 3, 2, 1, 0},
      {0, 1, 2, 2, 2, 2, 1, 0},
      {0, 1, 1, 1, 1, 1, 1, 0},
      {0, 0, 0, 0, 0, 0, 0, 0}
    };*/


    @Override
    public void setTime(long time) {
    	if (time >= 14000) {
    		MAX_DEPTH = 4;
    	} if (time >= 75000) {
    		MAX_DEPTH = 5;
    	}
    	System.out.println(MAX_DEPTH);
    	super.setTime(time);
    }


    @Override
    public String getName() {
        return "Vinci";
    }

    @Override
    public Move nextMove(Board brd) {
      if (WEIGHT_ARRAY == null) {
        WEIGHT_ARRAY = new int[brd.getSize()][brd.getSize()];
        final int center = brd.getSize() / 2;
        for (int x = 0; x < brd.getSize(); x++)
          for (int y = 0; y < brd.getSize(); y++)
            WEIGHT_ARRAY[x][y] = Math.max(x, y) >= center ?
                Math.min(x, Math.min(y, Math.min(brd.getSize()-1-x, brd.getSize()-1-y))) :
                Math.min(x, y);
      }

      int alpha = -INF;
      int beta = INF;

      int bestScore = -INF;

      List<Move> bestMoves = new ArrayList<Move>();
      List<Move> moves = brd.getMovesFor(getColor());
      OurBoard board = new OurBoard((TypicalBoard) brd);
      List<Move> moves_done = new ArrayList<Move>();

    	for (Move move : moves) {
    	  moves_done.clear();
    		moves_done.add(move);

    		int score = -negaScout(board, moves_done,
    		    alpha, beta, 0, getOpponent(getColor()));

    		if (score == bestScore) {
    		  bestMoves.add(move);
    		} else
    		if (score > bestScore) {
    		  bestMoves.clear();
    		  bestMoves.add(move);
          bestScore = score;
    		}

    	}

    	return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    private int score(final Board board, final Color player) {
      int score_a = 0;
      int score_b = 0;
      int count_a = 12;
      int count_b = 12;

      for (int x = 0; x < board.getSize(); x++)
        for (int y = 0; y < board.getSize(); y++)
          if (board.getState(x, y) == Color.PLAYER1) {
            score_a += WEIGHT_ARRAY[x][y];
            count_a--;
          } else
          if (board.getState(x, y) == Color.PLAYER2) {
            score_b += WEIGHT_ARRAY[x][y];
            count_b--;
          }

      return (player == Color.PLAYER1) ? (
              score_a - score_b + (count_a == 1 ? 10 : 0)
          ) : (
              score_b - score_a + (count_b == 1 ? 10 : 0)
          );
    }


    private int negaScout(final OurBoard board, final List<Move> moves_done,
        int alpha, int beta, int d, final Color player ) {

      int a, b;

      TypicalBoard current_board = board.applyMoves(moves_done);

      if (d == MAX_DEPTH)
        return score(current_board, player);

      a = alpha;
	    b = beta;

	    for (Move move : current_board.getMovesFor(player)) {
	        moves_done.add(move);

	        int t = -negaScout(board, moves_done,
	            -b, -a, d+1, getOpponent(player));

	        if ( (t > a) && (t < beta) && (d < MAX_DEPTH - 1))
	          a = -negaScout(board, moves_done,
	              -beta, -t, d+1, getOpponent(player));

	        moves_done.remove(moves_done.size() - 1);

	        a = Math.max(a,t);

	        if (a >= beta)
	          return a;

	        b = a + 1;
	    }

     return a;
   }

    private class OurBoard {
      private TypicalBoard board_;

      public OurBoard(TypicalBoard board) {
        board_ = board;
      }

      public TypicalBoard applyMoves(List<Move> moves) {
        TypicalBoard board = board_.clone();

        for (Move move : moves) {
          board.doMove(move);
        }

        return board;
      }
    }

}
