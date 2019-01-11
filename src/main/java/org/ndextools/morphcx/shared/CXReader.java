package org.ndextools.morphcx.shared;

import java.io.*;

import org.ndexbio.model.cx.NiceCXNetwork;
import org.ndexbio.rest.client.NdexRestClientUtilities;

/**
 *  Produces a transitional CX network data structure that will be morphed.
 */
public class CXReader {
    private final Configuration cfg;


    public CXReader(Configuration cfg) {
        Utilities.nullReferenceCheck(cfg, Configuration.class.getSimpleName());
        this.cfg = cfg;
    }

    /**
     * Produces a NiceCXNetwork object using the applicable input stream (stdin or file).
     *
     * @throws IOException if a problem occurs when producing a NiceCXNetwork object
     * @return NiceCXNetwork object containing the CX network data structure to be morphed
     */
    public NiceCXNetwork produceNiceCX() throws IOException {
        NiceCXNetwork cx;

        if (cfg.inputIsFile()) {

            try (InputStream input = new FileInputStream( cfg.getInputFilename() );
                 BufferedInputStream bufferedInput = new BufferedInputStream(input))
            {
                cx = NdexRestClientUtilities.getCXNetworkFromStream(bufferedInput);
            }
            catch (Exception e) {
                String msg = this.getClass().getSimpleName() + ": " + e.getMessage();
                throw new IOException(msg);
            }

        } else {

            try (BufferedInputStream bufferedInput = new BufferedInputStream(System.in)) {
                cx = NdexRestClientUtilities.getCXNetworkFromStream(bufferedInput);
            }
            catch (Exception e) {
                String msg = this.getClass().getSimpleName() + ": " + e.getMessage();
                throw new IOException(msg);
            }
        }

        return cx;
    }

}
