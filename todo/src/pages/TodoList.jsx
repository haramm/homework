import React, { useState } from 'react';
import '../assets/css/todoList.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import TodoTemplate from './TodoTemplate';

function TodoList() {                                   
    const [inputText, setInputText] = useState('');                 //입력된 할일 텍스트
    const [todoTextList, setTodoTextList] = useState([]);           //입력된 할일을 담은 리스트
    const [selectedTodoList, setSelectedTodoList] = useState([]);   //체크박스 선택된 할일 리스트
    const [completedTodos, setCompletedTodos] = useState([]);       //완료 처리된 할일 리스트

    // 카운트 및 퍼센트 계산
    const todoCount = todoTextList.length;
    const doneCount = completedTodos.length;
    const donePercent = todoCount > 0 ? (parseFloat(doneCount) / parseFloat(todoCount)).toFixed(2) * 100 : '0';      //할일 퍼센트 계산

    const createTodo = () => {
        const trimmed = inputText.trim();                   //할일 텍스트 공백 삭제
        if (!trimmed) return;                               //텍스트 없으면 조기종료
        setTodoTextList([...todoTextList, trimmed]);        //할일을 담은 리스트에 기존 값 + 현재 값 저장
        setInputText('');                                   //입력 받고 난 후 입력창 다시 초기화
    };

    const updateSelectTodo = (e) => {                       //체크박스 선택 시 실행될 함수
        const index = Number(e.target.value);               
        const { checked } = e.target;

        if (checked) {                                          //체크박스 선택되면
            setSelectedTodoList((prev) => [...prev, index]);    //선택된 할일 담는 리스트에 기존값 + 현재값 추가
        } else {
            setSelectedTodoList((prev) => prev.filter((i) => i !== index)); //현재 인덱스 값이랑 다른 값으로만 리스트 세팅, 즉 현재값 삭제
        }
    };

    const handleComplete = (index) => {             
        setCompletedTodos((prev) =>             //index가 이미 선택되어있으면 해당 인덱스 선택리스트에서 제외, 선택 안되어있으면 등록
            prev.includes(index) ? prev.filter((i) => i !== index) : [...prev, index]
        );
    };

    const handleDelete = (index) => {               //삭제 버튼 클릭 시 
        setTodoTextList((prev) => prev.filter((_, i) => i !== index));  //할일 리스트에서 해당 인덱스 삭제
        setSelectedTodoList((prev) => prev.filter((i) => i !== index)); //선택 리스트에서 해당 인덱스 삭제
        setCompletedTodos((prev) => prev.filter((i) => i !== index));   //완료 리스트에서 해당 인덱스 삭제
    };

    const handleAllComplete = () => {           
        setCompletedTodos((prev) => {
            const newCompleted = [...prev];     //기존 완료된 배엷 복사

            selectedTodoList.forEach((index) => {       
                if (!newCompleted.includes(index)) {    //복사한 배열에 포함되지 않는다면
                    newCompleted.push(index);           //해당 인덱스 추가
                }
            });

            return newCompleted;
        });

        setSelectedTodoList([]); // 선택 해제
    };

    return (
        <main className="container">
            <section className="contents">
                <header className="header mb-3">
                    <p>TodoList</p>
                </header>
                <section className="work-status mb-3">
                    <p>할 일 : {todoCount}건</p>
                    <p>한 일 : {doneCount}건</p>
                    <p>달성률 : {donePercent}%</p>
                </section>
                <section className="todo-input mb-4">
                    <div className="row">
                        <div className="col-8">
                            <input
                                type="text"
                                className="form-control"
                                value={inputText}
                                onChange={(e) => setInputText(e.target.value)}
                                placeholder="할 일을 입력하세요"
                            />
                        </div>
                        <div className="col-4 text-end">
                            <button type="button" className="btn btn-primary me-2" onClick={createTodo}>
                                등록
                            </button>
                            <button type="button" className="btn btn-success" onClick={handleAllComplete}>
                                일괄 완료
                            </button>
                        </div>
                    </div>
                </section>
                <section className="todo-list">
                    {todoTextList.map((text, index) => (
                        <TodoTemplate
                            key={index}
                            text={text}
                            value={index}
                            selectedTodo={updateSelectTodo}
                            isChecked={selectedTodoList.includes(index)}
                            onComplete={() => handleComplete(index)}
                            onDelete={() => handleDelete(index)}
                            isDone={completedTodos.includes(index)}
                        />
                    ))}
                </section>
            </section>
        </main>
    );
}

export default TodoList;