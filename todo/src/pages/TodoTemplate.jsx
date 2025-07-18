import React from 'react';

function TodoTemplate({ text, selectedTodo, isChecked, value, onComplete, onDelete, isDone }) {
    return (
        <div id="todoTemplate">
            <div className="todo mb-2 d-flex align-items-center">
                <div className="item1 me-2">
                    <input
                        type="checkbox"
                        onChange={selectedTodo}
                        checked={isChecked}
                        value={value}
                    />
                </div>
                <div className="item2">
                    <p style={{ textDecoration: isDone ? 'line-through' : 'none', margin: 0,    //완료 상태 시 글씨 회색, 글씨 줄처리
                                color : isDone ? 'gray' : 'black'}}>{text}</p>
                </div>
                <div className="item3 text-end">
                    <button
                        type="button"
                        className="btn btn-success btn-sm me-2"
                        onClick={onComplete}
                    >
                        완료
                    </button>
                    <button
                        type="button"
                        className="btn btn-danger btn-sm"
                        onClick={onDelete}
                    >
                        삭제
                    </button>
                </div>
            </div>
        </div>
    );
}

export default TodoTemplate;