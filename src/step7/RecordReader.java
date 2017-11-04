package step7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class RecordReader {
	private BufferedReader reader;

	//　コンストラクタ1(引数なしのデフォルト値)
	public RecordReader() throws FileNotFoundException {
		this(new FileReader("C:\\esm-semi\\ex21\\record.log"));
	}


//　コンストラクタ2(引数あり)
	public RecordReader(Reader reader) {
		this.reader = new BufferedReader(reader);
	}

	//引数あるか
	public Record read() throws IOException {
		String line = reader.readLine();
		if (line == null) {
			return null;
		}
		return new Record(line);
	}

	public void close() throws IOException {
		reader.close();
	}
}
