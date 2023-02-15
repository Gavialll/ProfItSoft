import React, {useEffect} from 'react';
import {makeStyles} from "@material-ui/core/styles";
import List from "components/List";
import Item from "components/Item";
import {useDispatch, useSelector} from "react-redux";
import {fetchGetUsers} from "app/actions/userService";
import {Button} from "@material-ui/core";
import useChangePage from "hooks/useChangePage";
import * as PAGES from "constants/pages";
import {useIntl} from "react-intl";
import useLocationSearch from "hooks/useLocationSearch";

const getClasses = makeStyles(() => ({
    fullWidth: {
        width: "100%",
    },
    container: {
        backgroundColor: 'red',
    },
    center: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
    }
}));

const Users = () => {
    const classes = getClasses();
    const dispatch = useDispatch();
    const items = useSelector(({userService}) => userService.items);
    const loader = useSelector(({loader}) => loader);
    const changePage = useChangePage()
    const {formatMessage} = useIntl()
    const locationSearch = useLocationSearch();

    useEffect(() => {
        dispatch(fetchGetUsers())
    }, [])

    function createUser() {
        changePage({
            locationSearch: {
                ...locationSearch,
            },
            path: `/${PAGES.USER_EDIT}`,
        });
    }

    return (
        <div className={classes.fullWidth}>
            <div className={classes.center}>
                <Button onClick={createUser}>{formatMessage({id: "create"})}</Button>
            </div>
            <div className={classes.fullWidth}>
                {loader.loader
                    ?
                    <div>{loader.error ? loader.message.error : loader.message.loading}</div>
                    :
                    <List>
                        {items.map(user =>
                            <Item
                                key={user.id}
                                data={user}
                            />)}
                    </List>}
            </div>
        </div>
    );
};

export default Users;
