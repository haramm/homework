import React, { useEffect, useState } from 'react';
import '../assets/css/library.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import LendBook from './lendBook';
import BookList from './BookList';
import LendableList from './LendableList';

function Library(props) {

    const userList = ['이름1','이름2'];                          //유저 목록 생성
    const [loginedUser, setLoginedUser] = useState('');         // 로그인중인 유저 선택
    const [selectedUser, setSelectedUser] = useState('이름1');  // 로그인하기위해 선택된 유저이름 저장
    const [bookList, setBookList] = useState([                  //책 초깃값 설정
        {bookName : '책1', state : 'lended', owner : '이름1'},  //책은 이름, 상태, 현재 누가 갖고있는지에 대한 정보를 가지고있음
        {bookName : '책2', state : 'lended', owner : '이름1'},
        {bookName : '책3', state : 'lended', owner : '이름2'},
        {bookName : '책4', state : 'lended', owner : '이름2'},
        {bookName : '책5', state : 'lendable', owner : ''},
        {bookName : '책6', state : 'lendable', owner : ''}
    ]);
    const [lendedBookList, setLendedBookList] = useState([]);       //상태가 lended인 책을 담는 리스트
    const [lendableBookList, setLendableBookList] = useState([]);   //상태가 lendable인 책을 담는 리스트
    const [checkedBookList, setCheckedBookList] = useState([]);     //체크박스에 체크가 되어있는 책을 담는 리스트
    const [inputText, setInputText] = useState('');                 //책을 추가할때 이름을 입력받는 텍스트

    useEffect(() => {               //책을 대여 시 로그인된 유저와 전체 책 리스트에 
                                    //의존성을 걸어 해당 로그인된 유저의 대여중인 책 리스트를 변경
        const newLendedBookList = bookList.filter((book) => book.state === 'lended' && book.owner === loginedUser);
        setLendedBookList(newLendedBookList);
        
    },[loginedUser,bookList]);

    useEffect(() => {               //책 리스트 변경 시 의존성 주입
                                    //현재 상태가 lendable인 책을 lendable 리스트에 대입
        const newLendableBookList = bookList.filter((book) => book.state === 'lendable' && book.owner === '');
        setLendableBookList(newLendableBookList);
        
    },[bookList]);
    
    
    
    const loginBtn = () => {                //셀렉트박스로 선택된 유저를 로그인된 유저에 대입하여 로그인
        setLoginedUser(selectedUser);
    }

    const handleBookCheck = (lendBook) => {             //체크박스 선택시 발생되는 이벤트
        if (checkedBookList.includes(lendBook)) {       // 체크된 책 담는 리스트에 이미 있으면 제거, 없으면 추가
            setCheckedBookList(checkedBookList.filter(book => book !== lendBook)); 
        }
        else {                                                    
            setCheckedBookList([...checkedBookList, lendBook]);           
        }
    }

    const returnBook = () => {          //반납 버튼 이벤트
        const checkLendedList = checkedBookList.filter((book) => book.owner === '');
        if(checkLendedList.length !== 0) {              //새로운 배열에 onwer가 빈칸인 책만 담아서 길이 확인
            alert('대여 중인 책만 반납이 가능합니다.');  // 길이가 0이 아니면 대여중이 아닌 책이 체크되있는 상태
            return;                                    // 따라서 경고 문구 출력 후 조기종료
        }
        const newCheckedBookList = bookList.map(book => {   //현재 대여중인 책만 선택한 상태 시 
            if (checkedBookList.includes(book)) {           //전체 책중 체크된 책 리스트에 있는 책만 골라서 
                return {                                    //해당 책들의 state를 lendable, owner를 빈칸으로 설정
                    ...book,
                    state: 'lendable',
                    owner: ''
                };
            }
            return book;
        });
        setBookList(newCheckedBookList);                    //위 map으로 재설정하여 리턴받은 배열로 전체 책 리스트 설정
        setCheckedBookList([]);                             //체크박스 체크 모두 해제
    }

    const addBtn = () => {                                  //추가 버튼
        if(inputText.trim().length === 0) {                 //입력된 책 이름 없을 시 경고 문구 출력, 조기 종료
            alert('추가할 책의 이름을 입력하세요');
            return;
        }
        const newBook = {bookName : inputText, state: 'lendable', owner: ''};   //입력받은 책 이름으로 새로운 책 생성
        const newBookList = [...bookList,newBook];                          //전체 책리스트에 생성한 책 추가로 생성된 새로운 배열 
        setBookList(newBookList);                               //새로운 배열로 전체 책리스트 설정
        setInputText('');                                       //책 이름 입력칸 초기화
    }
    
    const lendBtn = () => {                                 //대여 버튼
        if(loginedUser.length === 0) {                      //미로그인 시 경고 문구 출력, 조기 종료
            alert('로그인이 필요합니다');                   
            return;
        }
        if(checkedBookList.length === 0) {                  // 체크된 책이 없을 시 경고 문구 출력, 조기 종료
            alert('대여할 책을 선택해주세요');
            return;
        }
        const newCheckedBookList = bookList.map(book => {           // 전체 책 중 체크된 책에 한하여 
            if (checkedBookList.includes(book)) {                   // 책의 state를 lended, owner를 로그인된 유저로 설정
                return {
                    ...book,
                    state: 'lended',
                    owner: loginedUser
                };
            }
            return book;
        });
        setBookList(newCheckedBookList);       //위 map으로 새로 설정된 배열로 전체 책리스트 설정
        setCheckedBookList([]);               //체크박스 전체 선택 해제
    }

    const deleteBtn = () => {
        //전체 책리스트에 체크박스 선택된 책만 필터링 후 필터링된 책들 중 owner가 빈칸이 아닌 책들만 추출하여 배열에 담음
        const checkLendedList = bookList.filter((book) => checkedBookList.includes(book)).filter((book) => book.owner !== '');
        if(checkLendedList.length !== 0) {  //위 필터로 만들어진 배열의 길이가 0이 아니면(삭제하고자 체크한 책 중 대여중인 책 존재)
            alert('대여중인 책은 삭제가 불가능합니다');     //경고 문구 출력, 조기 종료
            return;
        }
        const newBookList = bookList.filter((book) => !checkedBookList.includes(book)); // 체크 안한 책들로만 배열 구성
        setBookList(newBookList);       //체크 안한 책들로만 전체 책 설정하여 삭제
        setCheckedBookList([]);         //체크 전체 해제
    }

    return (
        <div className='main'>
            <div className='customContainer'>
                <section className='login'>
                    <select id='userName' value={selectedUser} onChange={(e) => setSelectedUser(e.target.value)}>
                        {
                            userList?.map((user,index) => (
                                <option key={index} value={user}>{user}</option>
                            ))
                        }
                    </select>
                    <button type='button' className='btn btn-info' onClick={loginBtn}>로그인</button>
                    <span>{loginedUser}</span>
                </section>
                <section className='lendBox'>
                    <span>대여한 도서 목록</span>
                    <button type='button' className='btn btn-success' onClick={returnBook}>반납</button>
                </section>
                <BookList lendedBookList={lendedBookList} checkedBookList={checkedBookList} handleBookCheck={handleBookCheck}/>
                <p style={{width : '90%'}}>도서 목록</p>
                <div className='inputBox'>
                    <input type='text' className='inputText' value={inputText} onChange={(e) => setInputText(e.target.value)}/>
                    <button type='button' className='btn btn-dark' onClick={addBtn}>추가</button>
                    <button type='button' className='btn btn-light' onClick={lendBtn}>대여</button>
                    <button type='button' className='btn btn-danger' onClick={deleteBtn}>삭제</button>
                </div>
                <LendableList lendableBookList={lendableBookList} checkedBookList={checkedBookList} handleBookCheck={handleBookCheck}/>
            </div>
        </div>
    );
}

export default Library;