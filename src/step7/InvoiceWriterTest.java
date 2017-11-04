package step7;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

public class InvoiceWriterTest {
	@Test
	public void testWrite() throws IOException {
		StringWriter output = new StringWriter();
		InvoiceWriter writer = new InvoiceWriter(output);
		Invoice invoice = new Invoice();
		invoice.setOwnerTelNumber("090-1234-0001");
		invoice.setBasicCharge(1100);
		invoice.addCallCharge(230);
		writer.write(invoice);
		String expected
				= "1 090-1234-0001\n"
				+ "5 1100\n"
				+ "7 230\n"
				+ "9 ====================\n";

		writer.close();
		String actual = output.toString();
		assertEquals(expected, actual);
	}
}
