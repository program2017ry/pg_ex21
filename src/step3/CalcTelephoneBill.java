package step3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CalcTelephoneBill {

	public static void main(String[] args){
		String readFileName = "C:\\esm-semi\\ex21\\record.log";
		String writerFileName = "C:\\esm-semi\\ex21\\invoice.dat";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(writerFileName));
			BufferedReader reader = new BufferedReader(new FileReader(readFileName));
			int basicFee = 1000;	// 基本料金(月)
			int minuteFee = 20;	//通話料金(分)
			int totalTelephoneFee = 0;
			String line;	//行を受け取る変数
			String userInfo = "";	//契約者情報
			ArrayList <String> serviceCodeList = new ArrayList();	// 加入サービス
			ArrayList <String> registerPhoneNumList = new ArrayList();	//C1の場合の登録電話番号

			while ((line = reader.readLine()) != null) {
				Record record = new Record(line);
				switch (record.getRecordCode()) {
				case '1':
					// 初期化処理
					serviceCodeList.clear();
					registerPhoneNumList.clear();
					basicFee = 1000;
					minuteFee = 20;
					totalTelephoneFee = 0;

					//契約者情報取得
					userInfo = record.getOwnerTelNumber();
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
					//通話開始時間を取得
					int startTelephoneTime = record.getStartHour();

					// E1でかつ、通話開始時間が日勤帯の場合、通話料は5円引き/分
					if (checkLunchService(serviceCodeList,startTelephoneTime)) {
						minuteFee -= 5;
					}

					// 登録先電話番号への通話半額
					String phoneNum = record.getCallNumber();
					if (registerPhoneNumList.contains(phoneNum)) {
						minuteFee = minuteFee/2;
					}

					// 通話料算出
					int telephoneFee = getTelephoneFee(minuteFee,line);
					totalTelephoneFee += telephoneFee;

					//case5は繰り返す可能性があるので初期化しておく。
					minuteFee = 20;
					break;

				case '9':
					//区切り線を"======="習得
					String separeteLine = line;

					//サービスコードに応じて、基本料金を算出
					calcBasicFee(basicFee,serviceCodeList);
					System.out.println(userInfo + "\n");
					System.out.println("5 " + basicFee + "\n");
					System.out.println("7 " + totalTelephoneFee + "\n");
					System.out.println(separeteLine + "\n");
//					writer.write(userInfo + "\n");
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


	public static String getUserInfo(String line) {
		return  line;
	}

	public static String getServiceCode(String line) {
		return line.substring(2, 4);
	}
	public static String getRegsiterPhoneNum(String line) {
		return line.substring(5,18);
	}
	public static int getStartTelephoneTime(String line) {
		return Integer.parseInt(line.substring(13,15));
	}
	private static boolean checkLunchService(ArrayList<String> serviceCodeList, int startTelephoneTime) {
		if (serviceCodeList.contains("E1") && 8 <= startTelephoneTime && startTelephoneTime <=17) {
			return true;
		}
		return false;
	}

	public static String getPhoneNum(String line) {
		return line.substring(23,36);
	}
	public static int getTelephoneFee(int minuteFee, String line) {
		return minuteFee * Integer.parseInt(line.substring(19,22));
	}
	private static void calcBasicFee(int basicFee, ArrayList<String> serviceCodeList) {
		if (serviceCodeList.contains("C1")) {
			basicFee += 100;
		}
		if (serviceCodeList.contains("E1")) {
			basicFee += 200;
		}
	}

}