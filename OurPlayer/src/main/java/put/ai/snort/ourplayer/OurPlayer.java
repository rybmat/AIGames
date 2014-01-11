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
    public static final int MAX_DEPTH = 2;
	
    private Random random=new Random(0xdeadbeef);

    @Override
    public String getName() {
        return "Vinci";
    }

    @Override
    public Move nextMove(Board brd) {
      int alpha = -INF;
      int beta = INF;
    	
    	Move bestMove = null;
      int bestScore = -INF;
    	
      List<Move> moves = brd.getMovesFor(getColor());
    	
      
    	for (Move move : moves) {
    		OurBoard board = new OurBoard((TypicalBoard) brd);
    	  List<Move> moves_done = new ArrayList<Move>();
    		moves_done.add(move);
    	  
    		if (bestMove == null)
    		  bestMove = move;
    		
    		int score = -negaScout(board, moves_done,
    		    alpha, beta, 0, getOpponent(getColor()));
        
    		if (score > bestScore) {
    		  bestMove = move;
          bestScore = score;
        }
    	  
    	}
      
    	return bestMove;
    }
    
   
    private int negaScout(OurBoard board, List<Move> moves_done, 
        int alpha, int beta, int d, Color player ) { 
    
      int a,b;
      
      if (d == MAX_DEPTH)
        return random.nextInt();
      
      a = alpha;
	    b = beta;
	    
	    List<Move> v = board.applyMoves(moves_done).getMovesFor(getColor());
	    
	    for (Move move : v) {
	        moves_done.add(move);
	        
	        int t = -negaScout(board, moves_done,
	            -b, -a, d+1, getOpponent(getColor()));
	        
	        if ( (t > a) && (t < beta) && (d < MAX_DEPTH - 1))
	          a = -negaScout(board, moves_done,
	              -beta, -t, d+1, getOpponent(getColor()));
	        
	        moves_done.remove(moves_done.size() - 1);
	        
	        a = Math.max(a,t);
	        
	        if (a >= beta)
	          return a;
	        
	        b = a + 1;
	    }

     return a;
   }
}
