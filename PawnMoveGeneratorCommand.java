package edu.uj.po.interfaces;

import java.util.ArrayList;
import java.util.List;

public class PawnMoveGeneratorCommand implements MoveGeneratorCommand{
    private final Color color;

    public PawnMoveGeneratorCommand(Color color){
        this.color = color;
    }
    @Override
    public List<Move> GenerateMovesForPosition(Position position) {
        List<Move> moveList = new ArrayList<>();
        var startPositionFileInt = position.file().ordinal();
        var startPositionRankInt = position.rank().ordinal();

        if(color == Color.BLACK){
            if(startPositionRankInt - 1 >= 0) {
                moveList.add(new Move(position,
                    new Position(File.values()[startPositionFileInt], Rank.values()[startPositionRankInt - 1])));
                if (startPositionFileInt - 1 >= 0) {
                    moveList.add(new Move(position,
                        new Position(File.values()[startPositionFileInt - 1], Rank.values()[startPositionRankInt - 1])));
                }
                if (startPositionFileInt + 1 <= 7) {
                    moveList.add(new Move(position,
                        new Position(File.values()[startPositionFileInt + 1], Rank.values()[startPositionRankInt - 1])));
                }
            }
            if(position.rank() == Rank.SEVENTH) {
                moveList.add(new Move(position,
                    new Position(File.values()[startPositionFileInt], Rank.values()[startPositionRankInt - 2])));
            }
        }else{
            if(startPositionRankInt + 1 <= 7) {
                moveList.add(new Move(position,
                    new Position(File.values()[startPositionFileInt], Rank.values()[startPositionRankInt + 1])));
                if (startPositionFileInt - 1 >= 0) {
                    moveList.add(new Move(position,
                        new Position(File.values()[startPositionFileInt - 1], Rank.values()[startPositionRankInt + 1])));
                }
                if (startPositionFileInt + 1 <= 7) {
                    moveList.add(new Move(position,
                        new Position(File.values()[startPositionFileInt + 1], Rank.values()[startPositionRankInt + 1])));
                }
            }
            if(position.rank() == Rank.SECOND) {
                moveList.add(new Move(position,
                    new Position(File.values()[startPositionFileInt], Rank.values()[startPositionRankInt +2])));
            }
        }
        return moveList;
    }
}
