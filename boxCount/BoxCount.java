package boxCount;

public class BoxCount {
	
public static int solution(int n, int w, int num) {
        
        int maxLayerCount = n % w == 0 ? n / w : n / w + 1;		//최고 높이 층수, 나머지 0이면 n/w, 아니면 n/w+1층
		int numLayerCount = num % w == 0 ? num / w : num / w + 1;  //지정된 번호의 층수 나머지 0이면 num/w, 아니면 num/w+1
		int result = 0;
		
		if(numLayerCount % 2 == 0) {					//번호의 층수가 1층,3층,5층....
			if(maxLayerCount % 2 == 0) {				//최고 높이 층수도 1층,3층,5층...
				if((n % w) >= (num % w)) {				//n과 num의 나머지를 비교하여 num위에 최고높이 층수가 있는지 확인
					result = maxLayerCount - numLayerCount + 1;		//있으면 두 층수 뺀 값에 +1
				}
				else { 												//없으면 드 층수 뺀 값
					result = maxLayerCount - numLayerCount;
				}
			}
			else {												//최고 높이 층수는 0층,2층,4층,6층...
				if(n % w == 0) {								//n이 길이로 나누어지면 최고 층수 전부 다차있음 계산x
					result = maxLayerCount - numLayerCount + 1;	//두 층수 뺀 값에 +1
				}
				else if((n % w) + (num % w == 0 ? w : num % w)  >= w + 1) {		//n과 num의 나머지의 합이 w+1보다 같거나 크면 num위에 최고 층수 존재,
																				// num이 w로 나누어지면 w로 대체
					result = maxLayerCount - numLayerCount + 1;					//두 층수 뺀 값에 +1
				}
				else {															//두 합이 더 작으면 num 위 최고 층수 없음
					result = maxLayerCount - numLayerCount;
				}
			}
		}
		else {																//번호의 층수가 0층,2층,4층....
			if(maxLayerCount % 2 == 0) {									//최고 층수는 1층,3층,5층...
				if(n % w == 0) {											//n이 길이로 나누어지면 최고 층수 다 차있음 계산x
					result = maxLayerCount - numLayerCount + 1;
				}
				else if((n % w) + (num % w == 0 ? w : num % w) >= w + 1) {	//n의 나머지와 num의 나머지가 w+1보다 크면 최고 층수 존재,
																			// num이 w로 나누어지면 w로 대체
					result = maxLayerCount - numLayerCount + 1;
				}
				else {														//두 합이 더 작으면 num 위 최고 층수 없음
					result = maxLayerCount - numLayerCount;
				}
			}
			else {															//최고 층수도 0층,2층,4층...
				if((n % w) >= (num % w)) {									//n과 num의 나머지 비교하여 최고 층수 여부 확인 n의 나머지가 더 크면 존재
					result = maxLayerCount - numLayerCount + 1;
				}
				else { 						
					result = maxLayerCount - numLayerCount;
				}
			}
		}
		
		return result;
    }

	public static void main(String[] args) {
		
		System.out.println(solution(22, 6, 8));
		System.out.println(solution(13, 3, 6));

	}

}
