import './App.css';
import React, { useState } from 'react';
import Login from './pages/user/Login';
import BoardWrite from './pages/board/BoardWrite';
import BoardList from './pages/board/BoardList';
import Join from './pages/user/Join';
import { Route } from 'react-router-dom';
import BoardDetail from './pages/board/BoardDetail';
import Nav from './pages/components/Nav';
import BoardModify from './pages/board/BoardModify';
import PtWrite from './pages/pt/PtWrite';
import PtList from './pages/pt/PtList';
import PtDetail from './pages/pt/PtDetail';
import PtModify from './pages/pt/PtModify';
import Test from './pages/user/Test';
import Test2 from './pages/user/Test2';

function App() {
  const [user, setUser] = useState();
  return (
    <div>
      <Nav user={user} />
      <Route path="/join" exact={true} component={Join}></Route>
      <Route path="/login" exact={true} component={Login}></Route>
      <Route path="/" exact={true} component={BoardList}></Route>
      <Route path="/boardWrite" exact={true} component={BoardWrite}></Route>
      <Route path="/boardDetail/:no" exact={true} component={BoardDetail}></Route>
      <Route path="/boardModify/:no" exact={true} component={BoardModify}></Route>
      <Route path="/ptWrite" exact={true} component={PtWrite}></Route>
      <Route path="/ptList" exact={true} component={PtList} ></Route>
      <Route path="/ptDetail/:no" exact={true} component={PtDetail}></Route>
      <Route path="/ptModify/:no" exact={true} component={PtModify}></Route>


      <Route path="/test" exact={true} component={Test2}></Route>
    </div>
  );
}

export default App;
