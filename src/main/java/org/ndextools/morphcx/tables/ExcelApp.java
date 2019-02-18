package org.ndextools.morphcx.tables;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ndexbio.cxio.aspects.datamodels.*;
import org.ndexbio.model.cx.NiceCXNetwork;
import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.writers.TableToPOI;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class ExcelApp implements Table3D {
    private final Configuration cfg;
    private final NiceCXNetwork niceCX;
    private final TableToPOI writer;
    private final OutputStream outStream;
    private final XSSFWorkbook wb;

    private List<SpreadsheetInfo> spreadsheets;

    public ExcelApp(Configuration cfg,
                    NiceCXNetwork niceCX,
                    TableToPOI writer,
                    XSSFWorkbook wb,
                    OutputStream outStream) {
        this.cfg = cfg;
        this.niceCX = niceCX;
        this.writer = writer;
        this.outStream = outStream;
        this.wb = wb;
    }

    public void morphThisCX() throws IOException {

        List<SpreadsheetInfo> spreadsheets = initializeSpreadsheets(niceCX, wb);

        processSpreadsheets(niceCX, wb, spreadsheets);
        writer.writeAll();

    }

    /**
     * Create spreadsheets and populate the first row with column headings.
     *
     * @param niceCX reference to NiceCXNetwork object being morphed
     * @param wb reference to Excel workbook object
     * @return List of spreadsheets and associated information belonging to Excel Excel workbook object
     */
    public List<SpreadsheetInfo> initializeSpreadsheets(final NiceCXNetwork niceCX, final XSSFWorkbook wb) {
        List<SpreadsheetInfo> spreadsheets = new ArrayList();

        // Create Network Table spreadsheet
        List<String> columnHeadings = gatherNetworkTableColumnHeadings(niceCX);
        String sheetName = ExcelAppConstants.SHEET_NETWORK_TABLE;
        Sheet sheet = wb.createSheet(sheetName);
        int nextRowIdx = addColumnHeadings(sheet, columnHeadings);

        SpreadsheetInfo spreadsheetInfo = new SpreadsheetInfo(
                wb,
                sheetName,
                sheet,
                columnHeadings,
                nextRowIdx);
        spreadsheets.add(spreadsheetInfo);

        // Create Node Table spreadsheet
        columnHeadings = gatherNodeTableColumnHeadings(niceCX);
        sheetName = ExcelAppConstants.SHEET_NODE_TABLE;
        sheet = wb.createSheet(sheetName);
        nextRowIdx = addColumnHeadings(sheet, columnHeadings);

        spreadsheetInfo = new SpreadsheetInfo(
                wb,
                sheetName,
                sheet,
                columnHeadings,
                nextRowIdx);
        spreadsheets.add(spreadsheetInfo);

        // Create Edge Table spreadsheet
        columnHeadings = gatherEdgeTableColumnHeadings(niceCX);
        sheetName = ExcelAppConstants.SHEET_EDGE_TABLE;
        sheet = wb.createSheet(sheetName);
        nextRowIdx = addColumnHeadings(sheet, columnHeadings);

        spreadsheetInfo = new SpreadsheetInfo(
                wb,
                sheetName,
                sheet,
                columnHeadings,
                nextRowIdx);
        spreadsheets.add(spreadsheetInfo);

        return spreadsheets;
    }

    /**
     * Iterate through each spreadsheet in an Excel workbook, and populate spreadsheet cells.
     *
     * @param niceCX reference to NiceCXNetwork object being morphed
     * @param wb reference to Excel workbook object
     * @param spreadsheetInfos List of spreadsheets and associated information belonging to Excel Excel workbook object
     */
    public void processSpreadsheets(final NiceCXNetwork niceCX, final XSSFWorkbook wb, final List<SpreadsheetInfo> spreadsheetInfos) {

        for (SpreadsheetInfo spreadsheetInfo : spreadsheetInfos) {
            switch (spreadsheetInfo.getSheetName()) {
                case ExcelAppConstants.SHEET_NETWORK_TABLE:
                    populateNetworkTableSheet(niceCX, wb, spreadsheetInfo);
                    break;
                case ExcelAppConstants.SHEET_NODE_TABLE:
                    populateNodeTableSheet(niceCX, wb, spreadsheetInfo);
                    break;
                case ExcelAppConstants.SHEET_EDGE_TABLE:
                    populateEdgeTableSheet(niceCX, wb, spreadsheetInfo);
                    break;
            }
        }
    }

    static void populateNetworkTableSheet(final NiceCXNetwork niceCX, final XSSFWorkbook wb, final SpreadsheetInfo sheetInfo) {

        List<NetworkAttributesElement> networkAttributesElements = niceCX.getNetworkAttributes().stream()
                .collect(Collectors.toList());

        if (networkAttributesElements.isEmpty()) return;

        Sheet sheet = sheetInfo.getSheet();
        Row row = sheet.createRow(sheetInfo.getNextRowIdx());
        for ( NetworkAttributesElement attr : networkAttributesElements ) {

            if (attr.isSingleValue()) {
                int columnIdx = sheetInfo.findColumnIdx(attr.getName());
                row.createCell(columnIdx).setCellValue(attr.getValue());

            } else {

                String attrName = attr.getName();
                StringBuilder attrValues = new StringBuilder();
                for ( String value : attr.getValues() ) {
                    attrValues.append(value);
                    attrValues.append('|');
                }
                attrValues.setLength(( attrValues.length() - 1) );

            }
        }

        sheetInfo.incrementNextRowIdx();
    }

    static void populateNodeTableSheet(final NiceCXNetwork niceCX, final XSSFWorkbook wb, final SpreadsheetInfo sheetInfo) {

        List<Long> nodeKeys = niceCX.getNodes().keySet().stream()
                .sorted()
                .collect(Collectors.toList());

        if (nodeKeys.isEmpty()) return;

        Sheet sheet = sheetInfo.getSheet();
        for ( Long nodeKey : nodeKeys) {
            Row row = sheet.createRow(sheetInfo.getNextRowIdx());

            // Nodes
            String nodeId = String.valueOf(nodeKey);
            String nodeName = niceCX.getNodes().get(nodeKey).getNodeName();
            String nodeRepresents = niceCX.getNodes().get(nodeKey).getNodeRepresents();

//            System.err.println("\n***********************");
//            String x = String.format("id=%s, name=%s, represents=%s", nodeId, nodeName, nodeRepresents);
//            System.err.println(x);
//
//            //Node Attributes
//            Collection<NodeAttributesElement> nodeAttributeElements = niceCX.getNodeAttributes().get(nodeKey);
//            for ( NodeAttributesElement nae : niceCX.getNodeAttributes().get(nodeKey)) {
//                if (nae.isSingleValue()) {
//                    System.err.println("* String  " + nae.getName() + "=" + nae.getValue());
//                } else {
//                    System.err.println(" List_of_Strings  " + nae.getValues().toString());
//                }
//                System.err.println("\n***********************");
//            }

            sheetInfo.incrementNextRowIdx();
        }
    }

    static void populateEdgeTableSheet(final NiceCXNetwork niceCX, final XSSFWorkbook wb, final SpreadsheetInfo sheetInfo) {

        Map<Long, EdgesElement> edges = niceCX.getEdges();

        if (edges.isEmpty()) return;

        Sheet sheet = sheetInfo.getSheet();

        for ( Map.Entry<Long, EdgesElement> entry : edges.entrySet() ) {
            Row row = sheet.createRow(sheetInfo.getNextRowIdx());

            long edgeId = entry.getValue().getId();
            long sourceNodeId = entry.getValue().getSource();
            long targetNodeId = entry.getValue().getTarget();
            String edgeInteraction = entry.getValue().getInteraction();

            // Edges
            String sourceNode = niceCX.getNodes().get(sourceNodeId).getNodeName();
            String targetNode = niceCX.getNodes().get(targetNodeId).getNodeName();

            int rowIdx = sheetInfo.getNextRowIdx();
            String edgeData =
                    String.format("row=%d, edgeId=%d, source=%s, target=%s, interaction=%s"
                    , rowIdx, edgeId, sourceNode, targetNode, edgeInteraction);
            System.err.println(edgeData);

            // EdgeAttributes

            if (niceCX.getEdgeAttributes().values() != null) {
                for (Collection<EdgeAttributesElement> attrs : niceCX.getEdgeAttributes().values()) {
                    for (EdgeAttributesElement attr : attrs) {
                        if (attr.getPropertyOf().equals(edgeId)) {
                            if (attr.isSingleValue()) {
                                String k = attr.getName();
                                String v = attr.getValue();
                                System.err.print("{k=" + k + ", v=" + v + "}, ");
                            } else {
                                StringBuilder multipleValues = new StringBuilder();
                                for (String value : attr.getValues()) {
                                    multipleValues.append(value);
                                    multipleValues.append("|");
                                }
                                multipleValues.setLength((multipleValues.length() - 1 ));
                                String k = attr.getName();
                                String v = multipleValues.toString();
                                System.err.print("{k=" + k + ", v=\'" + v + "\'}, ");
                            }
                        }
                    }
                }

                System.err.println();
            }

            sheetInfo.incrementNextRowIdx();
        }
    }

    static List<String> gatherNetworkTableColumnHeadings(final NiceCXNetwork niceCX) {
        List<String> orderedColumnHeadings = new ArrayList<>();

        orderedColumnHeadings.add(ExcelAppConstants.NETWORK_NAME);
        orderedColumnHeadings.add(ExcelAppConstants.NETWORK_DESCRIPTION);

        List<String> filteredColumnHeadings = niceCX.getNetworkAttributes().stream()
                .map(AbstractAttributesAspectElement::getName)
                .filter( filter -> !filter.equalsIgnoreCase(ExcelAppConstants.NETWORK_NAME))
                .filter( filter -> !filter.equalsIgnoreCase(ExcelAppConstants.NETWORK_DESCRIPTION))
                .sorted(Comparator.comparing(nae -> nae))
                .collect(Collectors.toList());

        for ( String heading : filteredColumnHeadings ) {
            orderedColumnHeadings.add(heading);
        }

        return orderedColumnHeadings;
    }

    static List<String> gatherNodeTableColumnHeadings(final NiceCXNetwork niceCX) {
        List<String> orderedNodeColumnHeadings = new ArrayList<>();

        orderedNodeColumnHeadings.add(ExcelAppConstants.NODE_NAME);
        orderedNodeColumnHeadings.add(ExcelAppConstants.NODE_REPRESENTS);

        Set<String> sortedNodeAttributeNames = new TreeSet<>();
        for (Collection<NodeAttributesElement> nodeAttrs : niceCX.getNodeAttributes().values() ) {
            for ( NodeAttributesElement nae : nodeAttrs ) {
                sortedNodeAttributeNames.add(nae.getName());
            }
        }

        orderedNodeColumnHeadings.addAll(sortedNodeAttributeNames);

        orderedNodeColumnHeadings.add(ExcelAppConstants.NODE_CX_NODE_ID);

        return orderedNodeColumnHeadings;
    }

    static List<String> gatherEdgeTableColumnHeadings(final NiceCXNetwork niceCX) {
        List<String> orderedEdgeColumnHeadings = new ArrayList<>();

        orderedEdgeColumnHeadings.add(ExcelAppConstants.NODE_NAME);
        orderedEdgeColumnHeadings.add(ExcelAppConstants.EDGE_INTERACTION);
        orderedEdgeColumnHeadings.add(ExcelAppConstants.EDGE_TARGET);

        Set<String> sortedEdgeAttributeNames = new TreeSet<>();
        for (Collection<EdgeAttributesElement> nodeAttrs : niceCX.getEdgeAttributes().values() ) {
            for ( EdgeAttributesElement nae : nodeAttrs ) {
                sortedEdgeAttributeNames.add(nae.getName());
            }
        }

        orderedEdgeColumnHeadings.addAll(sortedEdgeAttributeNames);

        orderedEdgeColumnHeadings.add(ExcelAppConstants.EDGE_CX_EDGE_ID);

        return orderedEdgeColumnHeadings;
   }

    static int addColumnHeadings(final Sheet sheet, final List<String> columnHeadings) {
        int nextRowIdx = 0;
        int columnIdx = 0;

        Row row = sheet.createRow(nextRowIdx);

        for ( String columnHeading : columnHeadings ) {
            row.createCell(columnIdx).setCellValue(columnHeading);
            columnIdx++;
        }

        return ++nextRowIdx;
    }

    public List<String> makeColumnHeadings() {
        return new ArrayList<>();   // not used by ExcelApp
    }

    @Override
    public String toString() {
        return String.format(
            "ExcelApp{" +
            "cfg=" + cfg +
            ", niceCX=" + niceCX +
            ", writer=" + writer +
            ", outStream=" + outStream +
            ", wb=" + wb +
            ", spreadsheets=" + spreadsheets +
            '}',
            this.cfg == null ? "null" : this.cfg.toString(),
            this.niceCX == null ? "null" : this.niceCX.toString(),
            this.writer == null ? "null" : this.writer.toString(),
            this.outStream == null ? "null" : this.outStream.toString(),
            this.wb == null ? "null" : this.wb.toString(),
            this.spreadsheets.toString()
        );
    }

    public static class ExcelAppConstants {
        private static final String SHEET_NETWORK_TABLE = "Network table";
        private static final String SHEET_NODE_TABLE = "Node table";
        private static final String SHEET_EDGE_TABLE = "Edge table";

        private static final String NETWORK_NAME = "name";
        private static final String NETWORK_DESCRIPTION = "description";

        private static final String NODE_NAME = "name";
        private static final String NODE_REPRESENTS = "represents";
        private static final String NODE_CX_NODE_ID = "cx node id";

        private static final String EDGE_INTERACTION = "interaction";
        private static final String EDGE_TARGET = "target";
        private static final String EDGE_CX_EDGE_ID = "cx edge id";
    }
}
