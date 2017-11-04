package step9;

import java.util.ArrayList;

public class DayService implements Service {
	private static final int START_TIME = 8;
	private static final int END_TIME = 17;
	private static final String SERVICE_CODE = "E1";
	private static final int BASIC_CHARGE = 200;

	ArrayList <String> serviceCodeList = new ArrayList();

	public boolean isJoined() {
		if (this.serviceCodeList.contains(SERVICE_CODE)) {
			return true;
		}
		return false;
	}

	// コンストラクタで下記処理を定義していはいけないの？？
	public void checkService(Record record) {
		serviceCodeList.add(record.getServiceCode());
	}

	public void clear() {
		serviceCodeList.clear();
	}

	public boolean isServiceTime(int start_time) {
		if (isJoined() == true && 8 <= start_time && start_time <=17) {
			return true;
		}
		return false;
	}

	public int calcUnitPrice(Record record, int unit_price) {
		if (isServiceTime(record.getStartHour()) == true) {
			return unit_price -= 5;
		}
		return unit_price;
	}

	public int calcBasicCharge(int basic_charge) {
		if (isJoined() == true) {
			return basic_charge + 200;
		}
		return basic_charge;
	}

}
