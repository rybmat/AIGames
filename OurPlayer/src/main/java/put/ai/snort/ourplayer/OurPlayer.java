/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.ourplayer;

import java.util.List;
import java.util.Random;

import put.ai.snort.game.Board;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;
import put.ai.snort.linesofaction.*;

public class OurPlayer extends Player {
   // public Board brd;
    public int INF = Integer.MAX_VALUE;
    public int depth;
    public byte Player;
    public int alpha;
    public int beta;
	
    private Random random=new Random(0xdeadbeef);

    @Override
    public String getName() {
        return "Leo";
    }

    @Override
    public Move nextMove(Board brd) {
        this.alpha = -INF;
        this.beta = INF;
        this.depth = 0;
    
    	
    	Move bestMove = null;
        int best = -INF;
    	List<Move> moves = brd.getMovesFor(getColor());
    	
    	for(Move m : moves){
    		Board b = brd.clone();
    		b.doMove(m);
    		if (bestMove == null)
                bestMove = m;
    		int score = -negaScout(b,alpha,beta,0,getOpponent(getColor()));
            if (score > best){
                    bestMove = m;
                    best = score;
            }
    	}
        return bestMove;
    }
    
   
    private int negaScout( Board brd, int alpha, int beta, int d, Color player ){ 
        int a,b;
        if (d == this.depth)
                return random.nextInt();
        a = alpha;
	    b = beta;
	    List<Move> v = brd.getMovesFor(getColor());
	    for (int i = 0; i< v.size(); i++){
	        Board bd = brd.clone();
	        Move m = v.get(i);
	        bd.doMove(m);
	        int t = - negaScout(bd,-b,-a,d+1,getOpponent(getColor()));
	        if ( (t > a) && (t < beta) && (i > 0) && (d < this.depth -1))
	                a = -negaScout(bd,-beta,-t,d+1,getOpponent(getColor()));
	        a = Math.max(a,t);
	        if (a >= beta)
	                return a;
	        b = a + 1;
    }
    return a;
}
}
