package edu.uj.po.interfaces.MoveValidators;

import edu.uj.po.interfaces.IBoardPrototype;
import edu.uj.po.interfaces.Move;
import edu.uj.po.interfaces.MoveValidators.MoveValidator;

public class StartingValidator implements MoveValidator {
    private MoveValidator next;
    public StartingValidator(MoveValidator moveValidator){
        this.next = moveValidator;
    }
    @Override
    public boolean Validate(Move move, IBoardPrototype board) {
        return next.Validate(move, board);
    }
}
