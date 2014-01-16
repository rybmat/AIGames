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
        return "Leo Vinci";
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

  /*  private int score(final Board board, final Color player) {
      int score_a = 0;
      int score_b = 0;
      int count_a = 0;
      int count_b = 0;
      int sc = 0;

      for (int x = 0; x < board.getSize(); x++)
        for (int y = 0; y < board.getSize(); y++)
          if (board.getState(x, y) == Color.PLAYER1) {
            score_a += WEIGHT_ARRAY[x][y];
            count_a++;
          } else
          if (board.getState(x, y) == Color.PLAYER2) {
            score_b += WEIGHT_ARRAY[x][y];
            count_b++;
          }

      sc = (player == Color.PLAYER1) ? (
              score_a - score_b + (count_a == 1 ? 10 : (count_b < 6 ? -2 : 0))
          ) : (
              score_b - score_a + (count_b == 1 ? 10 : (count_a < 6 ? -2 : 0))
          );
      sc += squares(board, player);
      return sc;
    }*/
    
    
    private int score(final Board board, final Color player) {	//by ryba
    	float squares_a = 0;
    	float squares_b = 0;
    	int tmp_count_a;
    	int tmp_count_b;
    	int square_score = 0;
    	
        int centr_score_a = 0;
        int centr_score_b = 0;
        int count_a = 0;
        int count_b = 0;
        int centr_score = 0;
    	
        
    	if (board.getState(0, 0) == Color.PLAYER1) {
            centr_score_a += WEIGHT_ARRAY[0][0];
            count_a++;
        } else if (board.getState(0, 0) == Color.PLAYER2) {
            centr_score_b += WEIGHT_ARRAY[0][0];
            count_b++;
        }
    	
    	if (board.getState(0, 1) == Color.PLAYER1) {
            centr_score_a += WEIGHT_ARRAY[0][1];
            count_a++;
        } else if (board.getState(0, 1) == Color.PLAYER2) {
            centr_score_b += WEIGHT_ARRAY[0][1];
            count_b++;
        }
    	
    	if (board.getState(1, 0) == Color.PLAYER1) {
            centr_score_a += WEIGHT_ARRAY[1][0];
            count_a++;
        } else if (board.getState(1, 0) == Color.PLAYER2) {
            centr_score_b += WEIGHT_ARRAY[1][0];
            count_b++;
        }
    	
        for (int x = 1; x < board.getSize(); x++)
            for (int y = 1; y < board.getSize(); y++) {
            	tmp_count_a = 0;
            	tmp_count_b = 0;
            	if (board.getState(x-1, y-1) == Color.PLAYER1) {
            		tmp_count_a++;
	            } else if (board.getState(x-1, y-1) == Color.PLAYER2) {
	                tmp_count_b++;
	            }
            	if (board.getState(x, y-1) == Color.PLAYER1) {
            		tmp_count_a++;
	            } else if (board.getState(x, y-1) == Color.PLAYER2) {
	                tmp_count_b++;
	            }
            	if (board.getState(x-1, y) == Color.PLAYER1) {
            		tmp_count_a++;
	            } else if (board.getState(x-1, y) == Color.PLAYER2) {
	                tmp_count_b++;
	            }
            	if (board.getState(x, y) == Color.PLAYER1) {
            		tmp_count_a++;
                    centr_score_a += WEIGHT_ARRAY[x][y];
                    count_a++;
	            } else if (board.getState(x, y) == Color.PLAYER2) {
	                tmp_count_b++;
	                centr_score_b += WEIGHT_ARRAY[x][y];
	                count_b++;
	            }
            	
            	squares_a += ( (tmp_count_a > 2) ? 1 : 0 );
            	squares_b += ( (tmp_count_b > 2) ? 1 : 0 );
            }
        
        centr_score = (player == Color.PLAYER1) ? (
                centr_score_a - centr_score_b + (count_a == 1 ? 10 : (count_b < 6 ? -2 : 0))
            ) : (
            		centr_score_b - centr_score_a + (count_b == 1 ? 10 : (count_a < 6 ? -2 : 0))
            );
    	
        square_score = (player == Color.PLAYER1) ? (int)( (squares_a/count_a) - (squares_b/count_b)) : (int)( (squares_b/count_b) - (squares_a/count_a) );
         	
        return centr_score + square_score;
    }

    
    private int negaScout(final OurBoard board, final List<Move> moves_done,
        int alpha, int beta, int d, final Color player ) {

      int a, b;

      TypicalBoard current_board = board.applyMoves(moves_done);

      if (d == MAX_DEPTH)
        return score(current_board, player);

      a = alpha;
	    b = beta;
	    
	   /* if (current_board.getWinner() == player){
	    	return INF;
	    }else if(current_board.getWinner() == getOpponent(player)){
	    	return -INF;
	    }*/
	    
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
