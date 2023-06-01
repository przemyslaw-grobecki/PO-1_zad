package Board;

import MoveValidators.*;
import Pieces.Piece;
import Pieces.PieceFactory;
import edu.uj.po.interfaces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.abs;

public class Board implements IBoardPrototype {

    private Piece isEnPassantDetected = null;
    private PieceFactory pieceFactory = new PieceFactory();
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
        boolean hasMoved = false;
        var pieceToMove = this.GetPiece(move.initialPosition());
        var pieceOnTheWayMaybe = this.GetPiece(move.finalPosition());
        if(pieceToMove.isEmpty()){
            return false;
        }
        if(moveValidator.Validate(move, this)) {
            //Make a move
            pieceToMove.get().setPosition(move.finalPosition());
            //Strike if possible
            pieceOnTheWayMaybe.ifPresent(pieceOnTheWay ->
                pieces.remove(pieceOnTheWay));
            //Promote if possible
            if (pieceToMove.get().pieceType == ChessPiece.PAWN) {
                if ((pieceToMove.get().color == Color.WHITE && pieceToMove.get().getPosition().rank() == Rank.EIGHTH) ||
                        (pieceToMove.get().color == Color.BLACK && pieceToMove.get().getPosition().rank() == Rank.FIRST)) {
                    pieces.remove(pieceToMove.get());
                    pieces.add(pieceFactory.CreatePiece(ChessPiece.QUEEN, pieceToMove.get().getPosition(), pieceToMove.get().color));
                    pieceToMove = GetPiece(pieceToMove.get().getPosition());
                }
            }
            //Still there is a need to check if king is vulnerable
            var enemyTeam = this.GetOpposingTeam(pieceToMove.get().color);
            var alliedKing = this.GetFigures(pieceToMove.get().color, ChessPiece.KING).get(0);
            var isKingSafe = enemyTeam.stream().noneMatch(enemy -> {
                var moves = enemy.ListAllPossibleMoves();
                return moves.stream()
                    .filter(enemyMove -> enemyMove.finalPosition().file() == alliedKing.getPosition().file() &&
                        enemyMove.finalPosition().rank() == alliedKing.getPosition().rank())
                    .anyMatch(enemyMove -> moveValidator.Validate(enemyMove, this.Copy()));
            });
            if (isKingSafe) {
                if (isEnPassantDetected != null) {
                    pieces.remove(isEnPassantDetected);
                    isEnPassantDetected = null;
                }
                hasMoved = true;
            }
        }
        return hasMoved;
    }

    @Override
    public boolean CheckForMate(Color color){
        AtomicBoolean isMate = new AtomicBoolean(false);
        var king = GetFigures(Arrays.stream(Color.values()).filter(c -> c != color).findFirst().get(), ChessPiece.KING).get(0);
        GetAlliedTeam(color)
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
        this.GetAlliedTeam(color)
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
    public List<Piece> GetAlliedTeam(Color color){
        return pieces.stream().filter(piece -> piece.color == color).toList();
    }

    @Override
    public List<Piece> GetOpposingTeam(Color color) {
        return pieces.stream().filter(piece -> piece.color != color).toList();
    }

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
        int startFile = source.file().ordinal();
        int startRank = source.rank().ordinal();
        int endFile = destination.file().ordinal();
        int endRank = destination.rank().ordinal();

        int horizontalMovement = endFile - startFile;
        int verticalMovement = endRank - startRank;

        int fileDirection = (endFile > startFile) ? 1 : -1;
        int rankDirection = (endRank > startRank) ? 1 : -1;

        if (isDiagonalMove(verticalMovement, horizontalMovement)) {
            for (int i = startFile + fileDirection, j = startRank + rankDirection; i != endFile; i += fileDirection, j += rankDirection) {
                this.GetPiece(i, j).ifPresent(piecesBetween::add);
            }
        } else {
            if (isVerticalMove(horizontalMovement)) {
                for (int j = startRank + rankDirection; j != endRank; j += rankDirection) {
                    this.GetPiece(startFile,j).ifPresent(piecesBetween::add);
                }
            } else if (isHorizontalMove(verticalMovement)) {
                for (int i = startFile + fileDirection; i != endFile; i += fileDirection) {
                    this.GetPiece(i, startRank).ifPresent(piecesBetween::add);
                }
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

    private boolean isDiagonalMove(int verticalMovement, int horizontalMovement) {
        return Math.abs(verticalMovement) == Math.abs(horizontalMovement);
    }

    private boolean isVerticalMove(int horizontalMovement) {
        return horizontalMovement == 0;
    }

    private boolean isHorizontalMove(int verticalMovement) {
        return verticalMovement == 0;
    }
}
