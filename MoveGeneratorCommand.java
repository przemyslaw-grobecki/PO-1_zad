package edu.uj.po.interfaces;

import java.util.List;

public interface MoveGeneratorCommand {
    public List<Move> GenerateMovesForPosition(Position position);
}
