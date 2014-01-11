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
    public static final int MAX_DEPTH = 4;
	
    private Random random=new Random(System.currentTimeMillis());
    
    private static int WEIGHT_ARRAY[][];

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
    	
      
    	for (Move move : moves) {
    		OurBoard board = new OurBoard((TypicalBoard) brd);
    	  List<Move> moves_done = new ArrayList<Move>();
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
        if (bestMoves.isEmpty())
          bestMoves.add(move);
        
    	}
      
    	return bestMoves.get(random.nextInt(bestMoves.size()));
    }
    
    private int center_score(Board board, Color player) {
      int score = 0;
      
      for (int x = 0; x < board.getSize(); x++)
        for (int y = 0; y < board.getSize(); y++)
          if (board.getState(x, y) == player)
            score += WEIGHT_ARRAY[x][y];
      
      return score;
    }
    
    private int score(Board board, Color player) {
     //if (board.getWinner() == player) {
     //   return INF;
     //} else 
        int score = 0;
      
        score += center_score(board, player) - center_score(board, getOpponent(player));
      
        
        return score;
     //}
      
    }
    
   
    private int negaScout(OurBoard board, List<Move> moves_done, 
        int alpha, int beta, int d, Color player ) { 
    
      int a, b;
      
      TypicalBoard current_board = board.applyMoves(moves_done);
      
      if (d == MAX_DEPTH)
        return score(current_board, player);
      
      a = alpha;
	    b = beta;
	    
	    for (Move move : current_board.getMovesFor(getColor())) {
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
