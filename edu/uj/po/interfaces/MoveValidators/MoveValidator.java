package edu.uj.po.interfaces.MoveValidators;

import edu.uj.po.interfaces.IBoardPrototype;
import edu.uj.po.interfaces.Move;

public interface MoveValidator {
    boolean Validate(Move move, IBoardPrototype board);
}
