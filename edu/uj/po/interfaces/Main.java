package edu.uj.po.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {


    public static void main(String[] args) {
        ChessSolver chessSolver = new ChessSolver();
        MateSetup_2(chessSolver);
        chessSolver.findMateInOneMove(Color.WHITE)
                .ifPresent(System.out::println);
    }

    private static void MateSetup_1(ChessSolver chessSolver){
        chessSolver.addChessPiece(new Position(File.h, Rank.FOURTH), Color.BLACK, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.f, Rank.SECOND), Color.BLACK, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.g, Rank.SECOND), Color.BLACK, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.h, Rank.SECOND), Color.WHITE, ChessPiece.KING);
    }

    private static void MateSetup_2(ChessSolver chessSolver){
        chessSolver.addChessPiece(new Position(File.a, Rank.EIGHTH), Color.BLACK, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.f, Rank.EIGHTH), Color.BLACK, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.h, Rank.EIGHTH), Color.BLACK, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.a, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.d, Rank.SEVENTH), Color.BLACK, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.f, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.c, Rank.SIXTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.f, Rank.SIXTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.SIXTH), Color.WHITE, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.c, Rank.FIFTH), Color.BLACK, ChessPiece.QUEEN);
        chessSolver.addChessPiece(new Position(File.d, Rank.FIFTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.e, Rank.FOURTH), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.FOURTH), Color.WHITE, ChessPiece.QUEEN);
        chessSolver.addChessPiece(new Position(File.g, Rank.THIRD), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.a, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.c, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.SECOND), Color.WHITE, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.h, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.a, Rank.FIRST), Color.WHITE, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.h, Rank.FIRST), Color.WHITE, ChessPiece.KING);
    }
}
