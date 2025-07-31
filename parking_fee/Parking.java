package parking_fee;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parking {
	
	private int[] fees;
	private String[] records;
	
	public Parking(int[] fees, String[] records) {
		
		this.fees = fees;
		this.records = records;
	}
	
	private int getMinutes(String time) {
	    String[] parts = time.split(":");
	    int hour = Integer.parseInt(parts[0]);
	    int minute = Integer.parseInt(parts[1]);
	    return hour * 60 + minute;
	}
	
	private int[] getFee(List<ArrayList<Integer>> minLst, int[] fees, String[] records) {
		
		int[] result = new int[minLst.size()];
		List<ArrayList<Integer>> feeLst = new ArrayList<ArrayList<Integer>>();
		List<ArrayList<Integer>> feeLstCalc = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < minLst.size(); i++) {
			feeLst.add(new ArrayList<Integer>());
			feeLstCalc.add(new ArrayList<Integer>());
			
		}
		
		for(int i = 0; i < minLst.size(); i++) {
			for(int j = 0; j < minLst.get(i).size(); j++) {
				if(j % 2 == 1) {
					int diff = minLst.get(i).get(j) - minLst.get(i).get(j - 1);
					feeLst.get(i).add(diff);
				}
				else if(j % 2 == 0 && j == minLst.get(i).size() - 1) {
					int diff = 1439 - minLst.get(i).get(j);
					feeLst.get(i).add(diff);
				}
			}
		}
		int[] resultSum = new int[minLst.size()];
		
		for(int i = 0; i < feeLst.size(); i++) {
			for(int j = 0; j < feeLst.get(i).size(); j++) {
				resultSum[i] += feeLst.get(i).get(j);
			}
		}
	
		
		for(int i = 0; i < resultSum.length; i++) {
			if(resultSum[i] < fees[0]) {
				resultSum[i] = fees[1];
			}
			else {
				int a = (int) Math.ceil((double)(resultSum[i] - fees[0]) / fees[2]);
				int calcFee = fees[1] + a * fees[3];
				resultSum[i] = calcFee;
			}
		}
		
		return resultSum;
	}
	
	public int[] solution(int[] fees, String[] records) {
        int[] answer = {};
        
        
        
        Set<String> carListSet = new HashSet<String>();
        
        for(int i = 0; i< records.length; i++) {
        	String[] splitedRecords = records[i].split(" ");
        	carListSet.add(splitedRecords[1]);
        }
        
        List<String> carList = new ArrayList<String>(carListSet);
        Collections.sort(carList);
        List<ArrayList<String>> resultLst = new ArrayList<ArrayList<String>>();
        List<ArrayList<Integer>> minLst = new ArrayList<ArrayList<Integer>>();
        
        for(int i = 0; i < carList.size(); i++) {
        	resultLst.add(new ArrayList<String>());
        	minLst.add(new ArrayList<Integer>());
        }
        
        for(int i = 0; i < records.length; i++) {
        	String[] splitedRecords = records[i].split(" ");
        	for(int j = 0; j < carList.size(); j++) {
        		if(splitedRecords[1].equals(carList.get(j))) {
        			resultLst.get(j).add(splitedRecords[0]);
        		}
        	}
        }
        
        
        for(int i = 0; i < resultLst.size(); i++) {
        	for(int j = 0; j < resultLst.get(i).size(); j++) {
        		int minutes = getMinutes(resultLst.get(i).get(j));
                minLst.get(i).add(minutes);
        		
        	}
        }
                  
        answer = getFee(minLst, fees, records);
        
        for(int i = 0; i < answer.length; i++) {
        	System.out.println((i+1) + "번째 차량 요금 : " + answer[i]);
        }
        
        
        
        
        return answer;
    }
}
