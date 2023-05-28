package edu.uj.po.interfaces.MoveValidators;

import edu.uj.po.interfaces.IBoardPrototype;
import edu.uj.po.interfaces.Move;

import java.util.Optional;

public class KingAssaultPossibleValidator implements MoveValidator {
    private Optional<MoveValidator> next;
    public KingAssaultPossibleValidator(MoveValidator next){
        this.next = Optional.of(next);
    }
    public KingAssaultPossibleValidator() {
        this.next = Optional.empty();
    }
    @Override
    public boolean Validate(Move move, IBoardPrototype board) {
        //Validation
        boolean isEligible = true;
        var pieceTakingMaybe = board.GetPieces().stream()
            .filter(piece -> piece.getPosition().rank() == move.finalPosition().rank() &&
                    piece.getPosition().file() == move.finalPosition().file()).findFirst();
        var pieceToBeTakenMaybe = board.GetPieces().stream()
                .filter(piece -> piece.getPosition().rank() == move.initialPosition().rank() &&
                        piece.getPosition().file() == move.initialPosition().file()).findFirst();
        if(!pieceTakingMaybe.isPresent()){
            return false;
        }
        if(pieceToBeTakenMaybe.isPresent()){
            isEligible = pieceTakingMaybe.get().color != pieceToBeTakenMaybe.get().color;
        }
        if(isEligible && next.isPresent()){
            isEligible = next.get().Validate(move, board);
        }
        return isEligible;
    }
}
