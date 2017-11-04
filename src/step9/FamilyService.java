package step9;

import java.util.ArrayList;

public class FamilyService implements Service {
	private static final int TEL_NUMBER= 2;
	private static final String SERVICE_CODE = "C1";
	private static final int BASIC_CHARGE = 100;

	ArrayList <String> serviceCodeList = new ArrayList();
	ArrayList <String> registerPhoneNumList = new ArrayList(TEL_NUMBER);

	public boolean isJoined() {
		if (this.serviceCodeList.contains(SERVICE_CODE)) {
			return true;
		}
		return false;
	}

	public void checkService(Record record) {
		serviceCodeList.add(record.getServiceCode());
		if (isJoined() == true) {
			registerPhoneNumList.add(record.getServiceOption());
		}
	}
	public void clear() {
		serviceCodeList.clear();
		registerPhoneNumList.clear();
	}

	public boolean isFamilyTelNumber(String phoneNum) {
		if (registerPhoneNumList.contains(phoneNum)) {
			return true;
		}
		return false;
	}

	public int calcUnitPrice(Record record, int unit_price) {
		if (isFamilyTelNumber(record.getCallNumber()) == true) {
			unit_price = unit_price/2;
		}
		return unit_price;
	}

	public int calcBasicCharge(int basic_charge) {
		if (isJoined() == true) {
			basic_charge += BASIC_CHARGE;
		}
		return basic_charge;
	}

}
