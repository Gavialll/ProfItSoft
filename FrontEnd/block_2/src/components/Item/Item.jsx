import React from 'react';
import {makeStyles} from "@material-ui/core/styles";
import user from './img/user.svg'
import {Button} from "@material-ui/core";
import {fetchDeleteUser} from "app/actions/userService";
import {useDispatch} from "react-redux";
import useChangePage from "hooks/useChangePage";
import * as PAGES from "constants/pages";
import {useIntl} from "react-intl";
import useLocationSearch from "hooks/useLocationSearch";

const getStyles = makeStyles({
    wrapper: {
        display: "flex",
        border: "1px solid black",
        borderRadius: "50px",
        overflow: "hidden",
        padding: "5px",
        width: 'calc(100% - 10px)',
        "&:hover $show":{
            display : "flex",
        }
    },
    img:{
        height: '60px'
    },
    wrapperInformation: {
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-evenly",
        width: "70%",
    },
    show: {
        display: "none",
    }
})

const Item = ({data}) => {
    const classes = getStyles();
    const dispatch = useDispatch();
    const changePage = useChangePage();
    const {formatMessage} = useIntl();
    const locationSearch = useLocationSearch();

    function editItem() {
        changePage({
            locationSearch:{
                ...locationSearch,
                id: data.id
            },
            path: `/${PAGES.USER_EDIT}`
        })
    }


    function deleteItem() {
        dispatch(fetchDeleteUser(data.id))
    }

    return (
        <div className={classes.wrapper}>
            <img className={classes.img} src={user} alt={"tr"}/>
            <div className={classes.wrapperInformation}>
                <span>{`${formatMessage({id: "name"})}: ${data.name}`}</span>
                <span>{`${formatMessage({id: "age"})}: ${data.age}`}</span>
                <span>{`${formatMessage({id: "profession"})}: ${data.profession}`}</span>
            </div>
            <Button
                className={classes.show}
                onClick={editItem}
            >
                {formatMessage({id: "edit"})}
            </Button>
            <Button
                className={classes.show}
                onClick={deleteItem}
            >
                {formatMessage({id: "delete"})}
            </Button>
        </div>
    );
};

export default Item;