import React from 'react';
import styled from 'styled-components';

const ButtonDiv = styled.button`                                       //버튼 스타일 지정
    width: 60px;
    height: 34px;
    background-color: ${(props) => props.$bgColor || 'red'};
    color: ${(props) => props.disabled ? 'red' : 'white'};
    text-align: center;
`

function Button({bgColor, onClick, isStart, text}) {

    return (            //직접 커스텀한 버튼
        <>              {/* 부모로 받아온 값으로 색상, 이벤트, disabled 여부 처리 */}
            <ButtonDiv $bgColor={bgColor} onClick={onClick} disabled={isStart}>{text}</ButtonDiv>
        </>
    );
}

export default Button;