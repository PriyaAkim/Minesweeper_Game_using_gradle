package com.learntodroid.androidminesweeper.services;

import com.learntodroid.androidminesweeper.entity.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* class representing the List of Mines in Grid of cells */

public class MineGrid {
    private List<Cell> cells;
    private int size;

/* constructor for Grid of cells which has initial value BLANK */

    public MineGrid(int size) {
        this.size = size;
        this.cells = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            cells.add(new Cell(Cell.BLANK));
        }
    }

//   method to place number of bombs in a grid
    public void generateGrid(int totalBombs) {
        int bombsPlaced = 0;
        while (bombsPlaced < totalBombs) {
            int x = new Random().nextInt(size);
            int y = new Random().nextInt(size);

            if (cellAt(x, y).getValue() == Cell.BLANK) {
                cells.set(x + (y*size), new Cell(Cell.BOMB));
                bombsPlaced++;
            }
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (cellAt(x, y).getValue() != Cell.BOMB) {
                    List<Cell> adjacentCells = adjacentCells(x, y);
                    int countBombs = 0;
                    for (Cell cell: adjacentCells) {
                        if (cell.getValue() == Cell.BOMB) {
                            countBombs++;
                        }
                    }
                    if (countBombs > 0) {
                        cells.set(x + (y*size), new Cell(countBombs));
                    }
                }
            }
        }
    }

//  method retrieve the cells
    public Cell cellAt(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return null;
        }
        return cells.get(x + (y*size));
    }

    public List<Cell> adjacentCells(int x, int y) {
        List<Cell> adjacentCells = new ArrayList<>();

        List<Cell> cellsList = new ArrayList<>();
        cellsList.add(cellAt(x-1, y));
        cellsList.add(cellAt(x+1, y));
        cellsList.add(cellAt(x-1, y-1));
        cellsList.add(cellAt(x, y-1));
        cellsList.add(cellAt(x+1, y-1));
        cellsList.add(cellAt(x-1, y+1));
        cellsList.add(cellAt(x, y+1));
        cellsList.add(cellAt(x+1, y+1));

        for (Cell cell: cellsList) {
            if (cell != null) {
                adjacentCells.add(cell);
            }
        }

        return adjacentCells;
    }

//    for converting x & y cordinates into Index
    public int toIndex(int x, int y) {
        return x + (y*size);
    }

//  converting Index to x & y cordinates
    public int[] toXY(int index) {
        int y = index / size;
        int x = index - (y*size);
        return new int[]{x, y};
    }

//   reveal the bomb
    public void revealAllBombs() {
        for (Cell c: cells) {
            if (c.getValue() == Cell.BOMB) {
                c.setRevealed(true);
            }
        }
    }

    public List<Cell> getCells() {
        return cells;
    }
}
