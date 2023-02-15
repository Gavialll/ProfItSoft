import React, {useEffect, useState} from 'react';
import {useDispatch} from 'react-redux';
import {BrowserRouter, Redirect, Route, Switch,} from 'react-router-dom';
import IntlProvider from 'components/IntlProvider';
import Header from 'components/Header';
import PageInitial from 'pageProviders/Initial';
import PageLogin from 'pageProviders/Login';
import PageUsers from 'pageProviders/Users';
import PageUserEdit from 'pageProviders/UserEdit';
import * as PAGES from 'constants/pages';
import {fetchUser,} from '../actions/user';

const App = () => {
    const [state, setState] = useState({
        componentDidMount: false,
    });
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(fetchUser());
        setState(prevState => ({
            ...prevState,
            componentDidMount: true,
        }));
    }, []);

    return (
        <BrowserRouter>
            <IntlProvider>
                <Header/>
                {state.componentDidMount && (
                    <Switch>
                        <Route path={`/${PAGES.LOGIN}`}>
                            <PageLogin/>
                        </Route>
                        <Route path={`/${PAGES.INITIAL}`}>
                            <PageInitial/>
                        </Route>
                        <Route path={`/${PAGES.USERS}`}>
                            <PageUsers/>
                        </Route>
                        <Route path={`/${PAGES.USER_EDIT}`}>
                        <PageUserEdit />
                        </Route>
                        <Redirect from="*" to={`/${PAGES.INITIAL}`}/>
                    </Switch>
                )}
            </IntlProvider>
        </BrowserRouter>
    );
};

export default App;
