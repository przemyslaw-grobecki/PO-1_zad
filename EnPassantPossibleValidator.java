package edu.uj.po.interfaces;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class EnPassantPossibleValidator implements MoveValidator {
    private Optional<MoveValidator> next;
    public EnPassantPossibleValidator(MoveValidator next){
        this.next = Optional.of(next);
    }
    public EnPassantPossibleValidator() {
        this.next = Optional.empty();
    }
    @Override
    public boolean Validate(Move move, IBoardPrototype board) {
        //Validation
        AtomicBoolean isEligible = new AtomicBoolean(true);
        if(move.initialPosition().file() == move.finalPosition().file() && next.isPresent()){
            return next.get().Validate(move, board);
        }
        var maybeEnPassantPiece = board.GetPieces()
            .stream()
            .filter(piece ->
                piece.getPosition().file() == move.initialPosition().file() &&
                piece.getPosition().rank() == move.initialPosition().rank() &&
                piece.pieceType == ChessPiece.PAWN &&
                ((piece.getPosition().rank() == Rank.FOURTH && piece.color == Color.BLACK) ||
                (piece.getPosition().rank() == Rank.FIFTH && piece.color == Color.WHITE)))
            .findFirst();
        if (maybeEnPassantPiece.isPresent()){
            switch (maybeEnPassantPiece.get().getPosition().file()){
                case a:
                    isEligible.set(board.GetPieces()
                            .stream()
                            .anyMatch(pieceToKill -> pieceToKill.getPosition().file() == File.b &&
                                    pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()));
                    break;
                case b:
                    isEligible.set(board.GetPieces()
                        .stream()
                        .anyMatch(pieceToKill -> (pieceToKill.getPosition().file() == File.a &&
                            pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()) ||
                            pieceToKill.getPosition().file() == File.c &&
                            pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()));
                    break;
                case c:
                    isEligible.set(board.GetPieces()
                        .stream()
                        .anyMatch(pieceToKill -> (pieceToKill.getPosition().file() == File.b &&
                            pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()) ||
                            pieceToKill.getPosition().file() == File.d &&
                                pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()));
                    break;
                case d:
                    isEligible.set(board.GetPieces()
                        .stream()
                        .anyMatch(pieceToKill -> (pieceToKill.getPosition().file() == File.c &&
                            pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()) ||
                            pieceToKill.getPosition().file() == File.e &&
                                pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()));
                    break;
                case e:
                    isEligible.set(board.GetPieces()
                            .stream()
                            .anyMatch(pieceToKill -> (pieceToKill.getPosition().file() == File.d &&
                                    pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()) ||
                                    pieceToKill.getPosition().file() == File.f &&
                                            pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()));
                    break;
                case f:
                    isEligible.set(board.GetPieces()
                        .stream()
                        .anyMatch(pieceToKill -> (pieceToKill.getPosition().file() == File.e &&
                            pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()) ||
                            pieceToKill.getPosition().file() == File.g &&
                                pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()));
                    break;
                case g:
                    isEligible.set(board.GetPieces()
                        .stream()
                        .anyMatch(pieceToKill -> (pieceToKill.getPosition().file() == File.f &&
                            pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()) ||
                            pieceToKill.getPosition().file() == File.h &&
                                pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()));
                    break;
                case h:
                    isEligible.set(board.GetPieces()
                        .stream()
                        .anyMatch(pieceToKill -> pieceToKill.getPosition().file() == File.g &&
                            pieceToKill.getPosition().rank() == maybeEnPassantPiece.get().getPosition().rank()));
                    break;
                default:
            }
        }

        if(isEligible.get() && next.isPresent()){
            isEligible.set(next.get().Validate(move, board));
        }
        return isEligible.get();
    }
}
