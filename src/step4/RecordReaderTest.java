package step4;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class RecordReaderTest {

	@Test
	public void test() {
		try {
			//　以下が実行される。
			// BufferedReader reader = new BufferedReader(new FileReader("C:\\esm-semi\\ex21\\record.log"));
			RecordReader reader = new RecordReader();

			// Record("1 090-1234-00012 ")
			// this.record = "1 090-1234-00012 "
			Record record = reader.read();
			assertEquals("1 090-1234-0001",record.toString());
		} catch(FileNotFoundException e) {
			fail();
		} catch (IOException e) {

		}
	}

	@Test
	public void test1() {
		try {
			//　以下が実行される。
			BufferedReader practice = new BufferedReader(new FileReader("C:\\esm-semi\\ex21\\recordnull.log"));
			RecordReader reader = new RecordReader(practice);

			// Record("1 090-1234-00012 ")
			// this.record = "1 090-1234-00012 "
			Record record = reader.read();
			assertNull(record);
		} catch(FileNotFoundException e) {
			fail();
		} catch (IOException e) {
			fail();
		}
	}
}
