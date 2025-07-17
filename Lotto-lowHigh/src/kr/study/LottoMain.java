package kr.study;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class LottoMain {

	public static void main(String[] args) {
		
		int[] inputArr = new int[6];
		int[] answerArr = new int[2];
		Set<Integer> inputSet = new HashSet<Integer>();
		Set<Integer> answerSet = new HashSet<Integer>();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("로또 계산 프로그램");
		
		int i = 0;
		while(i < 6) {
			System.out.print(i + 1 + "번째 숫자 입력 : ");
			int inputNum = sc.nextInt();
			inputSet.add(inputNum);
			i++;
		}
		
		while(answerSet.size() < 6) {
			System.out.print(answerSet.size() + 1 + "번째 정답 입력 : ");
			int inputNum = sc.nextInt();
			answerSet.add(inputNum);
		}
		
		
		Set<Integer> intersection = new HashSet<>(inputSet);
		intersection.retainAll(answerSet);
		Set<Integer> removeZeroInputSet = new HashSet<Integer>(inputSet);
		removeZeroInputSet.remove(0);
		int zeroCount = 6 - removeZeroInputSet.size();
		
		answerArr[0] = intersection.size() + zeroCount;
		answerArr[1] = intersection.size();
		System.out.println("겹치는 번호 갯수 : " + intersection.size());
		System.out.println("0 갯수 : " + zeroCount);
		System.out.print("result: ");
		
		for (Integer j : answerArr) {
			switch(j) {
			case 6 : 
				System.out.print("1 ");
				break;
			case 5 : 
				System.out.print("2 ");
				break;
			case 4 : 
				System.out.print("3 ");
				break;
			case 3 : 
				System.out.print("4 ");
				break;
			case 2 : 
				System.out.print("5 ");
				break;
			default: 
				System.out.print("6(낙첨) ");
				break;
			}
		}
	}
}
