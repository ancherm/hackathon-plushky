import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom'
import './styles/index.css';
import App from './App';
import { Provider } from 'react-redux';
import store from './store/store'

import * as serviceWorker from './serviceWorker';

ReactDOM.render((
    <Provider store={store}>
        <Router>
            <App />
        </Router>
    </Provider>
), document.getElementById('root'));

serviceWorker.unregister();

