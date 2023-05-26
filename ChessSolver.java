package edu.uj.po.interfaces;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ChessSolver implements Setup, Solver {
    private IBoardPrototype board = new Board();
    private PieceFactory pieceFactory = new PieceFactory();

    @Override
    public void reset() {
        board.GetPieces().clear();
    }

    @Override
    public void addChessPiece(Position position, Color color, ChessPiece piece) {
        board.GetPieces().add(pieceFactory.CreatePiece(piece, position, color));
    }

    @Override
    public Optional<Move> findMateInOneMove(Color color) {
        AtomicReference<Optional<Move>> mateMove = new AtomicReference<>(Optional.empty());
        board.GetPieces().stream()
            .filter(pieceThatAttacks -> pieceThatAttacks.color == color)
            .forEach(pieceThatAttacks -> {
                var possibleAttackingMoves = pieceThatAttacks.ListAllPossibleMoves();
                possibleAttackingMoves.forEach(attackingMove -> {
                    var boardBeforeFirstMove = board.Copy();
                    if(boardBeforeFirstMove.MovePiece(attackingMove)){
                        if(boardBeforeFirstMove.CheckForMate(color)){
                            mateMove.set(Optional.of(attackingMove));
                            boardBeforeFirstMove.GetPieces().stream()
                                .filter(pieceThatDefends -> pieceThatDefends.color != color)
                                .forEach(pieceThatDefends -> {
                                    var possibleDefendingMoves = pieceThatDefends.ListAllPossibleMoves();
                                    possibleDefendingMoves.forEach(defendingMove -> {
                                        var boardBeforeSecondMove = board.Copy();
                                        if(boardBeforeSecondMove.MovePiece(defendingMove)){
                                            if(!boardBeforeSecondMove.CheckForMate(color)){
                                                mateMove.set(Optional.empty());
                                            }
                                        }
                                    });
                                });
                        }
                    }
                });
            });
        return mateMove.get();
    }

    @Override
    public Optional<Move> findStalemateInOneMove(Color color) {
        AtomicReference<Optional<Move>> mateMove = new AtomicReference<>(Optional.empty());
        board.GetPieces().stream()
            .filter(pieceThatAttacks -> pieceThatAttacks.color == color)
            .forEach(pieceThatAttacks -> {
                var possibleAttackingMoves = pieceThatAttacks.ListAllPossibleMoves();
                possibleAttackingMoves.forEach(attackingMove -> {
                    var boardBeforeFirstMove = board.Copy();
                    if(boardBeforeFirstMove.MovePiece(attackingMove)){
                        if(boardBeforeFirstMove.CheckForStalemate(color)){
                            mateMove.set(Optional.of(attackingMove));
                            boardBeforeFirstMove.GetPieces().stream()
                                .filter(pieceThatDefends -> pieceThatDefends.color != color)
                                .forEach(pieceThatDefends -> {
                                    var possibleDefendingMoves = pieceThatDefends.ListAllPossibleMoves();
                                    possibleDefendingMoves.forEach(defendingMove -> {
                                        var boardBeforeSecondMove = board.Copy();
                                        if(boardBeforeSecondMove.MovePiece(defendingMove)){
                                            if(!boardBeforeSecondMove.CheckForStalemate(color)){
                                                mateMove.set(Optional.empty());
                                            }
                                        }
                                    });
                                });
                        }
                    }
                });
            });
        return mateMove.get();
    }
}
