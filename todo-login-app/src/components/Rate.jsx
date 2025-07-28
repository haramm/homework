import React from 'react';

function Rate({unCompletedTodoCount, completedTodoCount, donePercent}) {
    return (
        <>
            <div className='text-box text-end mt-5'>
                <span>할 일: {unCompletedTodoCount}건</span><br/><br/>
                <span>한 일: {completedTodoCount}건</span><br/><br/>
                <span>달성률: {donePercent}%</span><br/>
            </div>
        </>
    );
}

export default Rate;