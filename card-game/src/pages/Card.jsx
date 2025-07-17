import React from 'react';
import '../assets/css/card.css';

function Card({number, isChecked, onCheck}) {
    return (
        <>
            <div className='card'>
                <div className='checkbox-div'>
                    {/* 체크박스에 isChecked로 체크 여부 확인, 체크 상태 변할때마다 이벤트 onCheck실행, 실행 시 number값 넘겨줌 */}
                    <input type="checkbox" checked={isChecked} onChange={() => onCheck(number)}/>   
                </div>
                <div className='number-div'>
                    <span>{number}</span>
                </div>
            </div>
        </>
    );
}

export default Card;