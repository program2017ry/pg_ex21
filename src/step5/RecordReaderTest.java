package step5;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
public class RecordReaderTest {
	@Test
	public void testRead() throws IOException {
		String content
			= "1 090-1234-0001\n"
			+ "2 C1 090-1234-0002\n"
			+ "5 2004/06/04 03:34 003 090-1234-0002\n"
			+ "9 *************************\n";
		StringReader stringReader = new StringReader(content);
		RecordReader reader = new RecordReader(stringReader);
		assertEquals("1 090-1234-0001", reader.read().toString());
		assertEquals("2 C1 090-1234-0002", reader.read().toString());
		assertEquals("5 2004/06/04 03:34 003 090-1234-0002", reader.read().toString());
		assertEquals("9 *************************", reader.read().toString());
		assertNull(reader.read());
		reader.close();
	}
}