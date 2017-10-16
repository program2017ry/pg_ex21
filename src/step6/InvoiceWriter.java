package step6;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class InvoiceWriter {
	InvoiceWriter(Iovoice invoice,String filename) {
		try {
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

			// 情報を全てString型にして、Listに入れる。
			String[] invoiceList = {invoice.constractorPhoneNum,String.valueOf(invoice.basicFee),String.valueOf(invoice.totalTelephoneFee)};

			// 全ての要素を出力する。
			for (String string : invoiceList) {
				writer.println(string);
			}
			writer.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
