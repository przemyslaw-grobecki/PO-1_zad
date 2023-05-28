package edu.uj.po.interfaces;

import java.util.ArrayList;
import java.util.List;

public class VerticalMoveGeneratorCommand implements MoveGeneratorCommand{
    @Override
    public List<Move> GenerateMovesForPosition(Position position) {
        List<Move> moveList = new ArrayList<>();
        //Right
        for (int rank = position.rank().ordinal() + 1; rank <= Rank.EIGHTH.ordinal(); rank++) {
            moveList.add(new Move(position,new Position(position.file(), Rank.values()[rank])));
        }
        //Left
        for (int rank = position.rank().ordinal() - 1; rank >= Rank.FIRST.ordinal(); rank--) {
            moveList.add(new Move(position,new Position(position.file(), Rank.values()[rank])));
        }
        return moveList;
    }
}
