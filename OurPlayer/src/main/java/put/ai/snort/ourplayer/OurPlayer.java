package put.ai.snort.ourplayer;

import java.util.List;
import java.util.Random;

import put.ai.snort.game.Board;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;
import put.ai.snort.linesofaction.*;

public class OurPlayer extends Player {

    private Random random=new Random(0xdeadbeef);

    @Override
    public String getName() {
        return "Leo";
    }

    @Override
    public Move nextMove(Board b) {
        List<Move> moves = b.getMovesFor(getColor());
        
        for (Move _move : moves) {
          LoAMove move = (LoAMove) _move;
          System.out.println("x:" + move.getDstX());
          System.out.println(move);
        }
        
        return moves.get(random.nextInt(moves.size()));
    }
}
