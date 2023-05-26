package edu.uj.po.interfaces;

import java.util.ArrayList;
import java.util.List;

public class Piece {
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
}
