package maxNumber;

import java.util.Arrays;

public class maxNumber {
    public static String solution(int[] numbers) {

        String[] numStrs = new String[numbers.length]; //input받은 int배열 string으로 변환하여 저장
        for (int i = 0; i < numbers.length; i++) {
            numStrs[i] = String.valueOf(numbers[i]);
        }


        Arrays.sort(numStrs, (a, b) -> {  //두 문자 어느방향으로 붙여야 더 큰지 커스텀 비교
            String ab = a + b;
            String ba = b + a;
            return ba.compareTo(ab);
        });


        StringBuilder sb = new StringBuilder();
        for (String num : numStrs) {               //정렬된 문자열 이어붙이기
            sb.append(num);
        }


        if (sb.charAt(0) == '0') {      //결과가 0으로 시작하는 경우 처리
                                        //모든 값이 0이면 000...이 될 수 있으므로 0 반환
            return "0";
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println(solution(new int[]{6, 10, 2}));
        System.out.println(solution(new int[]{3, 30, 34, 5, 9}));
        System.out.println(solution(new int[]{0, 0, 0}));
    }
}
