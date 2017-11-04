package step7;

public class Invoice {
	private String ownerTelNumber;
	private int basicCharge;
	private int callCharge;

	public Invoice() {
		this.clear();
	}

	public String getOwnerTelNumber() {
		return this.ownerTelNumber;
	}

	public int getBasicCharge() {
		return this.basicCharge;
	}

	public int getCallCharge() {
		return this.callCharge;
	}

	public void setOwnerTelNumber(String ownerTelNumber) {
		this.ownerTelNumber = ownerTelNumber;
	}

	public void setBasicCharge(int basicCharge) {
		this.basicCharge = basicCharge;
	}

	public void setCallCharge(int callCharge) {
		this.callCharge = callCharge;
	}

	public void addCallCharge(int addCallCharge) {
		this.callCharge += addCallCharge;
	}

	public void addBasicCharge(int addBasicCharge) {
		this.basicCharge += addBasicCharge;
	}

	public void clear() {
		ownerTelNumber = null;
		basicCharge = 0;
		callCharge = 0;
	}

}
