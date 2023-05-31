package MoveValidators;

import edu.uj.po.interfaces.*;
import Board.IBoardPrototype;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.abs;

public class PieceOnTheWayValidator implements MoveValidator {
    private Optional<MoveValidator> next;
    public PieceOnTheWayValidator(MoveValidator next){
        this.next = Optional.of(next);
    }
    public PieceOnTheWayValidator() {
        this.next = Optional.empty();
    }
    @Override
    public boolean Validate(Move move, IBoardPrototype board) {
        //Validation
        var pieceMoving = board.GetPiece(move.initialPosition());
        if(pieceMoving.isEmpty()){
            return false;
        }
        if(pieceMoving.get().pieceType == ChessPiece.KNIGHT ||
                pieceMoving.get().pieceType == ChessPiece.PAWN ||
            pieceMoving.get().pieceType == ChessPiece.KING){
            return next.map(moveValidator -> moveValidator.Validate(move, board)).orElse(true);
        }
        var isEligible = board.GetPiecesBetween(move.initialPosition(), move.finalPosition()).isEmpty();
        return next.map(moveValidator -> moveValidator.Validate(move, board)).orElse(isEligible);
    }
}
