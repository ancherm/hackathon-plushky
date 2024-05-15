import React from 'react';
import { NavLink } from 'react-router-dom';
import "./css/TimeLine.css";

const TimeLine = (props) => {
    return (
        <article className="main-article-header">
            <nav>
                <ul>
                    <li>
                        <NavLink className="timeline " exact to={`/home/gallery/${props.id}`}>
                            <div className="div-text"><p>Документы</p></div>
                        </NavLink>
                    </li>
                    <li>
                        <NavLink className="about  " exact to={`/home/profile/${props.id}`}>
                            <div className={"div-text"}><p>Данные</p></div>
                        </NavLink>
                    </li>
                    <li>
                        <NavLink className="friends " exact to={`/home/friends/${props.id}`}>
                            <div className={"div-text"}><p>Мои одногруппники</p></div>
                        </NavLink>
                    </li>
                    <li>
                        <NavLink className="photos " exact to={`/home/formDoc/${props.id}`}>
                            <div className={"div-text"}><p>Связь с деканатом</p></div>
                        </NavLink>
                    </li>
                </ul>
            </nav>
        </article>
    )
}

export default TimeLine;