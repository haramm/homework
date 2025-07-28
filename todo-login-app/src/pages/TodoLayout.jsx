import React, { useEffect, useMemo, useState } from 'react';
import '../assets/css/todolayout.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import LoginBox from '../components/loginBox';
import Rate from '../components/Rate';
import InputBox from '../components/InputBox';
import ButtonBox from '../components/ButtonBox';
import TodoBox from '../components/TodoBox';
function TodoLayout(props) {

    const [loginedUser, setLoginedUser] = useState('');
    const [userList, setUserList] = useState(['유저1','유저2']);
    const [selectedUser, setSelectedUser] = useState('유저1');
    const [isLogin, setIsLogin] = useState(false);
    const [todoList, setTodoList] = useState([]);
    const [checkedList, setCheckedList] = useState([]);
    const [inputText, setInputText] = useState('');
    const [completedList, setCompletedList] = useState([]);
    

    const userTodos = useMemo(() => {
        return todoList.filter(todo => todo.owner === loginedUser);
    }, [todoList, loginedUser]);

    const completedTodoCount = useMemo(() => {
        return userTodos.filter(todo => todo.status === 'completed').length;
    }, [userTodos]);

    const unCompletedTodoCount = useMemo(() => {
        return userTodos.length - completedTodoCount;
    }, [userTodos, completedTodoCount]);

    const donePercent = useMemo(() => {
        const total = completedTodoCount + unCompletedTodoCount;
        return total === 0 ? '0' : ((completedTodoCount / total) * 100).toFixed(2);
    }, [completedTodoCount, unCompletedTodoCount]);

    useEffect(() => {
        if(loginedUser !== '') {
            setIsLogin(true);
        }
        else {
            setIsLogin(false);
        }
    },[loginedUser]);

    const addEvt = () => {
        if(inputText.trim().length === 0) {
            alert('추가할 할 일을 입력해주세요');
            return false;
        }
        setTodoList((prev) => 
            [...prev, {todoText : inputText, status : 'uncompleted', owner : loginedUser}]);
    }

    const handleTodoCheck = (checkedTodo) => {
        if(checkedList.includes(checkedTodo)) {
            setCheckedList(checkedList.filter(todo => todo !== checkedTodo));
        }
        else {
            setCheckedList([...checkedList, checkedTodo]);
        }
    }

    const completeAllEvt = () => {
        const newCompletedList = todoList?.map(todo => {
            if(checkedList.includes(todo)) {
                return {todoText : todo.todoText, status : 'completed', owner : loginedUser};
            }
            else {
                return todo;
            }
        })
        setTodoList(newCompletedList);
        setCheckedList([]);
    }

    const deleteAllEvt = () => {
        if(checkedList.length === 0) {
            alert('체크된 항목이 없습니다.');
            return false;
        }
        const isConfirm = confirm('정말로 삭제하겠습니까?');
        if(!isConfirm) return false;

        const newTodoList = todoList?.filter(todo => !checkedList.includes(todo));
        setTodoList(newTodoList);
        setCheckedList([]);
    }

    return (
        <div className='container'>
            <LoginBox selectedUser={selectedUser}
                        setSelectedUser={setSelectedUser}
                        isLogin={isLogin}
                        userList={userList}
                        loginedUser={loginedUser}
                        setLoginedUser={setLoginedUser}
            />
            <Rate unCompletedTodoCount={unCompletedTodoCount}
                    completedTodoCount={completedTodoCount}
                    donePercent={donePercent}/>
            <InputBox isLogin={isLogin}
                        addEvt={addEvt}
                        inputText={inputText}
                        setInputText={setInputText}/>
            <ButtonBox isLogin={isLogin}
                        checkedList={checkedList}
                        completeAllEvt={completeAllEvt}
                        deleteAllEvt={deleteAllEvt}/>
            <TodoBox todoList={todoList}
                        loginedUser={loginedUser}
                        checkedList={checkedList}
                        setCheckedList={setCheckedList}
                        handleTodoCheck={handleTodoCheck}
                        completedList={completedList}
                        setCompletedList={setCompletedList}
                        setTodoList={setTodoList}/>
        </div>
    );
}

export default TodoLayout;