import React from 'react';
import LendBook from './lendBook';
function BookList({lendedBookList, checkedBookList, handleBookCheck}) {
    return (
        <>
            <div className='bookList'>
                    {
                        lendedBookList?.map((lendBook, index) => (
                            <LendBook key={index} 
                                    lendBook={lendBook}
                                    isChecked={checkedBookList.includes(lendBook)}
                                    onCheck = {handleBookCheck}
                            />
                        ))
                    }
            </div>
        </>
    );
}

export default BookList;