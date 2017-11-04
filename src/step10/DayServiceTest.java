package step10;

import static org.junit.Assert.*;

import org.junit.Test;

public class DayServiceTest {

	@Test
	public void testCheckService() {
		DayService dayService = new DayService();

		// インスタンスを作成した直後は、加入フラグはfalse
		assertFalse(dayService.isJoined());

		// 昼トク割引意外のレコードの場合、加入フラグはfalseのまま
		dayService.checkService(new Record("2 C1 090-1234-0001"));
		assertFalse(dayService.isJoined());

		// 昼トク割引のレコードの場合、加入フラグがtrueになる
		dayService.checkService(new Record("2 E1"));
		assertTrue(dayService.isJoined());

		// clear()メソッドを呼び出すと、加入フラグがfalseになる
		dayService.clear();
		assertFalse(dayService.isJoined());
	}

	@Test
	public void testIsServiceTime() {
		DayService dayService = new DayService();

		// 昼トク割引に加入していない場合は、どの時間も昼トク割引の対象にならない
		assertFalse(dayService.isServiceTime(7));
		assertFalse(dayService.isServiceTime(8));
		assertFalse(dayService.isServiceTime(17));
		assertFalse(dayService.isServiceTime(18));

		// 昼トク割引に介入している場合は、8:00時から17:59分までに開始された通話は割引対象になる
		dayService.checkService(new Record("2 E1"));
		assertFalse(dayService.isServiceTime(7));
		assertTrue(dayService.isServiceTime(8));
		assertTrue(dayService.isServiceTime(17));
		assertFalse(dayService.isServiceTime(18));
	}
//
	@Test
	public void testCalcUnitPrice() {
		DayService dayService = new DayService();

		dayService.checkService(new Record("2 E1"));

		// 昼トク割引の対象外の時間の場合、単価は変わらない
		int unitPrice = dayService.calcUnitPrice(new Record("5 2004/06/05 18:00 010 090-1234-0002"), 20);
		assertEquals(20, unitPrice);

		// 昼トク割引の対象の時間の場合、単価は5円引き
		unitPrice = dayService.calcUnitPrice(new Record("5 2004/06/05 17:59 010 090-1234-0002"), 20);
		assertEquals(15, unitPrice);
	}

	@Test
	public void testCalcBasicCharge() {
		DayService dayService = new DayService();

		// 昼トク割引に加入していない場合、基本料金は変わらない
		int basicCharge = dayService.calcBasicCharge(1000);
		assertEquals(1000, basicCharge);

		// 昼トク割引に加入している場合、基本料金は200円増し
		dayService.checkService(new Record("2 E1"));
		basicCharge = dayService.calcBasicCharge(1000);
		assertEquals(1200, basicCharge);
	}
}
