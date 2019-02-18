package org.ndextools.morphcx.tables;

import org.ndexbio.cxio.aspects.datamodels.EdgeAttributesElement;
import org.ndexbio.cxio.aspects.datamodels.EdgesElement;
import org.ndexbio.cxio.aspects.datamodels.NodeAttributesElement;
import org.ndexbio.cxio.aspects.datamodels.NodesElement;
import org.ndexbio.model.cx.NiceCXNetwork;
import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.writers.TableWritable;

import java.io.IOException;
import java.util.*;

/**
 *  The subclass of Table that customizes output according to the NDEx WebApp table schema.
 */
public class WebApp extends Table implements Table2D {

    // column header labels used in this table, presented in column sequence order
    protected final static String LABEL_SOURCE_NODE = "Source Node";
    protected final static String LABEL_INTERACTION = "Interaction";
    protected final static String LABEL_TARGET_NODE = "Target Node";
    protected final static String LABEL_SOURCE_ID = "Source ID";
    protected final static String LABEL_NODE_ATTRIBUTE_SOURCE_ALIAS = "Source Alias";
    protected final static String LABEL_NODE_ATTRIBUTE_SOURCE_ = "Source ";
    protected final static String LABEL_TARGET_ID = "Target ID";
    protected final static String LABEL_NODE_ATTRIBUTE_TARGET_ALIAS = "Target Alias";
    protected final static String LABEL_NODE_ATTRIBUTE_TARGET_ = "Target ";
    protected final static String LABEL_EDGE_ATTRIBUTE_CITATION = "citation";
    protected final static String LABEL_CX_EDGE_ID = "cx edge id";
    protected final static String LABEL_CX_SOURCE_NODE_ID = "cx source node id";
    protected final static String LABEL_CX_TARGET_NODE_ID = "cx target node id";

    /**
     * Class constructor
     *
     * @param cfg reference to Configuration class object
     * @param niceCX reference to NiceCXNetwork class object
     * @param cxWriter reference to output writer class object
     */
    public WebApp(Configuration cfg, NiceCXNetwork niceCX, TableWritable cxWriter) {
        super(cfg, niceCX, cxWriter);
    }

    @Override
    public void morphThisCX() throws IOException {

        // Create and output column headers, saving them so that data can be arranged in that sequence for output.
        columnHeadings = makeColumnHeadings();
        columnsInTable = columnHeadings.size();

        try {
            exportWriter.outputRow(columnHeadings);
        } catch (Exception e) {
            String errMsg = this.getClass().getSimpleName() + ": - exportWriter is " + exportWriter;
            throw new IOException(errMsg);
        }

        // Iterate through all CX edges, gathering edge values into a unorderedListOfBins of cells which is output
        // at the end of each iteration.
        Map<Long, EdgesElement> edges = getNiceCX().getEdges();
        for (Map.Entry<Long, EdgesElement> entry : edges.entrySet()) {
            unorderedListOfBins.clear();

            long _cx_edge_id = entry.getValue().getId();
            long _cx_source_node_id = entry.getValue().getSource();
            long _cx_target_node_id = entry.getValue().getTarget();

            addCellToRow( LABEL_CX_EDGE_ID, Long.toString( _cx_edge_id ) );
            addCellToRow( LABEL_CX_SOURCE_NODE_ID, Long.toString( _cx_source_node_id ) );
            addCellToRow( LABEL_CX_TARGET_NODE_ID, Long.toString( _cx_target_node_id ) );

            // gather edge values for eventual output
            String _Interaction = entry.getValue().getInteraction();
            addCellToRow( LABEL_INTERACTION, _Interaction );

            // gather node values for eventual output
            Map<Long, NodesElement> nodes = getNiceCX().getNodes();
            List<Bin> nodeData = collectNodeData( nodes, _cx_source_node_id, _cx_target_node_id );
            addCellsToRow(nodeData);

            // gather node attribute values for eventual output
            List<Bin> nodeAttributeData;
            nodeAttributeData = collectNodeAttributes(_cx_source_node_id, _cx_target_node_id);
            addCellsToRow(nodeAttributeData);

            // gather edge attribute values for eventual output
            List<Bin> edgeAttributeData;
            edgeAttributeData = collectEdgeAttributes(_cx_edge_id);
            addCellsToRow(edgeAttributeData);

            // Arrange cells according to column heading order and output the unorderedListOfBins
            List<String> orderedListOfCells = pickAndPlop();
            try {
                exportWriter.outputRow(orderedListOfCells);
            } catch (Exception e) {
                String errMsg = this.getClass().getSimpleName() + ": - exportWriter is " + exportWriter;
                throw new IOException(errMsg);
            }
        }
    }

    private List<Bin> collectNodeData(Map<Long, NodesElement> nodes, Long sourceNodeId, Long targetNodeId) {
        List<Bin> nodeDataBins = new ArrayList<>();

        String _Source_Node = nodes.get(sourceNodeId).getNodeName();
        nodeDataBins.add( new Bin( LABEL_SOURCE_NODE, _Source_Node ));

        String _Source_Id = nodes.get(sourceNodeId).getNodeRepresents();
        nodeDataBins.add( new Bin( LABEL_SOURCE_ID, _Source_Id ));


        String _Target_Node = nodes.get(targetNodeId).getNodeName();
        nodeDataBins.add( new Bin( LABEL_TARGET_NODE, _Target_Node ));

        String _Target_Id = nodes.get(targetNodeId).getNodeRepresents();
        nodeDataBins.add( new Bin( LABEL_TARGET_ID, _Target_Id ));

        return nodeDataBins;
    }

    private List<Bin> collectNodeAttributes(long cxSourceNodeId, long cxTargetNodeId) {
        List<Bin> nodeAttributeBins = new ArrayList<>();

        // collect source node attributes only if there are any
        if (getNiceCX().getNodeAttributes().get(cxSourceNodeId) != null) {
            for (NodeAttributesElement nae: getNiceCX().getNodeAttributes().get(cxSourceNodeId)) {
                if (nae.isSingleValue()) {
                    String k = LABEL_NODE_ATTRIBUTE_SOURCE_ + nae.getName();
                    String v = nae.getValue();
                    nodeAttributeBins.add(new Bin(k, v));
                } else {
                    StringBuilder multipleAttrValues = new StringBuilder();
                    for (String value : nae.getValues()) {
                        multipleAttrValues.append(value);
                        multipleAttrValues.append("|");
                    }
                    multipleAttrValues.setLength((multipleAttrValues.length() - 1 ));
                    String k = LABEL_NODE_ATTRIBUTE_SOURCE_+ nae.getName();
                    String v = multipleAttrValues.toString();
                    nodeAttributeBins.add(new Bin(k, v));
                }
            }
        }

        // collect target node attributes only if there are any
        if (getNiceCX().getNodeAttributes().get(cxTargetNodeId) != null) {
            for (NodeAttributesElement nae: getNiceCX().getNodeAttributes().get(cxTargetNodeId)) {
                if (nae.isSingleValue()) {
                    String k = LABEL_NODE_ATTRIBUTE_TARGET_ + nae.getName();
                    String v = nae.getValue();
                    nodeAttributeBins.add(new Bin(k, v));
                } else {
                    StringBuilder multipleAttrValues = new StringBuilder();
                    for (String value : nae.getValues()) {
                        multipleAttrValues.append(value);
                        multipleAttrValues.append("|");
                    }
                    multipleAttrValues.setLength((multipleAttrValues.length() - 1 ));
                    String k = LABEL_NODE_ATTRIBUTE_TARGET_ + nae.getName();
                    String v = multipleAttrValues.toString();
                    nodeAttributeBins.add(new Bin(k, v));
                }
            }
        }

        return nodeAttributeBins;
    }

    private List<Bin> collectEdgeAttributes(long cxEdgeId) {
        List<Bin> edgeAttributeBins = new ArrayList<>();

        // collect edge attributes only if there are any
        if (getNiceCX().getEdgeAttributes().values() != null) {
            for (Collection<EdgeAttributesElement> attrs : getNiceCX().getEdgeAttributes().values()) {
                for (EdgeAttributesElement attr : attrs) {
                    if (attr.getPropertyOf().equals(cxEdgeId)) {
                        if (attr.isSingleValue()) {
                            String k = attr.getName();
                            String v = attr.getValue();
                            edgeAttributeBins.add(new Bin(k, v));
                        } else {
                            StringBuilder multipleValues = new StringBuilder();
                            for (String value : attr.getValues()) {
                                multipleValues.append(value);
                                multipleValues.append("|");
                            }
                            multipleValues.setLength((multipleValues.length() - 1 ));
                            String k = attr.getName();
                            String v = multipleValues.toString();
                            edgeAttributeBins.add(new Bin(k, v));
                        }
                    }
                }
            }
        }

        return edgeAttributeBins;
    }

    /**
     *
     * @return list of ordered column headings according to the sequence in which each was added.
     */
    public List<String> makeColumnHeadings() {

        int COLUMN_COUNT = 30;
        List<String> colHeadings = new ArrayList<>( COLUMN_COUNT );

        colHeadings.add(LABEL_SOURCE_NODE);
        colHeadings.add(LABEL_INTERACTION);
        colHeadings.add(LABEL_TARGET_NODE);

        Collection<String> orderedNodeAttributeLabels;
        orderedNodeAttributeLabels = gatherNodeColumnHeadings(niceCX);
        ((ArrayList<String>) colHeadings).addAll(orderedNodeAttributeLabels);

        Collection<String> orderedEdgeAttributeLabels;
        orderedEdgeAttributeLabels = gatherEdgeColumnHeadings(niceCX);
        ((ArrayList<String>) colHeadings).addAll(orderedEdgeAttributeLabels);

        colHeadings.add(LABEL_CX_EDGE_ID);
        colHeadings.add(LABEL_CX_SOURCE_NODE_ID);
        colHeadings.add(LABEL_CX_TARGET_NODE_ID);

        return colHeadings;
    }

    Collection<String> gatherNodeColumnHeadings(NiceCXNetwork niceCX) {
        List<String> orderedNodeAttributes = new ArrayList<>();
        Set<String> unorderedNodeAttributes = new TreeSet<>();

        for (Collection<NodeAttributesElement> nodeAttrs : niceCX.getNodeAttributes().values() ) {
            for ( NodeAttributesElement nae : nodeAttrs ) {
                unorderedNodeAttributes.add(nae.getName());
            }
        }

        orderedNodeAttributes.add(LABEL_SOURCE_ID);
        orderedNodeAttributes.add(LABEL_NODE_ATTRIBUTE_SOURCE_ALIAS);
        for (String  shortAttrLabel : unorderedNodeAttributes) {
            orderedNodeAttributes.add("Source " + shortAttrLabel);
        }

        orderedNodeAttributes.add(LABEL_TARGET_ID);
        orderedNodeAttributes.add(LABEL_NODE_ATTRIBUTE_TARGET_ALIAS);
        for (String  shortAttrLabel : unorderedNodeAttributes) {
            orderedNodeAttributes.add("Target " + shortAttrLabel);
        }

        return  orderedNodeAttributes;
    }

    Collection<String> gatherEdgeColumnHeadings(NiceCXNetwork niceCX) {

        List<String> orderedEdgeAttributes = new ArrayList<String>();
        Set<String> allEdgeAttributes = new TreeSet<>();
//        Set<String> allEdgeAttributes = new LinkedHashSet<>();

        for (Collection<EdgeAttributesElement> attrs : getNiceCX().getEdgeAttributes().values()) {
            for ( EdgeAttributesElement attr : attrs ) {
                if (!attr.getName().equalsIgnoreCase(LABEL_EDGE_ATTRIBUTE_CITATION)) {
                    allEdgeAttributes.add(attr.getName());
                }
            }
        }

        orderedEdgeAttributes.add(LABEL_EDGE_ATTRIBUTE_CITATION);
//        for (String edgeAttribute : allEdgeAttributes) {
//            orderedEdgeAttributes.add(edgeAttribute);
//        }
        orderedEdgeAttributes.addAll(allEdgeAttributes);

        return  orderedEdgeAttributes;
    }

}
