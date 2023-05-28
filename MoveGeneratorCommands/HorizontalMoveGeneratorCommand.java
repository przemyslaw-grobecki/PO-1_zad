package edu.uj.po.interfaces.MoveGeneratorCommands;

import edu.uj.po.interfaces.File;
import edu.uj.po.interfaces.Move;
import edu.uj.po.interfaces.Position;

import java.util.ArrayList;
import java.util.List;

public class HorizontalMoveGeneratorCommand implements MoveGeneratorCommand{
    @Override
    public List<Move> GenerateMovesForPosition(Position position) {
        List<Move> moveList = new ArrayList<>();
        //Right
        for (int file = position.file().ordinal() + 1; file <= File.h.ordinal(); file++) {
            moveList.add(new Move(position,new Position(File.values()[file], position.rank())));
        }
        //Left
        for (int file = position.file().ordinal() -1; file >= File.a.ordinal(); file--) {
            moveList.add(new Move(position,new Position(File.values()[file], position.rank())));
        }
        return moveList;
    }
}
