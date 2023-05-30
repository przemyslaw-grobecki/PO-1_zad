package MoveValidators;

import edu.uj.po.interfaces.ChessPiece;
import Board.IBoardPrototype;
import edu.uj.po.interfaces.Move;

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
        AtomicBoolean isEligible = new AtomicBoolean(true);
        var verticalMovement = move.finalPosition().rank().ordinal() - move.initialPosition().rank().ordinal();
        var horizontalMovement = move.finalPosition().file().ordinal() - move.initialPosition().file().ordinal();

        var pieceMovingMaybe = board.GetPieces().stream()
            .filter(piece -> piece.getPosition().rank() == move.initialPosition().rank() &&
                    piece.getPosition().file() == move.initialPosition().file()).findFirst();
        pieceMovingMaybe.ifPresent(pieceMoving -> {
            if(pieceMoving.pieceType != ChessPiece.KNIGHT) {
                //Diagonal
                if(abs(verticalMovement) == abs(horizontalMovement)){
                    //Right && Up
                    if(horizontalMovement > 0 &&
                            verticalMovement > 0){
                        for (int i = move.initialPosition().file().ordinal() + 1; i < move.finalPosition().file().ordinal(); i++) {
                            for (int j = move.initialPosition().rank().ordinal() + 1; j < move.finalPosition().rank().ordinal(); j++){
                                if(i - move.initialPosition().file().ordinal() == j - move.initialPosition().rank().ordinal()){
                                    int finalI = i;
                                    int finalJ = j;
                                    isEligible.set(board.GetPieces().stream().noneMatch(
                                            piece -> piece.getPosition().file().ordinal() == finalI &&
                                                    piece.getPosition().rank().ordinal() == finalJ
                                    ));
                                    if(!isEligible.get()) break;
                                }
                            }
                            if(!isEligible.get()) break;
                        }
                    }
                    //Right && Down
                    else if (horizontalMovement > 0 &&
                            verticalMovement < 0){
                        for (int i = move.initialPosition().file().ordinal() + 1; i < move.finalPosition().file().ordinal(); i++) {
                            for (int j = move.initialPosition().rank().ordinal() - 1; j > move.finalPosition().rank().ordinal(); j--){
                                if(i - move.initialPosition().file().ordinal() == move.initialPosition().rank().ordinal() - j){
                                    int finalI = i;
                                    int finalJ = j;
                                    isEligible.set(board.GetPieces().stream().noneMatch(
                                            piece -> piece.getPosition().file().ordinal() == finalI &&
                                                    piece.getPosition().rank().ordinal() == finalJ
                                    ));
                                    if(!isEligible.get()) break;
                                }
                            }
                            if(!isEligible.get()) break;
                        }
                    }
                    //Left && Up
                    else if (horizontalMovement < 0 &&
                            verticalMovement > 0){
                        for (int i = move.initialPosition().file().ordinal() - 1; i > move.finalPosition().file().ordinal(); i--) {
                            for (int j = move.initialPosition().rank().ordinal() + 1; j < move.finalPosition().rank().ordinal(); j++){
                                if(move.initialPosition().file().ordinal() - i == j - move.initialPosition().rank().ordinal()){
                                    int finalI = i;
                                    int finalJ = j;
                                    isEligible.set(board.GetPieces().stream().noneMatch(
                                            piece -> piece.getPosition().file().ordinal() == finalI &&
                                                    piece.getPosition().rank().ordinal() == finalJ
                                    ));
                                    if(!isEligible.get()) break;
                                }
                            }
                            if(!isEligible.get()) break;
                        }
                    }
                    //Left && Down
                    else {
                        for (int i = move.initialPosition().file().ordinal() - 1; i > move.finalPosition().file().ordinal(); i--) {
                            for (int j = move.initialPosition().rank().ordinal() - 1; j > move.finalPosition().rank().ordinal(); j--){
                                if(move.initialPosition().file().ordinal() - i == move.initialPosition().rank().ordinal() - j){
                                    int finalI = i;
                                    int finalJ = j;
                                    isEligible.set(board.GetPieces().stream().noneMatch(
                                            piece -> piece.getPosition().file().ordinal() == finalI &&
                                                    piece.getPosition().rank().ordinal() == finalJ
                                    ));
                                    if(!isEligible.get()) break;
                                }
                            }
                            if(!isEligible.get()) break;
                        }
                    }
                }else {
                    //Vertical
                    if (horizontalMovement == 0) {
                        //Up
                        if (verticalMovement > 0) {
                            isEligible.set(board.GetPieces().stream().noneMatch(piece -> piece.getPosition().file().ordinal() == move.initialPosition().file().ordinal() &&
                                    piece.getPosition().rank().ordinal() < move.finalPosition().rank().ordinal() &&
                                    piece.getPosition().rank().ordinal() > move.initialPosition().rank().ordinal()));
                        }
                        //Down
                        else {
                            isEligible.set(board.GetPieces().stream().noneMatch(piece -> piece.getPosition().file().ordinal() == move.initialPosition().file().ordinal() &&
                                    piece.getPosition().rank().ordinal() > move.finalPosition().rank().ordinal() &&
                                    piece.getPosition().rank().ordinal() < move.initialPosition().rank().ordinal()));
                        }
                    }
                    //Horizontal
                    if (verticalMovement == 0) {
                        //Right
                        if (horizontalMovement > 0) {
                            isEligible.set(board.GetPieces().stream().noneMatch(piece -> piece.getPosition().rank().ordinal() == move.initialPosition().rank().ordinal() &&
                                    piece.getPosition().file().ordinal() < move.finalPosition().file().ordinal() &&
                                    piece.getPosition().file().ordinal() > move.initialPosition().file().ordinal()));
                        }
                        //Left
                        else {
                            isEligible.set(board.GetPieces().stream().noneMatch(piece -> piece.getPosition().rank().ordinal() == move.initialPosition().rank().ordinal() &&
                                    piece.getPosition().file().ordinal() > move.finalPosition().file().ordinal() &&
                                    piece.getPosition().file().ordinal() < move.initialPosition().file().ordinal()));
                        }
                    }
                }
            }
        });
        if(isEligible.get() && next.isPresent()){
            isEligible.set(next.get().Validate(move, board));
        }
        return isEligible.get();
    }
}
