package MoveValidators;

import Board.IBoardPrototype;
import edu.uj.po.interfaces.Move;

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
