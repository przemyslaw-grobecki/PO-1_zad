package Board;

import Pieces.Piece;
import edu.uj.po.interfaces.Color;
import edu.uj.po.interfaces.Move;

import java.util.List;

public interface IBoardPrototype {
    public List<Piece> GetPieces();
    public IBoardPrototype Copy();
    public boolean MovePiece(Move move);
    public boolean CheckForMate(Color color);
    public boolean CheckForStalemate(Color color);
}
