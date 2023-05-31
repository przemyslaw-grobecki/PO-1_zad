package Board;

import MoveValidators.*;
import Pieces.Piece;
import edu.uj.po.interfaces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.abs;

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
    public List<Piece> GetTeam(Color color){
        return pieces.stream().filter(piece -> piece.color == color).toList();
    }

    @Override
    public List<Piece> GetFigures(Color color, ChessPiece pieceType){
        return pieces.stream().filter(piece -> piece.pieceType == pieceType && piece.color == color).toList();
    }

    @Override
    public Optional<Piece> GetPiece(Position position){
        return pieces.stream().filter(piece -> piece.getPosition().rank() == position.rank() &&
                piece.getPosition().file() == position.file()).findFirst();
    }

    @Override
    public Optional<Piece> GetPiece(int file, int rank) {
        return pieces.stream().filter(piece -> piece.getPosition().file().ordinal() == file &&
                piece.getPosition().rank().ordinal() == rank).findFirst();
    }

    @Override
    public Optional<Piece> GetPiece(File file, Rank rank) {
        return pieces.stream().filter(piece -> piece.getPosition().file() == file &&
                piece.getPosition().rank() == rank).findFirst();
    }

    @Override
    public List<Piece> GetPieces() {
        return pieces;
    }

    @Override
    public List<Piece> GetPiecesBetween(Position source, Position destination) {
        List<Piece> piecesBetween = new ArrayList<>();
        var horizontalMovement = destination.file().ordinal() - source.file().ordinal();
        var horizontalDirection = horizontalMovement > 0 ? 1 : -1;
        var verticalMovement = destination.rank().ordinal() - source.rank().ordinal();
        var verticalDirection = horizontalDirection > 0 ? 1 : -1;
        var isDiagonal = abs(horizontalMovement) == abs(verticalMovement);
        if(!isDiagonal && horizontalMovement != 0 && verticalMovement != 0){
            return Collections.emptyList();
        }

        if(isDiagonal){
            for (int file = source.file().ordinal() + horizontalDirection;
                 file * horizontalDirection < destination.file().ordinal() * horizontalDirection;
                 file = file + horizontalDirection){
                for (int rank = source.rank().ordinal() + verticalDirection;
                     rank * verticalDirection < destination.rank().ordinal() * verticalDirection;
                     rank = rank + verticalDirection) {
                    if(abs(file) - source.file().ordinal() == abs(rank) - source.rank().ordinal()){
                        this.GetPiece(file, rank).ifPresent(piecesBetween::add);
                    }
                }
            }
        }
        else {
            for (int file = source.file().ordinal() + horizontalDirection;
                 file * horizontalDirection < destination.file().ordinal() * horizontalDirection;
                 file = file + horizontalDirection) {
                this.GetPiece(file, source.rank().ordinal()).ifPresent(piecesBetween::add);
            }
            for (int rank = source.rank().ordinal() + verticalDirection;
                 rank * verticalDirection < destination.rank().ordinal() * verticalDirection;
                 rank = rank + verticalDirection) {
                this.GetPiece(source.file().ordinal(), rank).ifPresent(piecesBetween::add);
            }
        }
        return piecesBetween;
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
