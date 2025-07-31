package parking_fee;


public class ParkingMain {

	public static void main(String[] args) {
		
		int[] fees = {180, 5000, 10, 600};
		String[] records = {"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", 
				"07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT",
				"22:59 5961 IN", "23:00 5961 OUT"};
		int[] fees1 = {120,0,60,591};
		String[] records1 = {"16:00 3961 IN","16:00 0202 IN","18:00 3961 OUT",
				"18:00 0202 OUT","23:58 3961 IN"};
		int[] fees2 = {1,461,1,10};
		String[] records2 = {"00:00 1234 IN"};
		
		Parking p = new Parking(fees, records);
		p.solution(fees, records);
		
		Parking p1 = new Parking(fees1, records1);
		p1.solution(fees1, records1);
		
		Parking p2 = new Parking(fees2, records2);
		p2.solution(fees2, records2);
	}
		
}
	


