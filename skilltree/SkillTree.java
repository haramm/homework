package skilltree;

import java.util.ArrayList;
import java.util.List;


public class SkillTree {
	
	public static int solution(String skill, String[] skill_trees) {
		
		int result = 0;												//결과값 대입할 변수
		for(String skill_tree : skill_trees) {						//검사할 스킬트리 순회
			List<String> filteredTree = new ArrayList<String>();	//skill내에 있는 문자열만 담을 배열
			String[] splitedTree = skill_tree.split("");			//스킬트리 내 하나의 스킬트리를 한문자 단위로 분리하여 배열에 담기
			for(int i = 0; i < splitedTree.length; i++) {			//분리된 길이만큼 반복
				if(skill.indexOf(splitedTree[i]) != -1) {			//해당 문자열이 스킬 내에 있는 문자열이면
					filteredTree.add(splitedTree[i]);				//필터 배열에 담기
				}
			}
			List<Integer> indexArr = new ArrayList<Integer>();		//필터 배열에 들어있는 문자열이 skill내에 몇번 인덱스에 있는지를 담는 배열
			for(String s : filteredTree) {							//필터 배열 순회
				indexArr.add(skill.indexOf(s));						//해당 문자열이 skill 내 몇번 인덱스에 있는지 값을 대입
			}
			boolean isValid = true;									//스킬트리 타당 여부 
			for(int i = 0; i < indexArr.size(); i++) {				//인덱스 배열 길이만큼 반복
				if(indexArr.get(i) != i) {							//해당 스킬트리가 타당하려면 무조건 0부터 0,1,2,3 순서대로 가야됨
																	//따라서 무조건 i번째 값은 i여야함
					isValid = false;								//i번째 값이 i가 아니면 타당여부 false
					break;											//더이상 검사할 필요x break
				}
			}
			if(isValid) {											//인덱스 배열 전부 검사 통과 시 result + 1
				result++;
			}
		}
		return result;
	}
	public static void main(String[] args) {
		String skill = "CBD";
		String[] skill_trees = {"BACDE", "CBADF", "AECB", "BDA"};
		int result = solution(skill, skill_trees);
		System.out.println("result = " + result);

	}
	

}
