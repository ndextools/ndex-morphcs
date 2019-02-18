package org.ndextools.morphcx.tables;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for BaseCell class.
 */
public class BaseCellTest {
    private final String TEXT =
            "The only way to keep your health is to eat what you don’t want, " +
            "drink what you don’t like, and do what you’d rather not. Mark Twain";
    private final int COLUMN_IDX = 100;

    private BaseCell baseCell;

    @Test
    public void _Should_Return_Text() {
        baseCell = new BaseCell(TEXT, COLUMN_IDX);
        String results = baseCell.getText();
        Assert.assertEquals(TEXT, results);
    }

    @Test
    public void _Should_Return_ColumnIdx() {
        baseCell = new BaseCell(TEXT, COLUMN_IDX);
        int results = baseCell.getColumnIdx();
        Assert.assertEquals(COLUMN_IDX, results);
    }

    @Test
    public void _Should_Return_toString() {
        baseCell = new BaseCell(TEXT, COLUMN_IDX);
        String results = baseCell.toString();
        Assert.assertTrue(results.contains(TEXT));
        Assert.assertTrue(results.contains(String.valueOf(COLUMN_IDX)));
    }
}