import React, { useState } from 'react';
import '../assets/css/lotto.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import LottoList from './LottoList';
function MainLotto(props) {

    const [answerLottoList, setAnswerLottoList] = useState([]);
    const [userLottoList, setUserLottoList] = useState([]);
    const [isCompared, setIsCompared] = useState(false);

    function makeLottoList(count) {
        const lottoList = [];
        let i = 0;
        while(i < count) {
            const randNum = Math.floor((Math.random() * 45) + 1);
            if(lottoList.includes(randNum)) {
                continue;
            }
            lottoList.push(randNum);
            i++;
        }
        return lottoList;
    }

    function makeGrade(lottoList) {
        const answerLottoListExceptBonus = answerLottoList.filter((_,index) => index !== 6);
        const BonusNum = answerLottoList[6];
        const compareList = answerLottoListExceptBonus.filter((num) => lottoList.includes(num));
        if(compareList.length === 6) {
            return '1등';
        }
        else if(compareList.length === 5) {
            if(lottoList.includes(BonusNum)) {
                return '2등';
            }
            else {
                return '3등';
            }
        }
        else if(compareList.length === 4) {
            return '4등';
        }
        else if(compareList.length === 3) {
            return '5등';
        }
        else {
            return '꽝';
        }
    }

    const handleGenerateAnswerLotto = () => {
        setAnswerLottoList(makeLottoList(7));
        setUserLottoList([]);
        setIsCompared(false);
    }

    const handleGenerateUserLotto = () => {
        const userList = [];
        for(let i = 0; i < 5; i++) {
            userList.push(makeLottoList(6));
        }
        setUserLottoList(userList);
        setIsCompared(false);
    }

    const compareLotto = () => {
        setIsCompared(true);
    }

    return (
        <div className='main'>
            <section className='lotto-button-box'>
                <button type='button' className='btn btn-primary m-3' onClick={handleGenerateAnswerLotto}>로또생성</button>
                <button type='button' className='btn btn-dark m-3' onClick={handleGenerateUserLotto}>유저로또</button>
                <button type='button' className='btn btn-secondary m-3' onClick={compareLotto}>비교</button>
            </section>
            <section className='answer-lotto-box'>
                <LottoList lottoList={answerLottoList}/>
            </section>
            {
                userLottoList?.map((list,index) => (
                    <section key={index} className='user-lotto-box'>
                        <LottoList lottoList={list}/>
                        {isCompared && <span className='result'>{makeGrade(list)}</span>}
                    </section> 
                ))
            }
        </div>
    );
}

export default MainLotto;