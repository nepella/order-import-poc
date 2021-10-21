package org.olf.folio.order.util;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

public class LookupUtilHoldingsTest extends LookupUtilBaseTest {

    boolean debug = false;

    public LookupUtilHoldingsTest() {
        // TODO Auto-generated constructor stub
    }


    @Test
    public void getHoldingsTest() {

        this.getUtil().setEndPoint("holdings-types", "1000");
        Map<String,String> map = new HashMap<String, String>();
        try {
            map = this.getUtil().getReferenceValues(this.getToken());
            if (debug) {
                Iterator iter = map.keySet().iterator();

                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    System.out.println("name: "+ key);
                    System.out.println("id: "+ map.get(key));
                }
            } else {
                String testKey = "Serial";
                assertNotNull(map);
                assertTrue(map.containsKey(testKey));
                assertNotNull(map.get(testKey));
            }

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (InterruptedException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            //fileOut.close();
        }

    }

}
