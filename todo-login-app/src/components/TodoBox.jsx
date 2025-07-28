import React from 'react';
import Todo from './Todo';

function TodoBox({todoList, loginedUser, checkedList, setCheckedList, handleTodoCheck, completedList, setCompletedList, setTodoList}) {
    return (
        <>
            <div className='todo-box'>
                {
                    todoList?.filter(todo => todo.owner === loginedUser).map((todo, index) => (
                        <Todo todo={todo} 
                                key={index}
                                checkedList={checkedList}
                                setCheckedList={setCheckedList}
                                handleTodoCheck={handleTodoCheck}
                                completedList={completedList}
                                setCompletedList={setCompletedList}
                                todoList={todoList}
                                setTodoList={setTodoList}
                                />
                    ))
                }
            </div>
        </>
    );
}

export default TodoBox;