package step10;

public class Record {
	// 各レコードの情報 (Record Information)
	private static final int RI_OF_OWNER_TEL_NUMBER = 2;
	private static final int RI_OF_SERVICE_CODE = 2;
	private static final int RI_SZ_SERVICE_CODE = 2;
	private static final int RI_OF_SERVICE_OPTION = 5;

	private static final int RI_OF_CALL_START_TIME = 13;
	private static final int RI_SZ_HOUR = 2;

	private static final int RI_OF_CALL_MINUTE = 19;
	private static final int RI_SZ_CALL_MINUTE = 3;

	private static final int RI_OF_CALL_NUMBER = 23;

	private String record;

	public Record(String record) {
		this.record = record;
	}

	public String toString() {
		return record;
	}

	public char getRecordCode() {
		return record.charAt(0);
	}

	public String getOwnerTelNumber() {
		return record.substring(RI_OF_OWNER_TEL_NUMBER);
	}

	public String getServiceCode() {
		return record.substring(RI_OF_SERVICE_CODE, RI_OF_SERVICE_CODE + RI_SZ_SERVICE_CODE);
	}

	public String getServiceOption() {
		if (record.length() < RI_OF_SERVICE_OPTION) {
			return null;
		}
		return record.substring(RI_OF_SERVICE_OPTION);
	}

	public int getStartHour() {
		return Integer.parseInt(record.substring(RI_OF_CALL_START_TIME, RI_OF_CALL_START_TIME + RI_SZ_HOUR));
	}

	public int getCallMinutes() {
		return Integer.parseInt(record.substring(RI_OF_CALL_MINUTE, RI_OF_CALL_MINUTE + RI_SZ_CALL_MINUTE));
	}

	public String getCallNumber() {
		return record.substring(RI_OF_CALL_NUMBER);
	}

}
