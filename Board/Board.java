package Board;

import MoveValidators.*;
import Pieces.Piece;
import edu.uj.po.interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Board implements IBoardPrototype {

    private Piece isEnPassantDetected = null;
    private ArrayList<Piece> pieces;
    private MoveValidator moveValidator;
    public Board(){
        this.pieces = new ArrayList<>();
        this.moveValidator = new StartingValidator(
            new PieceOfSameColorAlreadyOnTileValidator(
                new PawnStrikeValidator(
                    new PawnMoveValidator(
                        new PieceOnTheWayValidator()
                    ), (piece -> isEnPassantDetected = piece)
                )
            )
        );
    }
    public Board(ArrayList<Piece> pieces){
        this.pieces = new ArrayList<>(pieces);
        this.moveValidator = new StartingValidator(
            new PieceOfSameColorAlreadyOnTileValidator(
                new PawnStrikeValidator(
                    new PawnMoveValidator(
                        new PieceOnTheWayValidator()
                    ), (piece -> isEnPassantDetected = piece)
                )
            )
        );
    }

    @Override
    public boolean MovePiece(Move move){
        //System.out.printf("Moving piece from %s to %s%n", move.initialPosition(), move.finalPosition());
        AtomicBoolean hasMoved = new AtomicBoolean(false);
        var pieceToMove = this.pieces.stream().filter(piece -> piece.getPosition().file() == move.initialPosition().file() &&
                piece.getPosition().rank() == move.initialPosition().rank()).findFirst();
        var pieceOnTheWayMaybe = this.pieces.stream().filter(piece -> piece.getPosition().file() == move.finalPosition().file() &&
                piece.getPosition().rank() == move.finalPosition().rank()).findFirst();
        pieceToMove.ifPresent(piece -> {
            if(moveValidator.Validate(move, this)) {
                piece.setPosition(move.finalPosition());
                //Still there is a need to check if king is vulnerable
                var enemyTeam = pieces.stream().filter(enemy -> piece.color != enemy.color).toList();
                var alliedKing = pieces.stream().filter(k -> k.color == piece.color && k.pieceType == ChessPiece.KING).findFirst().get();
                var isKingAlive = enemyTeam.stream().noneMatch(enemy -> {
                    var moves = enemy.ListAllPossibleMoves();
                    return moves.stream()
                        .filter(enemyMove -> enemyMove.finalPosition().file() == alliedKing.getPosition().file() &&
                            enemyMove.finalPosition().rank() == alliedKing.getPosition().rank())
                        .anyMatch(enemyMove -> moveValidator.Validate(enemyMove, this));
                });
                if (isKingAlive) {
                    if (piece.pieceType == ChessPiece.PAWN) {
                        if ((piece.color == Color.WHITE && piece.getPosition().rank() == Rank.EIGHTH) ||
                                (piece.color == Color.BLACK && piece.getPosition().rank() == Rank.FIRST)) {
                            piece.pieceType = ChessPiece.QUEEN;
                        }
                    }
                    pieceOnTheWayMaybe.ifPresent(pieceOnTheWay ->
                            pieces.remove(pieceOnTheWay));
                    if (isEnPassantDetected != null) {
                        pieces.remove(isEnPassantDetected);
                        isEnPassantDetected = null;
                    }
                    hasMoved.set(true);
                }
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
                    .filter(move -> move.finalPosition().file() == king.getPosition().file() &&
                            move.finalPosition().rank() == king.getPosition().rank())
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
        ArrayList<Piece> piecesCopy = new ArrayList<Piece>();
        this.pieces.forEach(piece -> {
            piecesCopy.add(piece.clone());
        });
        return new Board(piecesCopy);
    }
}
