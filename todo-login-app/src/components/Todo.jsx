import React, { useEffect } from 'react';
import '../assets/css/todo.css';
function Todo({todo,checkedList,setCheckedList,  completedList, setCompletedList, handleTodoCheck, todoList, setTodoList}) {

    const isCompleted = todo.status === 'completed';

    const completeEvt = () => {
        if (!isCompleted) {
            const updatedList = todoList.map(item => 
                item === todo ? { ...item, status: 'completed' } : item
            );
            setTodoList(updatedList);
        }
        setCheckedList([]);
    };
    
    const deleteEvt = (delTodo) => {
        const isConfirm = confirm('정말로 삭제하시겠습니까?');
        if(isConfirm) {
            const newTodoList = todoList.filter(todo => todo !== delTodo);
            setTodoList(newTodoList);
        }
        else {
            return false;
        }
    }

    return (
        <div className='todo' style={{backgroundColor : isCompleted ? '#bbbbbb' : 'white'}}>
            <input type='checkbox' 
                    className='me-5'
                    checked={checkedList.includes(todo)}
                    onChange={() => handleTodoCheck(todo)}/>
            <span className='ms-5'
                    style={{textDecoration : isCompleted 
                    ? 'line-through' 
                    : 'none', }}>{todo.todoText}</span>
            <button type='button' 
                    className='btn btn-primary'
                    disabled={todo.status === 'completed'}
                    onClick={completeEvt}>완료</button>
            <button type='button' 
                    className='btn btn-danger ms-3'
                    onClick={() => deleteEvt(todo)}>삭제</button>
        </div>
    );
}

export default Todo;