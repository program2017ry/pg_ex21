package step7;

import static org.junit.Assert.*;

import org.junit.Test;

public class IovoiceTest {
	@Test
	public void testClear() {
		Invoice invoice = new Invoice();
		// 初期値
		assertNull(invoice.getOwnerTelNumber());
		assertEquals(0, invoice.getBasicCharge());
		assertEquals(0, invoice.getCallCharge());
		invoice.setOwnerTelNumber("090-1234-0001");
		invoice.setBasicCharge(1200);
		invoice.addCallCharge(100);
		assertEquals("090-1234-0001", invoice.getOwnerTelNumber());
		assertEquals(1200, invoice.getBasicCharge());
		assertEquals(100, invoice.getCallCharge());
		// 初期値にクリアされる
		invoice.clear();
		assertNull(invoice.getOwnerTelNumber());
		assertEquals(0, invoice.getBasicCharge());
		assertEquals(0, invoice.getCallCharge());
	}

	@Test
	public void testAddCharge() {
		Invoice invoice = new Invoice();
		invoice.addCallCharge(100);
		assertEquals(100, invoice.getCallCharge());
		// 金額が加算される
		invoice.addCallCharge(100);
		assertEquals(200, invoice.getCallCharge());
	}
}
