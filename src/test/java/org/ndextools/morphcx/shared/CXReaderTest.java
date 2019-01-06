package org.ndextools.morphcx.shared;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.ndexbio.model.cx.NiceCXNetwork;

/**
 * Unit tests for CXReader class.
 */
public class CXReaderTest {
    private String LBC_FILTERED_ERK_AKT = "src/test/resources/LBC_FILTERED_ERK_AKT.json";
    private String LUMINAL_BREAST_CANCER = "src/test/resources/LUMINAL_BREAST_CANCER.json";
    private String EMPTY_LIST = "src/test/resources/EMPTY_LIST.json";

    @Ignore
    public void _Should_Load_NiceCxNetwork_LBC_FILTERED_ERK_AKT() throws Exception {
        String[] args = {"-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = new Configuration(args);
        cfg.configure();

        CXReader cxReader = new CXReader(cfg);
        NiceCXNetwork niceCX = cxReader.produceNiceCX();

        Assert.assertEquals(30, niceCX.getEdges().size());
        Assert.assertEquals(12, niceCX.getNodes().size());
        Assert.assertEquals(9, niceCX.getMetadata().size());
    }

    @Ignore
    public void _Should_Load_NiceCxNetwork_LBC() throws Exception {
        String[] args = {"-i", LUMINAL_BREAST_CANCER};
        Configuration cfg = new Configuration(args);
        cfg.configure();

        CXReader cxReader = new CXReader(cfg);
        NiceCXNetwork niceCX = cxReader.produceNiceCX();

        Assert.assertEquals(158, niceCX.getEdges().size());
        Assert.assertEquals(32, niceCX.getNodes().size());
        Assert.assertEquals(10, niceCX.getMetadata().size());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Ignore
    public void _Should_Throw_IOException() throws Exception {
        String[] args = {"-i", EMPTY_LIST};
        Configuration cfg = new Configuration(args);
        cfg.configure();

        CXReader cxReader = new CXReader(cfg);

        thrown.expect(IOException.class);    // IOException thrown by NiceCXNetwork class
        NiceCXNetwork niceCX = cxReader.produceNiceCX();
    }
}

