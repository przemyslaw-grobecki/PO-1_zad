package MoveValidators;

import Board.EnPassantHandler;
import Pieces.Piece;
import edu.uj.po.interfaces.*;
import Board.IBoardPrototype;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PawnStrikeValidator implements MoveValidator {
    private Optional<MoveValidator> next;
    private EnPassantHandler enPassantHandler;
    public PawnStrikeValidator(MoveValidator next, EnPassantHandler enPassantHandler){
        this.next = Optional.of(next);
        this.enPassantHandler = enPassantHandler;
    }
    public PawnStrikeValidator(EnPassantHandler enPassantHandler) {
        this.next = Optional.empty();
        this.enPassantHandler = enPassantHandler;
    }
    @Override
    public boolean Validate(Move move, IBoardPrototype board) {
        //Validation
        var pieceMoving = board.GetPiece(move.initialPosition());
        if(pieceMoving.isEmpty()){
            return false;
        }
        if((move.initialPosition().file() == move.finalPosition().file() ||
                pieceMoving.get().pieceType != ChessPiece.PAWN) && next.isPresent()){
            return next.get().Validate(move, board);
        }
        var isEligible = true;
        AtomicReference<Piece> isEnPassant = new AtomicReference<>(null);

        var pieceToKill = board.GetPiece(move.finalPosition());
        if(pieceToKill.isEmpty()){
            if(pieceMoving.get().color == Color.BLACK && move.initialPosition().rank() == Rank.FOURTH ||
                pieceMoving.get().color == Color.WHITE && move.initialPosition().rank() == Rank.FIFTH){
               isEligible = board.GetPieces().stream().anyMatch(piece -> {
                    var match = piece.pieceType == ChessPiece.PAWN && (
                        piece.getPosition().file().ordinal() == pieceMoving.get().getPosition().file().ordinal() + 1 ||
                            piece.getPosition().file().ordinal() == pieceMoving.get().getPosition().file().ordinal() - 1);
                    if(match){
                        isEnPassant.set(piece);
                    }
                    return match;
                });
            }else {
                isEligible = false;
            }
        }
        if(isEnPassant.get() != null){
            enPassantHandler.HandleEnPassant(isEnPassant.get());
        }
        if(isEligible && next.isPresent()){
            isEligible = next.get().Validate(move, board);
        }
        return isEligible;
    }
}
