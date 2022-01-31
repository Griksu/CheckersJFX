package com.kodilla.checkersjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.kodilla.checkersjfx.CheckersMain.*;

public class BoardSquare extends Rectangle {

    private Pawn pawn;

    public boolean hasPawn() {
        return pawn != null;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public BoardSquare(boolean light, int x, int y) {
        setWidth(SQUARE_SIZE);
        setHeight(SQUARE_SIZE);

        relocate(x * SQUARE_SIZE, y * SQUARE_SIZE);

        setFill(light ? Color.rgb(187,187,179) : Color.rgb(26, 26, 179));
    }
}
