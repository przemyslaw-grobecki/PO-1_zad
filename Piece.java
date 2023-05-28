package edu.uj.po.interfaces;

import java.util.ArrayList;
import java.util.List;

public class Piece implements Cloneable{
    private List<MoveGeneratorCommand> moveGeneratorCommands;
    private Position position;
    public ChessPiece pieceType;
    public Color color;

    public Piece(Position initialPosition, Color color, ChessPiece pieceType, List<MoveGeneratorCommand> moveGenerators){
        this.position = initialPosition;
        this.pieceType = pieceType;
        this.color = color;
        this.moveGeneratorCommands = moveGenerators;
    }

    public List<Move> ListAllPossibleMoves(){
        ArrayList<Move> moveList = new ArrayList<>();
        moveGeneratorCommands.forEach(command -> moveList.addAll(command.GenerateMovesForPosition(position)));
        return moveList;
    }
    public void setPosition(Position position){
        this.position = position;
    }
    public Position getPosition(){
        return position;
    }

    @Override
    public Piece clone() {
        try {
            Piece clone = (Piece) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
