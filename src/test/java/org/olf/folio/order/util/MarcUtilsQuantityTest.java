package org.olf.folio.order.util;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;

public class MarcUtilsQuantityTest extends MarcUtilsBaseTest { 
	boolean debug = false; 

	@Test
	public void testGetElectronic() {
		String fname = casalini;
		try {
			List<Record> records = getRecords(fname);
			for (Record record: records) {
				DataField nineEighty = (DataField) record.getVariableField("980");
				String quantity = marcUtils.getQuantity(nineEighty);
				if (debug) {
					System.out.println(fname + " - quantity: " + quantity);
				} else {
					assertNotNull(quantity);
					assertTrue(quantity.length() > 0);
				} 
				
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
		 
	} 

}
