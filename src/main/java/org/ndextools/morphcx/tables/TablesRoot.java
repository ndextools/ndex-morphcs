package org.ndextools.morphcx.tables;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ndexbio.model.cx.NiceCXNetwork;
import org.ndextools.morphcx.shared.CXReader;
import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.shared.Utilities;
import org.ndextools.morphcx.writers.TableToCSV;
import org.ndextools.morphcx.writers.TableWritable;
import org.ndextools.morphcx.writers.WriterFactory;

/**
 * The TablesRoot class controls the high-level steps involved in morphing a CX network.
 */
public class TablesRoot {

    /**
     * Converts the JSON-format NDEx CX Network into a NiceCXNetwork object, then uses a factory
     * class to instantiate the class of writer needed by the operation.  Finally, control is passed
     * to the class which morphs the NiceCXNetwork object to the desired output format.
     *
     * @param cfg reference to a Configuration class object
     * @throws Exception base class exception when there is an IO or other processing error
     */
    public void execute(final Configuration cfg) throws Exception {
        String msg = String.format("%s: expecting MorphCX.appConfig() !=null, actual=%s ",
                TablesRoot.class.getSimpleName(), cfg);
        Utilities.nullReferenceCheck(cfg, msg);

        NiceCXNetwork niceCX = LoadInputCxNetwork(cfg);

        switch (cfg.getOperation())
        {
            case EXCEL:
                // TODO: BEGIN *************************************************************
                // TODO: Remove feature disabled conditional when features goes alpha
                if (cfg.EXCEL_FEATURE_DISABLED) {
                    System.err.println("The Excel feature is still being developed!");
                    return;
                } else {

                    try ( Workbook writer = setupPOIOutputWriter(cfg) ) {
                        Table3D morph = new CXToExcel(cfg, niceCX, writer);
                        morph.morphThisCX();
                    } catch (Exception e) {
                        throw new Exception(e);
                    }
                    break;
                }
                // TODO: END **************************************************************

            case TSV:
            case CSV:
            case NOT_SPECIFIED:

                try ( TableWritable writer = setupCSVOutputWriter(cfg) ) {
                    Table2D morph = new Webapp( cfg, niceCX, writer);
                    morph.morphThisCX();
                } catch (Exception e) {
                    throw new Exception(e);
                }
                break;

        }
    }

    private static NiceCXNetwork LoadInputCxNetwork(Configuration cfg) throws IOException {
        CXReader cxReader = new CXReader(cfg);
        return cxReader.produceNiceCX();
    }

    private static TableWritable setupCSVOutputWriter(Configuration cfg) throws Exception {
        WriterFactory wf = new WriterFactory(cfg);
        return wf.getWriter();
    }


    private Workbook setupPOIOutputWriter(Configuration cfg) throws Exception {
        Workbook workbook;

        try
        {

        // Determine whether the output is to a file or stdout.
            if (cfg.outputIsFile()) {
                Workbook wb = WorkbookFactory.create(new File(cfg.getOutputFilename()));
                return wb;
            } else {
                Workbook wb = WorkbookFactory.create(new FileInputStream(cfg.getOutputFilename()));
                return wb;
            }

        }
            catch (Exception e) {
            String msg = this.getClass().getSimpleName() + ": " + e.getMessage();
            throw new Exception(msg);
        }
    }

}
