package com.example.minesweeper;

import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MineSweeperImplTest {

    @DataProvider
    public Object[][] correctMineFields() {
        return new Object[][] { {"*..*\\n....\\n*.**", "*11*\\n2333\\n*2**"}, {"****", "****"}, {".\\n.\\n.", "0\\n0\\n0"},
                {"*", "*"}, {".", "0"} };
    }

    @Test(dataProvider = "correctMineFields")
    public void verifyHandlingOfCorrectMineFields(String mineField, String expectedHintField) {
        MineSweeper mineSweeper = new MineSweeperImpl();
        mineSweeper.setMineField(mineField);
        Assertions.assertThat(mineSweeper.getHintField()).isEqualTo(expectedHintField);
    }

    @DataProvider
    public Object[][] incorrectMineFields() {
        return new Object[][] { {"*..*\\n....\\n1234"}, {"*..*\\n..\\n****"}, {"....\\n"}, {""} };
    }

    @Test(dataProvider = "incorrectMineFields", expectedExceptions = IllegalArgumentException.class)
    public void verifyHandlingOfIncorrectMineFields(String mineField) {
        MineSweeper mineSweeper = new MineSweeperImpl();
        mineSweeper.setMineField(mineField);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void verifyExceptionThrownWhenFieldNotInitialised() {
        MineSweeper mineSweeper = new MineSweeperImpl();
        mineSweeper.getHintField();
    }

}
