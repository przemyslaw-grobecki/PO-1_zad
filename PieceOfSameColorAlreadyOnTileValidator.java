package edu.uj.po.interfaces;

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
        AtomicBoolean isEligible = new AtomicBoolean(true);

        board.GetPieces()
            .stream()
            .filter(piece -> piece.getPosition().rank() == move.finalPosition().rank() &&
                piece.getPosition().file() == move.finalPosition().file())
            .findFirst()
            .ifPresent(pTwo -> {
                board.GetPieces()
                    .stream()
                    .filter(piece -> piece.getPosition().rank() == move.initialPosition().rank() &&
                        piece.getPosition().file() == move.initialPosition().file())
                    .findFirst()
                    .ifPresent(pOne -> {
                        isEligible.set(pTwo.color != pOne.color);
                    });
        });
        if(isEligible.get() && next.isPresent()){
            isEligible.set(next.get().Validate(move, board));
        }
        return isEligible.get();
    }
}
