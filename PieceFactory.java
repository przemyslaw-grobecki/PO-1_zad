package edu.uj.po.interfaces;

import java.util.ArrayList;
import java.util.List;

public class PieceFactory {
    public Piece CreatePiece(ChessPiece pieceType, Position initialPosition, Color color){
        List<MoveGeneratorCommand> moveGeneratorCommandList = new ArrayList<>();
        switch (pieceType){
            case BISHOP -> moveGeneratorCommandList.add(new DiagonalMoveGeneratorCommand());
            case KING -> {
                moveGeneratorCommandList.add(new RangeMoveGeneratorCommandDecorator(new DiagonalMoveGeneratorCommand(), 1));
                moveGeneratorCommandList.add(new RangeMoveGeneratorCommandDecorator(new HorizontalMoveGeneratorCommand(), 1));
                moveGeneratorCommandList.add(new RangeMoveGeneratorCommandDecorator(new VerticalMoveGeneratorCommand(), 1));
            }
            case KNIGHT -> {
                moveGeneratorCommandList.add(new KnightMoveGeneratorCommand());
            }
            case PAWN -> {
                moveGeneratorCommandList.add(new PawnMoveGeneratorCommand(color));
            }
            case QUEEN -> {
                moveGeneratorCommandList.add(new HorizontalMoveGeneratorCommand());
                moveGeneratorCommandList.add(new VerticalMoveGeneratorCommand());
                moveGeneratorCommandList.add(new DiagonalMoveGeneratorCommand());
            }
            case ROOK -> {
                moveGeneratorCommandList.add(new VerticalMoveGeneratorCommand());
                moveGeneratorCommandList.add(new HorizontalMoveGeneratorCommand());
            }
        }
        return new Piece(initialPosition, color, pieceType, moveGeneratorCommandList);
    }
}
