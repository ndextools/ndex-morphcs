package org.ndextools.morphcx.tables;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ndexbio.model.cx.NiceCXNetwork;
import org.ndextools.morphcx.shared.CXReader;
import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.shared.Utilities;
import org.ndextools.morphcx.writers.TableToPOI;
import org.ndextools.morphcx.writers.TableWritable;
import org.ndextools.morphcx.writers.WriterFactory;

/**
 * The TablesRoot class controls the high-level steps involved in morphing a CX network.
 */
public class TablesRoot {

    /**
     * Converts the JSON-format NDEx CX Network into a NiceCXNetwork object, Then control
     * is passed to the class which morphs the NiceCXNetwork object to the desired output format.
     *
     * @param cfg reference to a Configuration class object
     * @throws Exception base class exception when there is an IO or other processing error
     */
    public void execute(final Configuration cfg) throws Exception {

        String msg = String.format("%s: expecting MorphCX.appConfig() !=null, actual=%s ",
                TablesRoot.class.getSimpleName(), cfg);
        Utilities.nullReferenceCheck(cfg, msg);

        // Transform input stream to a NiceCXNetwork object.
        CXReader cxReader = new CXReader(cfg);
        NiceCXNetwork niceCX = cxReader.produceNiceCX();

        // Prepare for and run a morphing operation on the NiceCXNetwork object.
        switch (cfg.getOperation()) {
            case TSV:
            case CSV:
            case NOT_SPECIFIED:

                try ( TableWritable writer = setupCSVOutputWriter(cfg) ) {
                    Table2D morph = new WebApp( cfg, niceCX, writer);
                    morph.morphThisCX();
                } catch (Exception e) {
                    throw new Exception(e);
                }
                break;

            case EXCEL:

                if (cfg.outputIsFile()) {
                    setupForPOIAsFile(cfg, niceCX);
                } else {
                    setupForPOIAsStdout(cfg, niceCX);
                    return;
                }
                break;

            default:
                String errMsg = String.format("%s: \'%s\' is not a valid operation",
                        TablesRoot.class.getSimpleName(), cfg.getOperation());
                throw new Exception(errMsg);
        }
    }

    private static TableWritable setupCSVOutputWriter(Configuration cfg) throws Exception {
        WriterFactory wf = new WriterFactory(cfg);
        return wf.getCSVWriter();
    }

    void setupForPOIAsFile(Configuration cfg, NiceCXNetwork niceCX) throws Exception {

        try (OutputStream outputStream = new FileOutputStream(cfg.getOutputFilename());
             XSSFWorkbook workbook = new XSSFWorkbook() ) {
            TableToPOI writer = new TableToPOI(outputStream, workbook);
            Table3D morph = new ExcelApp(cfg, niceCX, writer, workbook, outputStream);
            morph.morphThisCX();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    void setupForPOIAsStdout(Configuration cfg, NiceCXNetwork niceCX) throws Exception {

        try (OutputStream outputStream = new PrintStream(System.out);
             XSSFWorkbook workbook = new XSSFWorkbook() ) {
            TableToPOI writer = new TableToPOI(outputStream, workbook);
            Table3D morph = new ExcelApp(cfg, niceCX, writer, workbook, outputStream);
            morph.morphThisCX();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}
