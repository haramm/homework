import React, { useState } from 'react';

function InputBox({isLogin, addEvt, inputText, setInputText}) {

    return (
        <>
            <div className='input-box'>
                <input type='text' 
                        id="input-text" 
                        className='form-control w-75'
                        value={inputText}
                        onChange={(e) => setInputText(e.target.value)}/>
                <button type='button' 
                        className='btn btn-primary'
                        disabled={!isLogin}
                        onClick={addEvt}>등록</button>
            </div>
        </>
    );
}

export default InputBox;