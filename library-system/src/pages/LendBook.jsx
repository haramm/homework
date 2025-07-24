import React from 'react';
import '../assets/css/lendBook.css';

function LendBook({lendBook, isChecked, onCheck}) {
    return (
        <div className='lendBookDiv'>
            <input type='checkbox' 
                checked={isChecked}
                onChange={() => onCheck(lendBook)}
            />
            {lendBook.bookName}
        </div>
    );
}

export default LendBook;