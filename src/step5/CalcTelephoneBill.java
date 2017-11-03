package step5;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CalcTelephoneBill {
	public static void main(String[] args){
		try {
			RecordReader reader = new RecordReader();
			String writerFileName = "C:\\esm-semi\\ex21\\invoice.dat";
			BufferedWriter writer = new BufferedWriter(new FileWriter(writerFileName));
			Invoice invoice = new Invoice();
			ArrayList <String> serviceCodeList = new ArrayList();	// 加入サービス
			ArrayList <String> registerPhoneNumList = new ArrayList();	//C1の場合の登録電話番号

			for (Record record = reader.read(); record != null; record = reader.read()) {
				switch (record.getRecordCode()) {
				case '1':
					// 初期化処理
					serviceCodeList.clear();
					registerPhoneNumList.clear();
					invoice.clear();

					//契約者情報取得
					invoice.setOwnerTelNumber(record.getOwnerTelNumber());
					break;

				case '2':
					//サービスコード取得し、Listに保存
					String serviceCode = record.getServiceCode();
					serviceCodeList.add(serviceCode);

					// ServiceCodeがC1の場合は、登録電話番号を取得し、Listに保存
					if (serviceCode.equals("C1")) {
						String registerPhoneNum = record.getServiceOption();
						registerPhoneNumList.add(registerPhoneNum);
					}
					break;

				case '5':
					invoice.addCallCharge(calcUnitPrice(serviceCodeList, registerPhoneNumList, record) * record.getCallMinutes());
					break;

				case '9':
					//区切り線を"======="取得
					String separeteLine = "*************************";

					//サービスコードに応じて、基本料金を算出
					calcBasicCharge(invoice,serviceCodeList);
					System.out.print(invoice.getOwnerTelNumber() + "\n");
					System.out.print("5 " + invoice.getBasicCharge() + "\n");
					System.out.print("7 " + invoice.getCallCharge() + "\n");
					System.out.print(separeteLine + "\n");
//					writer.write(constractorPhoneNum + "\n");
//					writer.write("5 " + basicFee + "\n");
//					writer.write("7 " + totalTelephoneFee + "\n");
//					writer.write(separeteLine + "\n");
					break;
				}
			}
			writer.flush();
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

	public static int calcUnitPrice(ArrayList<String> serviceCodeList, ArrayList<String> registerPhoneNumList, Record record) {
		int minuteFee = 20;
		if (serviceCodeList.contains("E1") &&  8 <= record.getStartHour() && record.getStartHour() <=17) {
			minuteFee -= 5;
		}
		if (registerPhoneNumList.contains(record.getCallNumber())) {
			minuteFee = minuteFee/2;
		}
		return minuteFee;
	}

	private static void calcBasicCharge(Invoice invoice, ArrayList<String> serviceCodeList) {
		if (serviceCodeList.contains("C1")) {
			invoice.addBasicCharge(100);
		}
		if (serviceCodeList.contains("E1")) {
			invoice.addBasicCharge(200);
		}
		invoice.addBasicCharge(1000);
	}

}