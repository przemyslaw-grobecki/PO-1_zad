package MoveValidators;

import Board.IBoardPrototype;
import edu.uj.po.interfaces.Move;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class PieceOfSameColorAlreadyOnTileValidator implements MoveValidator {
    private Optional<MoveValidator> next;
    public PieceOfSameColorAlreadyOnTileValidator(MoveValidator next){
        this.next = Optional.of(next);
    }
    public PieceOfSameColorAlreadyOnTileValidator() {
        this.next = Optional.empty();
    }
    @Override
    public boolean Validate(Move move, IBoardPrototype board) {
        //Validation
        var movingPiece = board.GetPiece(move.initialPosition());
        var pieceOnTile = board.GetPiece(move.finalPosition());
        if(movingPiece.isEmpty()){
            return false;
        }
        if (pieceOnTile.isEmpty()){
            return next.map(moveValidator -> moveValidator.Validate(move, board)).orElse(true);
        }
        var isEligible = movingPiece.get().color != pieceOnTile.get().color;
        if(isEligible && next.isPresent()){
            isEligible = next.get().Validate(move, board);
        }
        return isEligible;
    }
}
