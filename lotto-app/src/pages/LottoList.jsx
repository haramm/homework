import React from 'react';
import '../assets/css/lottoList.css';
function LottoList({lottoList}) {

    return (
        <>
            {
                lottoList?.map((lotto, index) => (
                    <div className='lotto' key={index}>
                        {lotto}
                    </div>
                ))
            }
        </>
    );
}

export default LottoList;