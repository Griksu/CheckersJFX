package com.kodilla.checkersjfx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckersMain extends Application{

    public static final int SQUARE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private final BoardSquare[][] board = new BoardSquare[WIDTH][HEIGHT];

    private final Group squareGroup = new Group();
    private final Group pawnGroup = new Group();
    private PawnColor whoIsMoving = PawnColor.WHITE;


    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * SQUARE_SIZE, HEIGHT * SQUARE_SIZE);
        root.getChildren().addAll(squareGroup, pawnGroup);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                BoardSquare square = new BoardSquare((x + y) % 2 == 0, x, y);
                board[x][y] = square;

                squareGroup.getChildren().add(square);

                Pawn pawn = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    pawn = makePawn(PawnColor.RED, x, y);
                }

                if(y >= 5 && (x + y) % 2 != 0) {
                    pawn = makePawn(PawnColor.WHITE, x, y);
                }

                if (pawn != null) {
                    square.setPawn(pawn);
                    pawnGroup.getChildren().add(pawn);
                }
            }
        }
        return root;
    }

    private MoveResult tryMove(Pawn pawn, int newX, int newY) {

        if (board[newX][newY].hasPawn() || (newX + newY) % 2 == 0) {
            return new MoveResult(MoveType.NONE);
        }
        if (pawn.getColor() != whoIsMoving) {
            return new MoveResult(MoveType.NONE);
        }

        int x0 = toBoard(pawn.getOldX());
        int y0 = toBoard(pawn.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == pawn.getColor().moveDir) {
            switchPlayer();
            return new MoveResult(MoveType.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == pawn.getColor().moveDir * 2) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPawn() && board[x1][y1].getPawn().getColor() != pawn.getColor()) {
                switchPlayer();
                return new MoveResult(MoveType.CAPTURE, board[x1][y1].getPawn());
            }
        }
        return new MoveResult(MoveType.NONE);
    }

    private void switchPlayer() {
        whoIsMoving = (whoIsMoving == PawnColor.WHITE) ? PawnColor.RED : PawnColor.WHITE;
    }

    private void checkWinner() {

        int redPawnsLeft = 0;
        int whitePawnsLeft = 0;

        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {

                if (board[x][y].hasPawn() && board[x][y].getPawn().getColor() == PawnColor.WHITE) {
                    whitePawnsLeft++;
                } else if (board[x][y].hasPawn() && board[x][y].getPawn().getColor() == PawnColor.RED) {
                    redPawnsLeft++;
                }
            }
        }

        if (redPawnsLeft != 0) {
            System.out.println("Red has still pawn(s): " + redPawnsLeft);
        }

        if (whitePawnsLeft != 0) {
            System.out.println("White has still pawn(s): " + whitePawnsLeft);
        }

        if (redPawnsLeft == 0) {
            if (whoIsMoving == PawnColor.RED) {
                System.out.println("White is winner!");
            }
        }

        if (whitePawnsLeft == 0) {
            if (whoIsMoving == PawnColor.WHITE) {
                System.out.println("Red is winner!");
            }
        }
    }

    private int toBoard(double pixel) {
        return (int) (pixel + SQUARE_SIZE / 2) / SQUARE_SIZE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Pawn makePawn(PawnColor color, int x, int y) {
        Pawn pawn = new Pawn(color, x, y);

        pawn.setOnMouseReleased(e -> {
            int newX = toBoard(pawn.getLayoutX());
            int newY = toBoard(pawn.getLayoutY());

            MoveResult result = tryMove(pawn, newX, newY);

            int x0 = toBoard(pawn.getOldX());
            int y0 = toBoard(pawn.getOldY());

            switch (result.getType()) {
                case NONE:
                    pawn.abortMove();
                    break;
                case NORMAL:
                    pawn.move(newX, newY);
                    board[x0][y0].setPawn(null);
                    board[newX][newY].setPawn(pawn);
                    break;
                case CAPTURE:
                    pawn.move(newX, newY);
                    board[x0][y0].setPawn(null);
                    board[newX][newY].setPawn(pawn);

                    Pawn otherPawn = result.getPawn();
                    board[toBoard(otherPawn.getOldX())][toBoard(otherPawn.getOldY())].setPawn(null);
                    pawnGroup.getChildren().remove(otherPawn);
                    break;
            }
            checkWinner();
        });
        return pawn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}