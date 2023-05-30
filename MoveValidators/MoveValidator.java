package MoveValidators;

import Board.IBoardPrototype;
import edu.uj.po.interfaces.Move;

public interface MoveValidator {
    boolean Validate(Move move, IBoardPrototype board);
}
