import React, { Component } from 'react';
import userService from '../infrastructure/userService'
import { Redirect } from 'react-router-dom'

function withAuthorization(WrapperComponent, roles) {
    return class WithAuthorization extends Component {
        constructor(props) {
            super(props);

            this.state = {
                userRoles: [],
                ready: false
            }
        }

        componentDidMount = () => {
            let currentUserRoles = userService.getRole();

            if(currentUserRoles){
                this.setState({
                    userRoles: currentUserRoles.split(','),
                    ready: true 
                })
            }
        }

        render = () => {
            if(!this.state.ready){
                return null;
            }
            let userHasAccess = false;

            for (let role of roles){
                userHasAccess = userHasAccess || this.state.userRoles.indexOf(role) !== -1;
            }

            if(userHasAccess){
                return <WrapperComponent {...this.props} /> 
            }else{
                return <Redirect to="/error" {...this.props} />
            }
        }
    }
}

export function withUserAuthorization(Component) {
    return withAuthorization(Component, ['USER', 'ADMIN', 'ROOT'])
}

export function withAdminAuthorization(Component) {
    return withAuthorization(Component, ['ADMIN', 'ROOT'])
}

export function withRootAuthorization(Component) {
    return withAuthorization(Component, ['ROOT'])
}
