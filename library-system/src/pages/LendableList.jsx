import React from 'react';
import LendBook from './lendBook';

function LendableList({lendableBookList, checkedBookList, handleBookCheck}) {
    return (
        <>
            <div className='lendableList'>
                {
                    lendableBookList?.map((lendableBook, index) => (
                        <LendBook key={index} 
                                lendBook={lendableBook}
                                isChecked={checkedBookList.includes(lendableBook)}
                                onCheck={handleBookCheck}
                        />
                    ))
                }
            </div>
        </>
    );
}

export default LendableList;