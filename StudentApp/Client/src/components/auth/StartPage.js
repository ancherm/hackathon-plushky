import React from 'react';
import { NavLink } from 'react-router-dom';
import userService from '../../infrastructure/userService';
import './css/StartPage.css';

const StartPage = () => {
    const isAdmin = userService.isAdmin();
    const isRoot = userService.isRoot();
    const currentUserId = userService.getUserId();
    let StartPageView;

    if (!localStorage.getItem('token')) {
        StartPageView = (
            <div>
                <div className="container text-center start-page-margin" >
                    <div className="jumbotron bg-light text-dark text-center mb-0 mt-5 mx-auto jumbo-wrapper" style={{ 'boxShadow': '0 0 14px 1px rgba(0, 0, 0, 0.3)' }}>
                        <h2 className="h1 h1-responsive">Добро пожаловать!</h2>
                        <div className="hr-styles" style={{'width': '70%'}}></div>
                        <p className="lead" style={{'marginBottom': '1.5rem', 'fontWeight': '600'}}>Если у вас нет учетной записи, обратитесь в деканат, для получения логина и пароля</p>
                        <div className="hr-styles" style={{'width': '70%'}}></div>
                        <p className="lead">
                            <NavLink className="btn App-button-primary btn-lg m-3" to="/login" role="button">Войти</NavLink>
                        </p>
                    </div>
                </div>
            </div >
        )
    } else {
        StartPageView = (
            <div>
                <div className="container text-center start-page-margin">
                    <div className="jumbotron bg-light text-dark text-center mb-0 mt-5 jumbo-wrapper" style={{ 'boxShadow': '0 0 14px 1px rgba(0, 0, 0, 0.3)' }}>
                        <h3 className="md-display-5 h3 h3-responsive mb-3">Привет {userService.getUsername()}!</h3>
                        <div className="hr-styles" style={{'width': '80%'}}></div>
                        <h2 className="h1 h1-responsive">Добро пожаловать!</h2>
                        <div className="hr-styles" style={{'width': '80%'}}></div>
                        <p className="lead">
                            {/*<NavLink className="btn App-button-primary btn-lg m-3" to={`/home/comments/${currentUserId}`} role="button">Домой</NavLink>*/}
                            <NavLink className="btn App-button-primary btn-lg m-3" to={`/home/profile/${currentUserId}`} role="button">Профиль</NavLink>
                            {(isAdmin || isRoot) && <NavLink className="btn App-button-primary btn-lg m-3" to={`/home/users/all/${userService.getUserId()}`} role="button">Все пользователи</NavLink>}
                        </p>
                    </div>
                </div>
            </div>
        )
    }

    return (
        <div className="container text-center pt-5">
            {StartPageView}
        </div>

    )
}

export default StartPage;