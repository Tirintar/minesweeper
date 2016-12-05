package com.example.minesweeper;

public class MineSweeperImpl implements MineSweeper{

    private static final String NO_DATA = "No data has been passed.";
    private static final String ROWS_HAVE_DIFFERENT_LENGTH = "Rows have different length.";
    private static final String ILLEGAL_CHARACTER_FOUND = "Illegal character found, only '*', '.' and '\\n' are supported.";
    private static final String MINEFIELD_NOT_INITIALISED = "Minefield has not been initialised";
    private static final String NEW_LINE_AT_END = "Minefield ends with an empty row";

    private char[][] mineField;

    public void setMineField(String mineField) throws IllegalArgumentException {
        if (null == mineField || mineField.equals("")) {
            throw new IllegalArgumentException(NO_DATA);
        }
        if (mineField.endsWith("\\n")) {
            throw new IllegalArgumentException(NEW_LINE_AT_END);
        }
        String[] fieldRows = mineField.split("\\\\n");
        int rowLength = 0;
        for (String fieldRow : fieldRows) {
            if (rowLength == 0) {
                rowLength = fieldRow.length();
            } else {
                if (rowLength != fieldRow.length()) {
                    throw new IllegalArgumentException(ROWS_HAVE_DIFFERENT_LENGTH);
                }
            }
        }
        this.mineField = new char[fieldRows.length][rowLength];
        for (int i = 0; i < fieldRows.length; i++) {
            for (int j = 0; j < rowLength; j++) {
                char field = fieldRows[i].charAt(j);
                if (field == '*') {
                    this.mineField[i][j] = '*';
                } else if (field == '.') {
                    this.mineField[i][j] = '.';
                } else {
                    throw new IllegalArgumentException(ILLEGAL_CHARACTER_FOUND);
                }
            }
        }
    }

    public String getHintField() throws IllegalStateException {
        if (mineField == null) {
            throw new IllegalStateException(MINEFIELD_NOT_INITIALISED);
        }
        String hintField = "";
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                if (mineField[i][j] == '.') {
                    int mineCount = 0;
                    if (i != 0) {
                        mineCount += countAdjacentMines(mineField[i - 1], j);
                    }
                    mineCount += countAdjacentMines(mineField[i], j);
                    if (i != mineField.length-1) {
                        mineCount += countAdjacentMines(mineField[i + 1], j);
                    }
                    hintField += String.valueOf(mineCount);
                } else {
                    hintField += "*";
                }
            }
            if(i != mineField.length - 1) {
                hintField += ("\\n");
            }
        }
        return hintField;
    }

    private int countAdjacentMines(char[] row, int fieldIndex) {
        int mineCount = 0;
        if (fieldIndex != 0) {
            if (row[fieldIndex - 1] == '*') {
                mineCount++;
            }
        }
        if (row[fieldIndex] == '*') {
            mineCount++;
        }
        if (fieldIndex < row.length-1) {
            if (row[fieldIndex + 1] == '*') {
                mineCount++;
            }
        }
        return mineCount;
    }
}
