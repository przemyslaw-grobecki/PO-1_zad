import edu.uj.po.interfaces.*;

public class Main {


    public static void main(String[] args) {
        ChessSolver chessSolver = new ChessSolver();
        var color = MateSetup_EnPassantHard(chessSolver);
        chessSolver.findMateInOneMove(color)
                .ifPresent(System.out::println);
    }

    //H4-F3
    private static Color MateSetup_1(ChessSolver chessSolver){
        chessSolver.addChessPiece(new Position(File.h, Rank.FOURTH), Color.BLACK, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.f, Rank.SECOND), Color.BLACK, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.g, Rank.SECOND), Color.BLACK, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.h, Rank.SECOND), Color.WHITE, ChessPiece.KING);
        return Color.BLACK;
    }

    //G4-G7
    private static Color MateSetup_2(ChessSolver chessSolver){
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
        return Color.WHITE;
    }

    //F7-F1
    private static Color MateSetup_3(ChessSolver chessSolver){
        chessSolver.addChessPiece(new Position(File.h, Rank.EIGHTH), Color.WHITE, ChessPiece.QUEEN);
        chessSolver.addChessPiece(new Position(File.a, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.c, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.f, Rank.SEVENTH), Color.BLACK, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.d, Rank.SIXTH), Color.BLACK, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.g, Rank.SIXTH), Color.BLACK, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.h, Rank.FIFTH), Color.WHITE, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.c, Rank.FOURTH), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.a, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.FIRST), Color.WHITE, ChessPiece.KING);
        return Color.BLACK;
    }

    //F3-F7
    private static Color MateSetup_4(ChessSolver chessSolver){
        chessSolver.addChessPiece(new Position(File.a, Rank.EIGHTH), Color.BLACK, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.c, Rank.EIGHTH), Color.BLACK, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.d, Rank.EIGHTH), Color.BLACK, ChessPiece.QUEEN);
        chessSolver.addChessPiece(new Position(File.e, Rank.EIGHTH), Color.BLACK, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.g, Rank.EIGHTH), Color.BLACK, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.h, Rank.EIGHTH), Color.BLACK, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.a, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.c, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.d, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.f, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.c, Rank.SIXTH), Color.BLACK, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.c, Rank.FIFTH), Color.BLACK, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.e, Rank.FIFTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.c, Rank.FOURTH), Color.WHITE, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.e, Rank.FOURTH), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.f, Rank.THIRD), Color.WHITE, ChessPiece.QUEEN);
        chessSolver.addChessPiece(new Position(File.a, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.c, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.d, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.f, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.a, Rank.FIRST), Color.WHITE, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.b, Rank.FIRST), Color.WHITE, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.c, Rank.FIRST), Color.WHITE, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.e, Rank.FIRST), Color.WHITE, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.g, Rank.FIRST), Color.WHITE, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.h, Rank.FIRST), Color.WHITE, ChessPiece.ROOK);
        return Color.WHITE;
    }

    //E8-E1
    private static Color MateSetup_5(ChessSolver chessSolver){
        chessSolver.addChessPiece(new Position(File.b, Rank.EIGHTH), Color.BLACK, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.e, Rank.EIGHTH), Color.BLACK, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.f, Rank.EIGHTH), Color.BLACK, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.g, Rank.EIGHTH), Color.BLACK, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.h, Rank.EIGHTH), Color.BLACK, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.a, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.d, Rank.SEVENTH), Color.WHITE, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.g, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.d, Rank.SIXTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.d, Rank.FIFTH), Color.WHITE, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.d, Rank.THIRD), Color.WHITE, ChessPiece.QUEEN);
        chessSolver.addChessPiece(new Position(File.f, Rank.THIRD), Color.BLACK, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.a, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.f, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.a, Rank.FIRST), Color.WHITE, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.c, Rank.FIRST), Color.WHITE, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.f, Rank.FIRST), Color.WHITE, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.h, Rank.FIRST), Color.WHITE, ChessPiece.ROOK);
        return Color.BLACK;
    }

    //A8-G8
    private static Color MateSetup_6(ChessSolver chessSolver){
        chessSolver.addChessPiece(new Position(File.a, Rank.EIGHTH), Color.WHITE, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.f, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.SEVENTH), Color.BLACK, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.f, Rank.SIXTH), Color.WHITE, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.g, Rank.SIXTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.SIXTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.e, Rank.FIFTH), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.f, Rank.FIFTH), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.FOURTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.d, Rank.THIRD), Color.BLACK, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.g, Rank.THIRD), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.c, Rank.SECOND), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.SECOND), Color.WHITE, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.h, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        return Color.WHITE;
    }

    //A6-F1
    private static Color MateSetup_7(ChessSolver chessSolver){
        chessSolver.addChessPiece(new Position(File.h, Rank.SEVENTH), Color.BLACK, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.a, Rank.SIXTH), Color.BLACK, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.d, Rank.SIXTH), Color.WHITE, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.a, Rank.FIFTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.e, Rank.FIFTH), Color.WHITE, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.f, Rank.FIFTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.FIFTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.FOURTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.c, Rank.THIRD), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.f, Rank.THIRD), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.THIRD), Color.WHITE, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.b, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        return Color.BLACK;
    }

    //D5-E6
    private static Color MateSetup_EnPassantHard(ChessSolver chessSolver){
        chessSolver.addChessPiece(new Position(File.c, Rank.EIGHTH), Color.BLACK, ChessPiece.QUEEN);
        chessSolver.addChessPiece(new Position(File.e, Rank.EIGHTH), Color.BLACK, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.f, Rank.EIGHTH), Color.BLACK, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.a, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.SEVENTH), Color.WHITE, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.f, Rank.SEVENTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.SEVENTH), Color.BLACK, ChessPiece.KING);
        chessSolver.addChessPiece(new Position(File.h, Rank.SEVENTH), Color.BLACK, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.d, Rank.SIXTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.SIXTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.h, Rank.SIXTH), Color.WHITE, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.c, Rank.FIFTH), Color.BLACK, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.d, Rank.FIFTH), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.e, Rank.FIFTH), Color.BLACK, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.FIFTH), Color.WHITE, ChessPiece.QUEEN);
        chessSolver.addChessPiece(new Position(File.h, Rank.FIFTH), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.FOURTH), Color.BLACK, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.c, Rank.FOURTH), Color.WHITE, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.e, Rank.FOURTH), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.FOURTH), Color.BLACK, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.g, Rank.THIRD), Color.WHITE, ChessPiece.KNIGHT);
        chessSolver.addChessPiece(new Position(File.a, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.b, Rank.SECOND), Color.WHITE, ChessPiece.BISHOP);
        chessSolver.addChessPiece(new Position(File.c, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.g, Rank.SECOND), Color.WHITE, ChessPiece.PAWN);
        chessSolver.addChessPiece(new Position(File.f, Rank.FIRST), Color.WHITE, ChessPiece.ROOK);
        chessSolver.addChessPiece(new Position(File.g, Rank.FIRST), Color.WHITE, ChessPiece.KING);
        return Color.WHITE;
    }


}
