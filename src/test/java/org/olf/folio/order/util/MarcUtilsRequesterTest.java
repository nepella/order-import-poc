package org.olf.folio.order.util;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;

public class MarcUtilsRequesterTest extends MarcUtilsBaseTest {
    boolean debug = false;

    @Test
    public void testGetRequester() {
        String fname = requestors;
        try {
            List<Record> records = getRecords(fname);
            for (Record record: records) {
                String requester = marcUtils.getRequester(record);
                if (debug) {
                    System.out.println(fname + " - requester: " + requester);
                } else {
                    assertNotNull(requester);
                    assertTrue(requester.length() > 0);
                }
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}
