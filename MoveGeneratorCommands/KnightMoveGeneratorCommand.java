package MoveGeneratorCommands;

import edu.uj.po.interfaces.File;
import edu.uj.po.interfaces.Move;
import edu.uj.po.interfaces.Position;
import edu.uj.po.interfaces.Rank;

import java.util.ArrayList;
import java.util.List;

public class KnightMoveGeneratorCommand implements MoveGeneratorCommand{
    @Override
    public List<Move> GenerateMovesForPosition(Position position) {
        List<Move> moveList = new ArrayList<>();
        var startPositionFileInt = position.file().ordinal();
        var startPositionRankInt = position.rank().ordinal();

        int[] knightFileSmallMoves = new int[]{1, -1};
        int[] knightRankSmallMoves = new int[]{1, -1};
        int[] knightFileBigMoves = new int[]{2,-2};
        int[] knightRankBigMoves = new int[]{2,-2};

        for (int moveSmall: knightFileSmallMoves) {
            for (int moveBig: knightRankBigMoves) {
                if (startPositionFileInt + moveSmall <= 7 && startPositionFileInt + moveSmall >= 0 &&
                startPositionRankInt + moveBig <= 7 && startPositionRankInt + moveBig >= 0) {
                    moveList.add(new Move(position, new Position(
                            File.values()[startPositionFileInt+moveSmall],
                            Rank.values()[startPositionRankInt + moveBig]
                    )));
                }
            }
        }
        for (int moveSmall: knightRankSmallMoves) {
            for (int moveBig: knightFileBigMoves) {
                if (startPositionRankInt + moveSmall <= 7 && startPositionRankInt + moveSmall >= 0 &&
                        startPositionFileInt + moveBig <= 7 && startPositionFileInt + moveBig >= 0) {
                    moveList.add(new Move(position, new Position(
                            File.values()[startPositionFileInt+moveBig],
                            Rank.values()[startPositionRankInt + moveSmall]
                    )));
                }
            }
        }
        return moveList;
    }
}
