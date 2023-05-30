package MoveGeneratorCommands;

import edu.uj.po.interfaces.Move;
import edu.uj.po.interfaces.Position;

import java.util.List;

import static java.lang.Math.abs;

public class RangeMoveGeneratorCommandDecorator implements MoveGeneratorCommand{
    private MoveGeneratorCommand moveGeneratorCommand;
    private int range;

    public RangeMoveGeneratorCommandDecorator(MoveGeneratorCommand moveGeneratorCommand, int range){
        this.moveGeneratorCommand = moveGeneratorCommand;
        this.range = range;
    }

    @Override
    public List<Move> GenerateMovesForPosition(Position position) {
        List<Move> generatedMoves = moveGeneratorCommand.GenerateMovesForPosition(position);
        return generatedMoves.stream().filter(move ->
            abs(move.finalPosition().rank().ordinal() - move.initialPosition().rank().ordinal()) <= range &&
            abs(move.finalPosition().file().ordinal() - move.initialPosition().file().ordinal()) <= range
        ).toList();
    }
}
