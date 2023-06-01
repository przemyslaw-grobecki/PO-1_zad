import Board.Board;
import Board.IBoardPrototype;
import Pieces.PieceFactory;
import edu.uj.po.interfaces.*;

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
        board.GetAlliedTeam(color)
            .stream()
            .anyMatch(pieceThatAttacks -> {
                var possibleAttackingMoves = pieceThatAttacks.ListAllPossibleMoves();
                var isMate = possibleAttackingMoves.stream().anyMatch(attackingMove -> {
                    var boardBeforeFirstMove = board.Copy();
                    if(boardBeforeFirstMove.MovePiece(attackingMove)){
                        if(boardBeforeFirstMove.CheckForMate(color)){
                            var noAvailableDefences = boardBeforeFirstMove.GetOpposingTeam(color)
                                .stream()
                                .allMatch(pieceThatDefends -> {
                                    var possibleDefendingMoves = pieceThatDefends.ListAllPossibleMoves();
                                    var noCounterMoves = possibleDefendingMoves.stream().allMatch(defendingMove -> {
                                        var boardBeforeSecondMove = boardBeforeFirstMove.Copy();
                                        if(boardBeforeSecondMove.MovePiece(defendingMove)){
                                            return boardBeforeSecondMove.CheckForMate(color);
                                        }
                                        return true;
                                    });
                                    return noCounterMoves;
                                });
                            if(noAvailableDefences){
                                mateMove.set(Optional.of(attackingMove));
                                return true;
                            }
                        }
                    }
                    return false;
                });
                return isMate;
            });
        return mateMove.get();
    }

    @Override
    public Optional<Move> findStalemateInOneMove(Color color) {
        AtomicReference<Optional<Move>> stalemateMove = new AtomicReference<>(Optional.empty());
        board.GetAlliedTeam(color)
            .stream()
            .anyMatch(pieceThatAttacks -> {
                var possibleAttackingMoves = pieceThatAttacks.ListAllPossibleMoves();
                var isStalemate = possibleAttackingMoves.stream().anyMatch(attackingMove -> {
                    var boardBeforeFirstMove = board.Copy();
                    if(boardBeforeFirstMove.MovePiece(attackingMove)){
                        if(!boardBeforeFirstMove.CheckForMate(color)){
                            var noAvailableActions = boardBeforeFirstMove.GetOpposingTeam(color)
                                .stream()
                                .allMatch(pieceThatDefends -> {
                                    var possibleDefendingMoves = pieceThatDefends.ListAllPossibleMoves();
                                    var noAvailableMoves = possibleDefendingMoves.stream().noneMatch(defendingMove -> {
                                        var boardBeforeSecondMove = boardBeforeFirstMove.Copy();
                                        return boardBeforeSecondMove.MovePiece(defendingMove);
                                    });
                                    return noAvailableMoves;
                                });
                            if(noAvailableActions){
                                stalemateMove.set(Optional.of(attackingMove));
                                return true;
                            }
                        }
                    }
                    return false;
                });
                return isStalemate;
            });
        return stalemateMove.get();
    }

    public void PrintBoard(){
        board.GetPieces().forEach(piece -> {
            System.out.println();
            System.out.println(piece.getPosition());
            System.out.println(piece.color);
            System.out.println(piece.pieceType);
        });
    }
}
