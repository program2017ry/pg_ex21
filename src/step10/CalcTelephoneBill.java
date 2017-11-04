package step10;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CalcTelephoneBill {
	private static final int INITIAL_BASIC_CHARGE = 1000;
	private static final int INITIAL_CALL_UNIT_PRICE = 20;

	private static int basic_charge = 0;
	private static int unit_price = 0;

	public static void main(String[] args){
		try {
			RecordReader reader = new RecordReader();
			InvoiceWriter writer = new InvoiceWriter();

			Invoice invoice = new Invoice();
			ServiceCollection serviceCollection = new ServiceCollection();

			for (Record record = reader.read(); record != null; record = reader.read()) {
				switch (record.getRecordCode()) {
				case '1':
					// 初期化処理
					basic_charge = INITIAL_BASIC_CHARGE;
					serviceCollection.clear();
					invoice.clear();

					//契約者情報取得し、Inovoiceインスタンスに登録
					invoice.setOwnerTelNumber(record.getOwnerTelNumber());
					break;

				case '2':
					serviceCollection.checkService(record);
					break;

				case '5':
					// 通話単価　＊　通話時間　を通話料金に追加　
					unit_price = serviceCollection.calcUnitPrice(record, unit_price);
					invoice.addCallCharge(unit_price * record.getCallMinutes());
					unit_price = INITIAL_CALL_UNIT_PRICE;
					break;

				case '9':
					//サービスコードに応じて、基本料金を算出
					basic_charge = serviceCollection.calcBasicCharge(basic_charge);
					invoice.addBasicCharge(basic_charge);
					System.out.print("1 " + invoice.getOwnerTelNumber() + "\n"); //test用
					System.out.print("5 " + invoice.getBasicCharge() + "\n"); //test用
					System.out.print("7 " + invoice.getCallCharge() + "\n"); //test用
					System.out.print("============\n"); //test用
					writer.write(invoice);
					break;
				}
			}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
//		} finally {
//			writer.close();
		}
		}
	// flushとfinallyは不要。
}