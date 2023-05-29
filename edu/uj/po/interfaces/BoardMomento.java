package edu.uj.po.interfaces;

import java.util.ArrayList;
import java.util.List;

public class BoardMomento {
    public final ArrayList<Piece> pieces;
    public BoardMomento(List<Piece> pieces){
        this.pieces = new ArrayList<>(pieces);
    }
}
