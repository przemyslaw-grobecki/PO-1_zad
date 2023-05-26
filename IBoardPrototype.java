package edu.uj.po.interfaces;

import java.util.List;

public interface IBoardPrototype {
    public List<Piece> GetPieces();
    public IBoardPrototype Copy();
    public boolean MovePiece(Move move);
    public boolean CheckForMate(Color color);
    public boolean CheckForStalemate(Color color);
}
