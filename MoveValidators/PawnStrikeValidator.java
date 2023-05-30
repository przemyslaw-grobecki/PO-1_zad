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
        AtomicBoolean isEligible = new AtomicBoolean(true);
        AtomicReference<Piece> isEnPassant = new AtomicReference<>(null);
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
                    isEligible.set(board.GetPieces().stream().anyMatch(piece -> {
                        var match = piece.pieceType == ChessPiece.PAWN && (
                            piece.getPosition().file().ordinal() == pawn.getPosition().file().ordinal() + 1 ||
                            piece.getPosition().file().ordinal() == pawn.getPosition().file().ordinal() - 1);
                        if(match){
                            isEnPassant.set(piece);
                        }
                        return match;
                    }));
                }else {
                    isEligible.set(false);
                }
            }
        });
        if(isEnPassant.get() != null){
            enPassantHandler.HandleEnPassant(isEnPassant.get());
        }

        if(isEligible.get() && next.isPresent()){
            isEligible.set(next.get().Validate(move, board));
        }
        return isEligible.get();
    }
}
