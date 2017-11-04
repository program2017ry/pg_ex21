package step8;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CalcTelephoneBill {
	private static final int INITIAL_BASIC_CHARGE = 1000;
	private static final int INITIAL_CALL_UNIT_PRICE = 20;

	public static void main(String[] args){
		try {
			RecordReader reader = new RecordReader();
			InvoiceWriter writer = new InvoiceWriter();

			Invoice invoice = new Invoice();
			DayService dayService = new DayService();
			FamilyService familyService = new FamilyService();

			for (Record record = reader.read(); record != null; record = reader.read()) {
				switch (record.getRecordCode()) {
				case '1':
					// 初期化処理
					dayService.clear();
					familyService.clear();
					invoice.clear();

					//契約者情報取得し、Inovoiceインスタンスに登録
					invoice.setOwnerTelNumber(record.getOwnerTelNumber());
					break;

				case '2':
					//サービスコード取得し、Listに保存
					dayService.checkService(record);
					familyService.checkService(record);
					break;

				case '5':
					// 通話単価　＊　通話時間　を通話料金に追加　
					int unitPrice = calcUnitPrice(record, dayService, familyService);
					invoice.addCallCharge(unitPrice * record.getCallMinutes());
					break;

				case '9':
					//サービスコードに応じて、基本料金を算出
					int basicCharge =calcBasicCharge(dayService, familyService);
					invoice.addBasicCharge(basicCharge);
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

	// 通話料金/分を計算。プランと登録電話番号リストと、Recordインスタンスを渡す。
	public static int calcUnitPrice(Record record, DayService dayService, FamilyService familyService) {
		int unit_price = INITIAL_CALL_UNIT_PRICE;
		if (dayService.isServiceTime(record.getStartHour())) {
			unit_price = dayService.calcUnitPrice(record, unit_price);
		}
		if (familyService.isFamilyTelNumber(record.getCallNumber())) {
			unit_price = familyService.calcUnitPrice(record, unit_price);
		}
		return unit_price;
	}

	private static int calcBasicCharge(DayService dayService, FamilyService familyService) {
		int basic_charge = INITIAL_BASIC_CHARGE;
		if (dayService.isJoined()) {
			basic_charge = dayService.calcBasicCharge(basic_charge);
		}
		if (familyService.isJoined()) {
			basic_charge = familyService.calcBasicCharge(basic_charge);
		}
		return basic_charge;
	}

}