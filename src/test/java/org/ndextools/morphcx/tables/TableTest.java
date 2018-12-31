package org.ndextools.morphcx.tables;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TableTest {

    @Test
    public void _ShouldAddCellToUnorderedRow() {
        String heading1 = "Column-1";
        String data1 = "Data-1";
        String heading2 = "Column-2";
        String data2 = "Data-2";
        Table table = new Table(null, null, null);
        table.addCellToRow(heading1, data1);
        table.addCellToRow(heading2, data2);
        List<Table.Cell> listOfCells;
        listOfCells = table.getUnorderedListOfCells();
        Assert.assertEquals(heading1, listOfCells.get(0).getCellColumnHeading());
        Assert.assertEquals(data1, listOfCells.get(0).getCellData());
        Assert.assertEquals(heading2, listOfCells.get(1).getCellColumnHeading());
        Assert.assertEquals(data2, listOfCells.get(1).getCellData());
    }

    @Test
    public void _ShouldInstantiateCell() {
        String heading = "Column-1";
        String data = "Data-1";
        Table table = new Table(null, null, null);
        Table.Cell cell = table.new Cell(heading, data);
        Assert.assertEquals(heading, cell.getCellColumnHeading());
        Assert.assertEquals(data, cell.getCellData());
    }
}