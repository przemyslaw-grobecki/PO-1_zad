package edu.uj.po.interfaces.MoveGeneratorCommands;

import edu.uj.po.interfaces.File;
import edu.uj.po.interfaces.Move;
import edu.uj.po.interfaces.Position;
import edu.uj.po.interfaces.Rank;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class DiagonalMoveGeneratorCommand implements MoveGeneratorCommand {
    @Override
    public List<Move> GenerateMovesForPosition(Position position) {
        List<Move> moveList = new ArrayList<>();
        //Right && Up
        for (int file = position.file().ordinal() + 1; file <= File.h.ordinal(); file++) {
            for (int rank = position.rank().ordinal() + 1; rank <= Rank.EIGHTH.ordinal() ; rank++) {
                if(file - position.file().ordinal() == rank - position.rank().ordinal()){
                    moveList.add(new Move(position,new Position(File.values()[file], Rank.values()[rank])));
                }
            }
        }
        //Right && Down
        for (int file = position.file().ordinal() + 1; file <= File.h.ordinal(); file++) {
            for (int rank = position.rank().ordinal() - 1; rank >= Rank.FIRST.ordinal() ; rank--) {
                if(file - position.file().ordinal() == abs(rank - position.rank().ordinal())){
                    moveList.add(new Move(position,new Position(File.values()[file], Rank.values()[rank])));
                }
            }
        }
        //Left && Up
        for (int file = position.file().ordinal() - 1; file >= File.a.ordinal(); file--) {
            for (int rank = position.rank().ordinal() + 1; rank <= Rank.EIGHTH.ordinal() ; rank++) {
                if(abs(file - position.file().ordinal()) == rank - position.rank().ordinal()){
                    moveList.add(new Move(position,new Position(File.values()[file], Rank.values()[rank])));
                }
            }
        }
        //Left && Down
        for (int file = position.file().ordinal() - 1; file >= File.a.ordinal(); file--) {
            for (int rank = position.rank().ordinal() - 1; rank >= Rank.FIRST.ordinal() ; rank--) {
                if(abs(file - position.file().ordinal()) == abs(rank - position.rank().ordinal())){
                    moveList.add(new Move(position,new Position(File.values()[file], Rank.values()[rank])));
                }
            }
        }
        return moveList;
    }
}
