package edu.uj.po.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {


    public static void main(String[] args) {
        class Position{
            public int x;
            public int y;
            public Position(int x, int y){
                this.x = x;
                this.y = y;
            }
        }

        List<Position> positions = new ArrayList<Position>();

        positions.add(new Position(1,2));
        positions.add(new Position(2,3));
        Optional<Position> pos = positions.stream().filter(posi -> posi.x == 1).findAny();

        pos.get().x = 10;

        positions.forEach(posi -> System.out.print(posi.x));
    }
}
