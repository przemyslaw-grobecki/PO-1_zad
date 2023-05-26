package edu.uj.po.interfaces;

public class StartingValidator implements MoveValidator{
    private MoveValidator next;
    public StartingValidator(MoveValidator moveValidator){
        this.next = moveValidator;
    }
    @Override
    public boolean Validate(Move move, IBoardPrototype board) {
        return next.Validate(move, board);
    }
}
