package fruitcount;

import java.util.Arrays;

public class fruitCount {
	
	public static int solution(int k, int m, int[] score) {
		
		
		//최대 이익 계산 알고리즘
		//몇개를 넣든 최저 점수의 사과로 계산되어
		//무조건 제일 높은 점수의 사과를 몰아서 넣는게 이득
		//정렬하여 높은 점수부터 m갯수만큼 짤라 사과 박스 생성 -> 최대 이익
		
		Arrays.sort(score);  //score 정렬
		
		int lstCount = score.length / m;     // 사과박스 몇개 만들지 계산
		int result = 0;						
		for(int i = 0; i < lstCount; i++) {	 // 만들 사과박스 갯수만큼 반복
			int[] temp = new int[m];	     // 사과박스 담을 배열
			int index = (score.length - (i * m) - 1);		//뒤에서부터 m개씩 짤라넣을수 있게 인덱스 생성 -> 매 반복마다 m개씩 줄어든다
			int tempIndex = 0;								//사과박스 담을 배열 인덱스 생성
			for(int j = index; j >= index - m + 1; j--) {	//계산한 인덱스부터 m개만큼 역순으로 잘라서 박스에 담기
				temp[tempIndex++] = score[j];				//사과박스에 담기
			}
			result += getPrice(temp);						//사과박스 만들때마다 해당 사과박스 점수 result 에 더하기
		}
		
		
		
		return result;
	}
	
	public static int getPrice(int[] score) {				//사과박스당 점수 계산 함수
		
		int min = score[0];						// 최저 점수 일단 처음 값으로 저장
		for(int num : score) {					// 사과박스 반복하여 최저 점수값 min에 대입
			if(num < min) {
				min = num;
			}
		}
		return min * score.length;				// 최저 점수에 사과박스 갯수 곱하여 사과박스 가격 반환
	}

	public static void main(String[] args) {
		
		int k1 = 3;
		int m1 = 4;
		int[] score1 = {1,2,3,1,2,3,1};
		int result1 = solution(k1, m1, score1);
		System.out.println("result = " + result1);
		
		int k2 = 4;
		int m2 = 3;
		int[] score2 = {4,1,2,2,4,4,4,4,1,2,4,2};
		int result2 = solution(k2, m2, score2);
		System.out.println("result = " + result2);
	}

}
