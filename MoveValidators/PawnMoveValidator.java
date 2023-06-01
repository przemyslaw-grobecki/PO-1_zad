package MoveValidators;

import Board.IBoardPrototype;
import edu.uj.po.interfaces.ChessPiece;
import edu.uj.po.interfaces.Move;

import java.util.Optional;

public class PawnMoveValidator implements MoveValidator {
    private Optional<MoveValidator> next;
    public PawnMoveValidator(MoveValidator next){
        this.next = Optional.of(next);
    }
    public PawnMoveValidator() {
        this.next = Optional.empty();
    }
    @Override
    public boolean Validate(Move move, IBoardPrototype board) {
        var movingPiece = board.GetPiece(move.initialPosition());
        if(movingPiece.isEmpty()){
            return false;
        }
        if((move.initialPosition().file() != move.finalPosition().file() ||
                movingPiece.get().pieceType != ChessPiece.PAWN) && next.isPresent()) {
            return next.get().Validate(move, board);
        }
        var isEligible = board.GetPiece(move.finalPosition()).isEmpty();
        if(isEligible && next.isPresent()){
            isEligible = next.get().Validate(move, board);
        }
        return isEligible;
    }
}
