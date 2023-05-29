package edu.uj.po.interfaces.MoveGeneratorCommands;

import edu.uj.po.interfaces.Move;
import edu.uj.po.interfaces.Position;

import java.util.List;

public interface MoveGeneratorCommand {
    public List<Move> GenerateMovesForPosition(Position position);
}
