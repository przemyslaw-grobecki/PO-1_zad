package MoveValidators;

import Board.IBoardPrototype;
import edu.uj.po.interfaces.ChessPiece;
import edu.uj.po.interfaces.Move;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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
        AtomicBoolean isEligible = new AtomicBoolean(true);
        if(move.initialPosition().file() != move.finalPosition().file() && next.isPresent()){
            return next.get().Validate(move, board);
        }
        var maybePawn = board.GetPieces()
            .stream()
            .filter(piece ->
                piece.getPosition().file() == move.initialPosition().file() &&
                    piece.getPosition().rank() == move.initialPosition().rank() &&
                    piece.pieceType == ChessPiece.PAWN)
            .findFirst();
        maybePawn.ifPresent(pawn -> isEligible.set((board.GetPieces()
            .stream()
            .noneMatch(piece ->
                piece.getPosition().file() == move.finalPosition().file() &&
                    piece.getPosition().rank() == move.finalPosition().rank()))));
        if(isEligible.get() && next.isPresent()){
            isEligible.set(next.get().Validate(move, board));
        }
        return isEligible.get();
    }
}
