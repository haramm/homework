import React from 'react';

function LoginBox({selectedUser, setSelectedUser, isLogin, userList, loginedUser, setLoginedUser}) {

    const loginEvt = () => {
        setLoginedUser(selectedUser);
    }

    const logoutEvt = () => {
        setLoginedUser('');
    }

    return (
        <>
            <header className='text-center mt-3'>
                <h2>Todo List</h2>
            </header>
            <div className='login-box mt-3'>
                <select id='userName' 
                        value={selectedUser}
                        className='form-select w-25' 
                        disabled={isLogin}
                        onChange={(e) => setSelectedUser(e.target.value)}>
                            {
                                userList?.map((user,index) => (
                                    <option key={index} value={user}>{user}</option>
                                ))
                            }
                </select>
                {
                    isLogin && <button type='button' 
                        className='btn btn-danger ms-2'
                        onClick={logoutEvt}>로그아웃</button>
                        || <button type='button' 
                        className='btn btn-info ms-2'
                        onClick={loginEvt}>로그인</button>
                }
                
                <span className='ms-2'>{loginedUser}</span>
            </div>
        </>
    );
}

export default LoginBox;