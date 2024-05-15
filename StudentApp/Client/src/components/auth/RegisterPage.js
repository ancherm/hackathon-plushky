import React, { Component, Fragment } from 'react';
import '../../styles/FormPages.css'
import { toast } from 'react-toastify';
import { ToastComponent } from '../common';
import placeholder_user_image from '../../assets/images/placeholder.png';
import default_background_image from '../../assets/images/default-background-image.jpg';
import { connect } from 'react-redux';
import { registerAction, redirectAction } from '../../store/actions/authActions'

class RegisterPage extends Component {
    constructor(props) {
        super(props)

        this.state = {
            username: '',
            email: '',
            password: '',
            confirmPassword: '',
            firstName: '',
            lastName: '',
            address: '',
            city: '',
            profilePicUrl: placeholder_user_image,
            backgroundImageUrl: default_background_image,
            touched: {
                username: false,
                email: false,
                password: false,
                confirmPassword: false,
                firstName: false,
                lastName: false,
                address: false,
                city: false,
            }
        };

        this.onChangeHandler = this.onChangeHandler.bind(this);
        this.onSubmitHandler = this.onSubmitHandler.bind(this);
    }

    componentDidUpdate(prevProps, prevState){
        if (this.props.registerError.hasError && prevProps.registerError !== this.props.registerError) {
            toast.error(<ToastComponent.errorToast text={this.props.registerError.message} />, {
                position: toast.POSITION.TOP_RIGHT
            });
        } else if (this.props.registerSuccess) {
            this.props.redirect();

            toast.success(<ToastComponent.successToast text={this.props.registerMessage} />, {
                position: toast.POSITION.TOP_RIGHT
            });

            this.props.history.push('/login');
        }
    }

    onChangeHandler(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    onSubmitHandler(event) {
        event.preventDefault();

        if (!this.canBeSubmitted()) {
            return;
        }

        const { touched, ...otherProps } = this.state;
        this.props.register(otherProps)
    }

    canBeSubmitted() {
        const { username, email, firstName, lastName, password, confirmPassword, address, city } = this.state;
        const errors = this.validate(username, email, firstName, lastName, password, confirmPassword, address, city);
        const isDisabled = Object.keys(errors).some(x => errors[x])
        return !isDisabled;
    }

    handleBlur = (field) => (event) => {
        this.setState({
            touched: { ...this.state.touched, [field]: true }
        });

    }

    validate = (username, email, firstName, lastName, password, confirmPassword, address, city) => {
        const emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
        const firstLastNameRegex = /^[А-ЯЁA-Z]([а-яёa-zA-Z]+)?$/;
        const testEmail = emailRegex.test(email)
        const testFirstName = firstLastNameRegex.test(firstName)
        const testLastName = firstLastNameRegex.test(lastName)
        return {
            username: username.length < 4 || username.length > 16,
            email: email.length === 0 || !testEmail,
            firstName: firstName.length === 0 || !testFirstName,
            lastName: lastName.length === 0 || !testLastName,
            password: password.length < 4 || password.length > 16,
            confirmPassword: confirmPassword.length === 0 || confirmPassword !== password,
            address: address.length === 0,
            city: city.length === 0,
        }
    }

    render() {
        const { username, email, firstName, lastName, password, confirmPassword, address, city } = this.state;
        const errors = this.validate(username, email, firstName, lastName, password, confirmPassword, address, city);
        const isEnabled = !Object.keys(errors).some(x => errors[x])

        const shouldMarkError = (field) => {
            const hasError = errors[field];
            const shouldShow = this.state.touched[field];
            return hasError ? shouldShow : false;
        }

        return (
            <Fragment>
                <section className="pt-3">
                    <div className="container register-form-content-section pb-4 ">
                        <h1 className="text-center font-weight-bold mt-4" style={{ 'margin': '1rem auto', 'paddingTop': '2rem' }}>Регистрация</h1>
                        <div className="hr-styles" style={{ 'width': '70%' }}></div>

                        <form className="Register-form-container" onSubmit={this.onSubmitHandler}>

                            <div className="section-container">
                                <section className="left-section">
                                    <div className="form-group">
                                        <label htmlFor="username" >Имя пользоваться</label>
                                        <input
                                            type="text"
                                            className={"form-control " + (shouldMarkError('username') ? "error" : "")}
                                            id="username"
                                            name="username"
                                            value={this.state.username}
                                            onChange={this.onChangeHandler}
                                            onBlur={this.handleBlur('username')}
                                            aria-describedby="usernameHelp"
                                            placeholder="Введите имя пользователя"
                                        />
                                        {shouldMarkError('username') && <small id="usernameHelp" className="form-text alert alert-danger"> {(!this.state.username ? 'Требуется имя пользователя!' : 'Длина имени пользователя должна составлять не менее 4 и не более 16 символов соответственно!')}</small>}
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="firstName" >Имя</label>
                                        <input
                                            type="text"
                                            className={"form-control " + (shouldMarkError('firstName') ? "error" : "")}
                                            id="firstName"
                                            name="firstName"
                                            value={this.state.firstName}
                                            onChange={this.onChangeHandler}
                                            onBlur={this.handleBlur('firstName')}
                                            aria-describedby="firstNameHelp"
                                            placeholder="Введите имя"
                                        />
                                        {shouldMarkError('firstName') && <small id="firstNameHelp" className="form-text alert alert-danger">{(!this.state.firstName ? 'Имя обязательно' : 'Имя должно начинаться с заглавной буквы и состоять только из букв!')}</small>}
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="address" >Адрес</label>
                                        <input
                                            type="text"
                                            className={"form-control " + (shouldMarkError('address') ? "error" : "")}
                                            id="address"
                                            name="address"
                                            value={this.state.address}
                                            onChange={this.onChangeHandler}
                                            onBlur={this.handleBlur('address')}
                                            aria-describedby="addressHelp"
                                            placeholder="Введите адрес"
                                        />
                                        {shouldMarkError('address') && <small id="addressHelp" className="form-text alert alert-danger">{(!this.state.address ? 'Адрес обязателен' : '')}</small>}
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="password" >Пароль</label>
                                        <input
                                            type="password"
                                            className={"form-control " + (shouldMarkError('password') ? "error" : "")}
                                            id="password"
                                            name="password"
                                            value={this.state.password}
                                            onChange={this.onChangeHandler}
                                            onBlur={this.handleBlur('password')}
                                            aria-describedby="passwordHelp"
                                            placeholder="Введите пароль"
                                        />
                                        {shouldMarkError('password') && <small id="passwordHelp" className="form-text alert alert-danger">{(!this.state.password ? 'Пароль обязателен' : 'Длина пароля должна составлять не менее 4 и не более 16 символов!')}</small>}
                                    </div>
                                </section>

                                <section className="right-section">
                                    <div className="form-group">
                                        <label htmlFor="email" >Email</label>
                                        <input
                                            type="email"
                                            className={"form-control " + (shouldMarkError('email') ? "error" : "")}
                                            id="email"
                                            name="email"
                                            value={this.state.email}
                                            onChange={this.onChangeHandler}
                                            onBlur={this.handleBlur('email')}
                                            aria-describedby="emailHelp"
                                            placeholder="Введите email"

                                        />
                                        {shouldMarkError('email') && <small id="emailHelp" className="form-text alert alert-danger">{(!this.state.email ? 'Email обязателен' : 'Неверный адрес электронной почты!')}</small>}
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="lastName" >Фамилия</label>
                                        <input
                                            type="text"
                                            className={"form-control " + (shouldMarkError('lastName') ? "error" : "")}
                                            id="lastName"
                                            name="lastName"
                                            value={this.state.lastName}
                                            onChange={this.onChangeHandler}
                                            onBlur={this.handleBlur('lastName')}
                                            aria-describedby="lastNameHelp"
                                            placeholder="Введите фамилию"
                                        />
                                        {shouldMarkError('lastName') && <small id="lastNameHelp" className="form-text alert alert-danger">{(!this.state.lastName ? 'Фамилия обязательна!' : 'Фамилия должна начинаться с заглавной буквы и состоять только из букв!')}</small>}
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="city" >Город</label>
                                        <input
                                            type="text"
                                            className={"form-control " + (shouldMarkError('city') ? "error" : "")}
                                            id="city"
                                            name="city"
                                            value={this.state.city}
                                            onChange={this.onChangeHandler}
                                            onBlur={this.handleBlur('city')}
                                            aria-describedby="cityHelp"
                                            placeholder="Введите город"
                                        />
                                        {shouldMarkError('city') && <small id="cityHelp" className="form-text alert alert-danger">{(!this.state.city ? 'Требуется город!' : '')}</small>}
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="confirmPassword" >Подтвердите пароль</label>
                                        <input
                                            type="password"
                                            className={"form-control " + (shouldMarkError('confirmPassword') ? "error" : "")}
                                            id="confirmPassword"
                                            name="confirmPassword"
                                            value={this.state.confirmPassword}
                                            onChange={this.onChangeHandler}
                                            onBlur={this.handleBlur('confirmPassword')}
                                            aria-describedby="confirmPasswordHelp"
                                            placeholder="Подтвердите ваш пароль"
                                        />
                                        {shouldMarkError('confirmPassword') && <small id="confirmPasswordHelp" className="form-text alert alert-danger">Пароли не совпадают!</small>}
                                    </div>
                                </section>
                            </div>

                            <div className="text-center">
                                <button disabled={!isEnabled} type="submit" className="btn App-button-primary btn-lg m-3">Регистрация</button>
                            </div>
                        </form>
                    </div>

                </section>
            </Fragment>
        )
    }
};

function mapStateToProps(state) {
    return {
        registerSuccess: state.register.success,
        registerMessage: state.register.message,
        registerError: state.registerError
    }
}

function mapDispatchToProps(dispatch) {
    return {
        register: (userData) =>
            dispatch(registerAction(userData)),
        redirect: () => dispatch(redirectAction())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(RegisterPage);
