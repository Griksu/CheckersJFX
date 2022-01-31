package com.kodilla.checkersjfx;

public enum PawnColor {

    RED(1), WHITE(-1);

    final int moveDir;

    PawnColor(int moveDir) {
        this.moveDir = moveDir;
    }
}
