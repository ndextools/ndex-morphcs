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
        List<Table.Bin> listOfBins;
        listOfBins = table.getUnorderedListOfBins();
        Assert.assertEquals(heading1, listOfBins.get(0).getCellColumnHeading());
        Assert.assertEquals(data1, listOfBins.get(0).getCellData());
        Assert.assertEquals(heading2, listOfBins.get(1).getCellColumnHeading());
        Assert.assertEquals(data2, listOfBins.get(1).getCellData());
    }

    @Test
    public void _ShouldInstantiateCell() {
        String heading = "Column-1";
        String data = "Data-1";
        Table table = new Table(null, null, null);
        Table.Bin bin = table.new Bin(heading, data);
        Assert.assertEquals(heading, bin.getCellColumnHeading());
        Assert.assertEquals(data, bin.getCellData());
    }
}