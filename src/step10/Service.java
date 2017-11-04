package step10;

public interface Service {
	   // 変数を初期化する
	   void clear();

	   // 割引サービスに加入しているかを検査する
	   void checkService(Record record);

	   // 単価を計算する
	   int calcUnitPrice(Record record, int unitPrice);

	   // 基本料金を計算する
	   int calcBasicCharge(int basicCharge);

}
