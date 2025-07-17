import React, { useState } from 'react';
import Button from './Button.jsx';
import Card from './Card.jsx';
import '../assets/css/CardBoard.css';

function CardBoard(props) {             

    const [randNumList, setRandNumList] = useState([]);             //유저 5장 카드 생성 시 사용할 배열
    const [selectNumList, setSelectNumList] = useState([]);         //유저 2장 선택 시 입력 받을 배열
    const [pcNumList, setPcNumList] = useState([]);                 //pc 자동 2장 생성 번호 입력 받을 배열
    const [userSum, setUserSum] = useState(0);                      //유저 선택 카드 합 입력받을 변수
    const [pcSum, setPcSum] = useState(0);                          //pc 카드 2장 합 입력받을 변수
    const [isStart, setIsStart] = useState(false);                  //게임이 시작되었는지 확인할 불리언값
    

    function makeRandNum() {
        return Math.floor((Math.random() * 20) + 1);              //함수 실행 시 1~20 사이 숫자 1개 반환
    }

    function createCard() {                         // 화면에 보일 카드 5개 생성 함수
        let numLst = [];                            // 랜덤번호 입력받을 배열 선언

        for (let i = 0; i < 5;) {                   // 5번 반복, i++은 밑에서 컨트롤하여 반복 지정
            let randNum = makeRandNum();            // 1~20 사이 랜덤 번호 생성
            if (!numLst.includes(randNum)) {        // 배열안에 랜덤번호가 존재하지 않는다면 -> 중복되지 않는다면
                numLst.push(randNum);               // 배열에 랜덤번호 삽입
                i++;                                // 삽입했으면 i 1증가
            }
                                                    // 중복되어 위 if문 미실행 시 i 증가시키지않고 재반복
        }
        setRandNumList(numLst);                     //위 for문 작동 후 랜덤번호 받은 배열을 randNumList에 대입
    }

    function createPcCard() {                       //pc 번호 2개 생성 함수
        let numLst = [];                            // pc 번호 2개 입력받을 배열 선언

        while (numLst.length < 2) {                 // 배열 길이가 2가 되기 전까지 반복
            let randNum = makeRandNum();            // 랜덤 번호 1~20 사이에서 생성
            if(!numLst.includes(randNum)) {         // 랜덤번호가 배열안에 있는 변수와 겹치지 않으면
                numLst.push(randNum);               // 배열에 랜덤번호 삽입
            }
        }
        setPcNumList(numLst);                       //pcNumList에 배열 삽입
    }

    function handleCardCheck(num) {                                 //카드 체크 관리하는 함수
        
        if (selectNumList.includes(num)) {                          // 선택한 값을 담은 배열에 num이 이미 있다면 -> 이미 체크되어있다면
            setSelectNumList(selectNumList.filter(n => n !== num)); // 선택한 값을 담은 배열에 filter 사용하여 num만 빼서 새로운 배열 생성하여 삽입
        } else {                                                    // => 이미 체크되어있으면 선택 해제
            setSelectNumList([...selectNumList, num]);              // 선택이 안되어있으면 선택
        }
    }

    const onClickStartBtn = () => {         //시작 버튼 클릭 시 실행되는 함수
        setIsStart(true);                   //게임 시작 여부 체크값 true로 전환
        setSelectNumList([]);               //유저 선택한 카드 배열 초기화
        setUserSum([]);                     //유저 선택한 카드 합 초기화
        setPcSum([]);                       //pc 카드 번호 합 초기화
        createCard();                       // 5개 카드 생성
        createPcCard();                     // pc 카드 2개 선택
        
        
    }

    const onClickSelectBtn = () => {        //선택 버튼 클릭 시 실행되는 수
        
        if(selectNumList.length !== 2) {       //선택한 카드가 2장이 아니면
            alert('카드 2장을 선택해주세요');   //경고 알림 출력
            return;                            //조기 종료
        }
        
        let userSum = selectNumList.reduce((sum, num) => Number(sum) + Number(num));    //reduce 사용하여 선택한 카드 5개 합 대입
        let pcSum = pcNumList.reduce((sum, num) => Number(sum) + Number(num));          //reduce 사용하여 선택한 카드 2개 합 대입

        setUserSum(userSum);            //userSum 대입
        setPcSum(pcSum);                //pcSum 대입
        setIsStart(false);              //선택 끝났으니 -> 게임 끝났으니 게임 시작 여부 false로 전환
    }

    const onClickResetBtn = () => {    //리셋 버튼 클릭 시 실행되는 함수
        setRandNumList([]);            //5개 랜덤번호 입력 배열 초기화
        setPcNumList([]);              //pc 2개 랜덤번호 초기화
        setPcSum([]);                  //pc 랜덤번호 합 초기화
        setUserSum([]);                //유저 랜덤번호 합 초기화
        setSelectNumList([]);          //유저가 선택한 번호 입력받은 배열 초기화
    }

    return (
        <>
            <div className='container'>
                <div className='card-div'>
                    {
                        randNumList.map((randNum, index) => (               //랜덤번호 5개 사용하여 Card 5개 생성
                            <Card 
                            key={index}                                     //map에 key 인덱스로 부여
                            number={randNum}                                //번호에 랜덤 생성 번호 부여
                            isChecked={selectNumList.includes(randNum)}     //선택한 번호 담는 배열에 랜덤번호 있으면 체크
                            onCheck={handleCardCheck} />                    //체크버튼 클릭 시 handleCardCheck 함수 실행
                        ))
                    }
                </div>
                <div className='bottom-div'>
                    <div className='text-div'>
                        <p>유저 선택 카드 : {selectNumList.map((obj) => obj + ' ')}</p>     {/* 선택한 값 담는 배열 안에 있는 값 + " " 출력 */}
                        <p>pc 선택 카드 : {pcNumList.map((obj) => obj + ' ')}</p>           {/* pc 번호 입력받은 배열 내 값 + " " 출력 */}
                        <p>
                            {userSum > pcSum && '유저 승리'}                            {/* 유저 선택한 번호 합이 pc 번호 합보다 크면 유저 승리 출력 */}
                            {userSum < pcSum && 'pc 승리'}                              {/* 유저 선택한 번호 합이 pc 번호 합보다 작으면 pc 승리 출력 */}
                            {userSum === pcSum && userSum === 0 && ''}                  {/* 유저 합과 pc 합이 같으면서 둘다 0이면 시작 전이라 출력x */}
                            {userSum === pcSum && userSum !== 0 && '무승부'}            {/* 유저 합과 pc 합이 같으면서 0이 아니면 무승부 출력 */}
                        </p>
                    </div>
                    <div className='btn-txt'>
                        <Button bgColor='green' onClick={onClickStartBtn} isStart={isStart} text='시작'></Button>   {/* 클릭 이벤트에 onClickStartBtn 시작여부 값에 isStart 대입 */}
                        <Button bgColor='yellow' onClick={onClickSelectBtn} text='선택'></Button>           {/* 클릭 이벤트에 onClickSelectBtn 대입 */}
                        <Button bgColor='red' onClick={onClickResetBtn} text='리셋'></Button>               {/* 클릭 이벤트에 onClickResetBtn 대입 */}
                    </div>
                </div>
            </div>
        </>
    );
}

export default CardBoard;