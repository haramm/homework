package giftCount;



public class GiftCount {

	public static void main(String[] args) {
		
		String[] friends = {"muzi", "ryan", "frodo", "neo"};
		String[] gifts = {"muzi frodo", "muzi frodo", "ryan muzi", 
							"ryan muzi", "ryan muzi", "frodo muzi", 
							"frodo ryan", "neo muzi"};
		solution(friends, gifts);
		
		String[] friends1 = {"joy", "brad", "alessandro", "conan", "david"};
		String[] gifts1 = {"alessandro brad", "alessandro joy", 
				"alessandro conan", "david alessandro", "alessandro david"};
		solution(friends1, gifts1);
		
		String[] friends2 = {"a", "b", "c"};
		String[] gifts2 = {"a b", "b a", "c a", "a c", "a c", "c a"};
		solution(friends2, gifts2);
	}
	
	public static int solution(String[] friends, String[] gifts) {
		
		int[][] giveCount = new int[friends.length][friends.length];
		int[][] getCount = new int[friends.length][friends.length];
		int[] predictCount = new int[friends.length];
		
		for(String gift : gifts) {
			inputGift(gift, friends, giveCount, getCount);
		}
		
		for(int i = 0; i < friends.length; i++) {
			for(int j = 0; j < friends.length; j++) {
				if(i==j) {
					continue;
				}
				if(giveCount[i][j] > giveCount[j][i]) {
					predictCount[i]++;
				}
				else if(giveCount[i][j] < giveCount[j][i]) {
					predictCount[j]++;
				}
				else {
					int sumI = 0;
					int sumJ = 0;
					
					for(int k = 0; k < giveCount.length; k++) {
						sumI += giveCount[i][k];
						sumJ += giveCount[j][k];
						sumI -= getCount[i][k];
						sumJ -= getCount[j][k];
					}
					if(sumI > sumJ) {
						predictCount[i]++;
					}
					else if(sumI < sumJ) {
						predictCount[j]++;
					}
				}
			}
		}
		
		int answer = 0;
		for(int count : predictCount) {
			if(count > answer) {
				answer = count;
			}
		}
		System.out.println("result : " + answer / 2);
		
		
		
		return answer / 2;
	}
	
	private static void inputGift(String gift, String[] friends,int[][] giveCount, int[][] getCount) {
		String[] splitedGift = gift.split(" ");
		int giveIndex = -1;
		int getIndex = -1;

		for(int i = 0; i < friends.length; i++) {
			if(splitedGift[0].equals(friends[i])) {
				giveIndex = i;
			}
			if(splitedGift[1].equals(friends[i])) {
				getIndex = i;
			}
		}
		if (giveIndex != -1 && getIndex != -1) {
			giveCount[giveIndex][getIndex]++;
			getCount[getIndex][giveIndex]++;
		}
	}
}
