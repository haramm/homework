import React from 'react';

function ButtonBox({isLogin, checkedList, completeAllEvt, deleteAllEvt}) {
    return (
        <>
            <div className='btn-box'>
                <button type='button' 
                        className='btn btn-secondary'
                        disabled={!isLogin || checkedList.length === 0}
                        onClick={completeAllEvt}>일괄 완료</button>
                <button type='button' 
                        className='btn btn-danger ms-3'
                        disabled={!isLogin || checkedList.length === 0}
                        onClick={deleteAllEvt}>일괄 삭제</button>
            </div>
        </>
    );
}

export default ButtonBox;