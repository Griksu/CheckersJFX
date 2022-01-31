package com.kodilla.checkersjfx;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static com.kodilla.checkersjfx.CheckersMain.SQUARE_SIZE;

public class Pawn extends StackPane {

    private PawnColor color;

    private double mouseX, mouseY;
    private double oldX, oldY;

    public PawnColor getColor() {
        return color;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Pawn(PawnColor color, int x, int y) {
        this.color = color;

        move(x, y);

        Ellipse bg = new Ellipse(SQUARE_SIZE * 0.3125, SQUARE_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(SQUARE_SIZE * 0.03);

        bg.setTranslateX((SQUARE_SIZE - SQUARE_SIZE * 0.3125 * 2 ) / 2);
        bg.setTranslateY((SQUARE_SIZE - SQUARE_SIZE * 0.26 * 2 ) / 2 + SQUARE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(SQUARE_SIZE * 0.3125, SQUARE_SIZE * 0.26);
        ellipse.setFill(color == PawnColor.RED
                ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(SQUARE_SIZE * 0.03);

        ellipse.setTranslateX((SQUARE_SIZE - SQUARE_SIZE * 0.3125 * 2 ) / 2);
        ellipse.setTranslateY((SQUARE_SIZE - SQUARE_SIZE * 0.26 * 2 ) / 2);

        getChildren().addAll(bg, ellipse);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    public void move(int x, int y) {
        oldX = x * SQUARE_SIZE;
        oldY = y * SQUARE_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
