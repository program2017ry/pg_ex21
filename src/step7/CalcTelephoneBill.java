package step7;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CalcTelephoneBill {
	private static final int INITIAL_BASIC_CHARGE = 1000;
	private static final int INITIAL_CALL_UNIT_PRICE = 20;

	public static void main(String[] args){
		try {
			RecordReader reader = new RecordReader();
			InvoiceWriter writer = new InvoiceWriter();

			Invoice invoice = new Invoice();
			DayService dayService = new DayService();
			ArrayList <String> serviceCodeList = new ArrayList();	// 加入サービス
			ArrayList <String> registerPhoneNumList = new ArrayList();	//C1の場合の登録電話番号


			for (Record record = reader.read(); record != null; record = reader.read()) {
				switch (record.getRecordCode()) {
				case '1':
					// 初期化処理
					serviceCodeList.clear();
					registerPhoneNumList.clear();
					dayService.clear();
					invoice.clear();

					//契約者情報取得し、Inovoiceインスタンスに登録
					invoice.setOwnerTelNumber(record.getOwnerTelNumber());
					break;

				case '2':
					//サービスコード取得し、Listに保存
					String serviceCode = record.getServiceCode();
					serviceCodeList.add(serviceCode);
					dayService.checkService(record);

					// ServiceCodeがC1の場合は、登録電話番号を取得し、Listに保存
					if (serviceCode.equals("C1")) {
						String registerPhoneNum = record.getServiceOption();
						registerPhoneNumList.add(registerPhoneNum);
					}
					break;

				case '5':
					// 通話単価　＊　通話時間　を通話料金に追加　
					int unitPrice = calcUnitPrice(serviceCodeList, registerPhoneNumList, record, dayService);
					invoice.addCallCharge(unitPrice * record.getCallMinutes());
					break;

				case '9':
					//サービスコードに応じて、基本料金を算出
					calcBasicCharge(invoice,serviceCodeList,dayService);
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
	public static int calcUnitPrice(ArrayList<String> serviceCodeList, ArrayList<String> registerPhoneNumList, Record record, DayService dayService) {
		int minuteFee = INITIAL_CALL_UNIT_PRICE;
		if (dayService.isServiceTime(record.getStartHour())) {
			minuteFee = dayService.calcUnitPrice(record, minuteFee);
		}
		if (registerPhoneNumList.contains(record.getCallNumber())) {
			minuteFee = minuteFee/2;
		}
		return minuteFee;
	}

	private static void calcBasicCharge(Invoice invoice, ArrayList<String> serviceCodeList, DayService dayService) {
		int basicCharge = INITIAL_BASIC_CHARGE;
		if (serviceCodeList.contains("C1")) {
			 basicCharge += 100;
		}
		if (dayService.isJoined()) {
			basicCharge = dayService.calcBasicCharge(basicCharge);
		}
		invoice.addBasicCharge(basicCharge);
	}

}