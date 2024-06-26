import React, { Fragment } from 'react';
import { NavLink } from 'react-router-dom';
import userService from '../../infrastructure/userService';

const Intro = (props) => {
    const formattedUsername = userService.formatUsername(props.firstName, props.lastName)
    return (
        <Fragment >
            <article className="aside-article-intro">
                <div className="aside-article-header">
                    <div className="aside-article-icon">
                        <i className="fas fa-globe-asia"></i>
                    </div>
                    <h3 className="aside-article-title">Интро</h3>
                </div>

                <div className="hr-styles" style={{'width': '90%'}}></div>

                <div className="aside-intro-content">
                    <h4 className="occupation">{formattedUsername}</h4>
                    <p>Живет в {props.address},  в {props.city}.</p>
                    <p>Из {props.city}.</p>
                </div>

                <button className="button update-info">
                    <NavLink className="about  " exact to={`/home/profile/${props.id}`}>Обновить информацию</NavLink>
                </button>
            </article>
        </Fragment>
    )
}

export default Intro;