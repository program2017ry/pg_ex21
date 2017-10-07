package ex21;

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
			BufferedWriter writer = new BufferedWriter(new FileWriter("writerFileName"));
			BufferedReader reader = new BufferedReader(new FileReader(readFileName));
			int basicFee = 1000;	// 基本料金(月)
			int minuteFee = 20;	//通話料金(分)
			int totalTelephoneFee = 0;
			String line;	//行を受け取る変数
			String userInfo = "";	//契約者情報
			ArrayList <String> serviceCode = new ArrayList();	// 加入サービス
			ArrayList <String> registerPhone = new ArrayList();	//C1の場合の登録電話番号

			while ((line = reader.readLine()) != null) {
				switch (line.charAt(0)) {
				case '1':
					serviceCode.clear();
					registerPhone.clear();
					basicFee = 1000;
					minuteFee = 20;
					totalTelephoneFee = 0;

					userInfo = line;
					break;

				case '2':
					serviceCode.add(line.substring(2, 4));
					if (line.substring(2, 4).equals("C1")) {
						registerPhone.add(line.substring(5,18));
					}
					break;

				case '5':
					// E1で日勤帯の通話料は、5円引き/分
					int i = Integer.parseInt(line.substring(13,15));
					if (serviceCode.contains("E1") && i >= 8 && i <=17)  {
						minuteFee -= 5;
					}

					// 登録先電話番号は通話半額
					if (registerPhone.contains(line.substring(23,36))) {
						minuteFee = minuteFee/2;
					}

					// 通話料算出
					int telephoneFee = minuteFee * Integer.parseInt(line.substring(19,22));
					totalTelephoneFee += telephoneFee;

					minuteFee = 20;
					break;

				case '9':
					String separeteLine = line;
					if (serviceCode.contains("C1")) {
						basicFee += 100;
					}
					if (serviceCode.contains("E1")) {
						basicFee += 200;
					}
					writer.write(userInfo + "\n");
					writer.write("5 " + basicFee + "\n");
					writer.write("7 " + totalTelephoneFee + "\n");
					writer.write(separeteLine + "\n");
					break;
				}
				writer.flush();
				reader.close();
				writer.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
//		} finally {
//			writer.close();
		}

		}
}