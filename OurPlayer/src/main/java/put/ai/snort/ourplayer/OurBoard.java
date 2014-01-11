package put.ai.snort.ourplayer;

import java.util.List;

import put.ai.snort.game.Move;
import put.ai.snort.game.TypicalBoard;

public class OurBoard {
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
