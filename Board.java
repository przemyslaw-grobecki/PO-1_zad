package edu.uj.po.interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Board implements IBoardPrototype {

    private ArrayList<Piece> pieces;
    private MoveValidator moveValidator;
    public Board(){
        this.pieces = new ArrayList<Piece>();
        this.moveValidator = new StartingValidator(
            new PieceOfSameColorAlreadyOnTileValidator(
                new EnPassantPossibleValidator(
                    new PieceOnTheWayValidator(
                    )
                )
            )
        );
    }
    public Board(ArrayList<Piece> pieces){
        this.pieces = new ArrayList<>(pieces);
        this.moveValidator = new StartingValidator(
            new PieceOfSameColorAlreadyOnTileValidator()
        );
    }

    @Override
    public boolean MovePiece(Move move){
        AtomicBoolean hasMoved = new AtomicBoolean(false);
        var pieceToMove = this.pieces.stream().filter(piece -> piece.getPosition().file() == move.initialPosition().file() &&
                piece.getPosition().rank() == move.initialPosition().rank()).findFirst();
        pieceToMove.ifPresent(piece -> {
            if(moveValidator.Validate(move, this)) {
                piece.setPosition(move.finalPosition());
                hasMoved.set(true);
            }
        });
        return hasMoved.get();
    }

    @Override
    public boolean CheckForMate(Color color){
        AtomicBoolean isMate = new AtomicBoolean(false);
        var king = pieces.stream()
            .filter(piece -> piece.color != color && piece.pieceType == ChessPiece.KING)
            .findFirst()
            .get();
        pieces.stream()
            .filter(piece -> piece.color == color)
            .forEach(piece -> {
                var possibleMoves = piece.ListAllPossibleMoves();
                possibleMoves.stream()
                    .filter(move -> move.finalPosition() == king.getPosition())
                    .forEach(move -> {
                        if(moveValidator.Validate(move, this.Copy())){
                            isMate.set(true);
                        }
                    });
            });
        return isMate.get();
    }

    @Override
    public boolean CheckForStalemate(Color color){
        AtomicBoolean isStalemate = new AtomicBoolean(false);
        pieces.stream()
                .filter(piece -> piece.color == color)
                .forEach(piece -> {
                    var possibleMoves = piece.ListAllPossibleMoves();
                    possibleMoves.forEach(move -> {
                        if(moveValidator.Validate(move, this.Copy())){
                            isStalemate.set(true);
                        }
                    });
                });
        return isStalemate.get();
    }

    @Override
    public List<Piece> GetPieces() {
        return pieces;
    }

    @Override
    public IBoardPrototype Copy() {
        var piecesCopy = new ArrayList<Piece>();
        Collections.copy(piecesCopy, this.pieces);
        return new Board(piecesCopy);
    }
}
