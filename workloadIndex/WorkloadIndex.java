package workloadIndex;

import java.util.*;

public class WorkloadIndex {
	
	public static long solution(int n, int[] works) {
        
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder()); //우선순위 큐 내림차순으로 생성

        
        for (int w : works) {
            pq.offer(w); //큐에 데이터 삽입
        }

        // n번 동안 가장 큰 값 줄이기
        for (int i = 0; i < n; i++) {
            int max = pq.poll();   // 가장 큰 값 꺼내기, poll -> 맨앞 값 반환 및 큐에서 삭제
            if (max == 0) return 0;   // 최대가 0이면 모두0 -> 리턴0
            pq.offer(max - 1);     // 줄인 값 다시 삽입
        }

        
        long result = 0; 			// 값 계산 -> 모두 꺼내서 제곱 더하기
        while (!pq.isEmpty()) {		// 큐 전부 순회 -> poll 사용하여 반환 후 삭제하여 전부 순회시 isEmpty true로 루프 탈출
            int num = pq.poll();	
            result += (long) num * num;
        }

        return result;
    }

	public static void main(String[] args) {
		
		int[] works1 = {4,3,3};
		int n1 = 4;
		System.out.println("result1 : " + solution(n1, works1));
		
		int[] works2 = {2,1,2};
		int n2 = 1;
		System.out.println("result2 : " + solution(n2, works2));
		
		int[] works3 = {1,1};
		int n3 = 3;
		System.out.println("result3 : " + solution(n3, works3));

	}

}
