package step6;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class InvoiceWriter {
	Writer writer;
	InvoiceWriter() throws IOException {
		String writerFileName = "C:\\esm-semi\\ex21\\invoice.dat";
		 this.writer = new BufferedWriter(new FileWriter(writerFileName));
	}
	InvoiceWriter(Writer writer) {
		this.writer = writer;
	}

	public void write(Invoice invoice) throws IOException {
		writer.write("1 " + invoice.getOwnerTelNumber() + "\n");
		writer.write("5 " + invoice.getBasicCharge() + "\n");
		writer.write("7 " + invoice.getCallCharge() + "\n");
		writer.write("9 ====================\n");
	};
	public void close() throws IOException{
		writer.close();
	};
}
