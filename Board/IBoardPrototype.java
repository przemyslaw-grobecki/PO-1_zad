package Board;

import Pieces.Piece;
import edu.uj.po.interfaces.*;

import java.util.List;
import java.util.Optional;

public interface IBoardPrototype {
    public List<Piece> GetPieces();
    public List<Piece> GetPiecesBetween(Position source, Position destination);
    public List<Piece> GetAlliedTeam(Color color);
    public List<Piece> GetOpposingTeam(Color color);
    public List<Piece> GetFigures(Color color, ChessPiece pieceType);
    public Optional<Piece> GetPiece(Position position);
    public Optional<Piece> GetPiece(int file, int rank);
    public Optional<Piece> GetPiece(File file, Rank rank);
    public IBoardPrototype Copy();
    public boolean MovePiece(Move move);
    public boolean CheckForMate(Color color);
    public boolean CheckForStalemate(Color color);
}
