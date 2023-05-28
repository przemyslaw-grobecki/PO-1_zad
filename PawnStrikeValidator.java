package edu.uj.po.interfaces;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class PawnStrikeValidator implements MoveValidator {
    private Optional<MoveValidator> next;
    public PawnStrikeValidator(MoveValidator next){
        this.next = Optional.of(next);
    }
    public PawnStrikeValidator() {
        this.next = Optional.empty();
    }
    @Override
    public boolean Validate(Move move, IBoardPrototype board) {
        //Validation
        AtomicBoolean isEligible = new AtomicBoolean(true);
        if(move.initialPosition().file() == move.finalPosition().file() && next.isPresent()){
            return next.get().Validate(move, board);
        }
        var maybePawn = board.GetPieces()
            .stream()
            .filter(piece ->
                piece.getPosition().file() == move.initialPosition().file() &&
                piece.getPosition().rank() == move.initialPosition().rank() &&
                piece.pieceType == ChessPiece.PAWN)
            .findFirst();
        maybePawn.ifPresent(pawn -> {
            if(board.GetPieces().stream().noneMatch(piece ->
                    piece.getPosition().file() == move.finalPosition().file() &&
                    piece.getPosition().rank() == move.finalPosition().rank() &&
                    piece.color != pawn.color)){
                //Check for En Passant
                if(pawn.color == Color.BLACK && move.initialPosition().rank() == Rank.FOURTH ||
                    pawn.color == Color.WHITE && move.initialPosition().rank() == Rank.FIFTH){
                    isEligible.set(board.GetPieces().stream().anyMatch(piece ->
                        piece.color == Color.WHITE &&
                        piece.pieceType == ChessPiece.PAWN &&(
                        piece.getPosition().file().ordinal() == pawn.getPosition().file().ordinal() + 1 ||
                        piece.getPosition().file().ordinal() == pawn.getPosition().file().ordinal() -1)
                    ));
                }else {
                    isEligible.set(false);
                }
            }
        });

        if(isEligible.get() && next.isPresent()){
            isEligible.set(next.get().Validate(move, board));
        }
        return isEligible.get();
    }
}
