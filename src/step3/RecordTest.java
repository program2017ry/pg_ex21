package step3;

import static org.junit.Assert.*;

import org.junit.Test;

public class RecordTest {

//	@Test
//	public void testRecord() {
//		assertEquals("2 C1 090-1234-0002", new Record("2 C1 090-1234-0002"));
//	}

	@Test
	public void testtoString() {
		Record record = new Record("2 C1 090-1234-0002");
		assertEquals("2 C1 090-1234-0002", record.toString());
	}

	@Test
	public void testgetRecordCode() {
		Record record = new Record("2 C1 090-1234-0002");
		assertEquals('2', record.getRecordCode());
	}

	@Test
	public void testgetOwnerTelNumber() {
		Record record = new Record("1 090-1234-0001");
		assertEquals("090-1234-0001", record.getOwnerTelNumber());
	}

	@Test
	public void testgetServiceCode() {
		Record record = new Record("2 C1 090-1234-0002");
		assertEquals("C1", record.getServiceCode());
	}

	@Test
	public void testgetServiceOptionTrue() {
		Record record = new Record("2 C1 090-1234-0002");
		assertEquals("090-1234-0002", record.getServiceOption());
	}

	@Test
	public void testgetServiceOptionFalse() {
		Record record = new Record("E1");
		assertEquals(null, record.getServiceOption());
	}

	@Test
	public void testgetStartHour() {
		Record record = new Record("5 2004/06/05 17:50 010 090-1234-9999");
		assertEquals(17, record.getStartHour());
	}

	@Test
	public void testgetCallMinutes() {
		Record record = new Record("5 2004/06/05 17:50 010 090-1234-9999");
		assertEquals(10, record.getCallMinutes());
	}

	@Test
	public void testgetCallNumber() {
		Record record = new Record("5 2004/06/05 17:50 010 090-1234-9999");
		assertEquals("090-1234-9999", record.getCallNumber());
	}


}
