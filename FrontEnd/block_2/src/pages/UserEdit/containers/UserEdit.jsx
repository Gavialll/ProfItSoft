import React, {useEffect, useState} from 'react';
import TextField from "components/TextField";
import Select from "components/Select";
import Typography from "components/Typography";
import MenuItem from "components/MenuItem";
import {Button} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
import {useDispatch, useSelector} from "react-redux";
import useChangePage from "hooks/useChangePage";
import * as PAGES from "constants/pages";
import useLocationSearch from "hooks/useLocationSearch";
import {fetchGetProfessions} from "app/actions/professionsService";
import {useIntl} from "react-intl";
import {fetchCreateUser, fetchGetUserById, fetchUpdateUser} from "app/actions/userService";

const getStyles = makeStyles({
    wrapper: {
        display: "flex",
        justifyContent: "center",
        width: "100%",
    },
    form: {
        gap: "25px",
        display: "flex",
        width: "max-content",
        flexDirection: "column",
        alignItems: "center",
    },
    select: {
        width: "100%"
    }
})

const UserEdit = () => {

    const userInitial = {
        name: "",
        age: 18,
        profession: ""
    }

    const classes = getStyles()
    const loader = useSelector(({loader}) => loader)
    const professions = useSelector(({professionService}) => professionService.professions)
    const dispatch = useDispatch()
    const [user, setUser] = useState(userInitial)
    const changePage = useChangePage()
    const params = useLocationSearch()
    const {formatMessage} = useIntl()
    const locationSearch = useLocationSearch();

    function createUser() {
        dispatch(fetchCreateUser(user, changePage, locationSearch))
    }

    function backToUsers(){
        delete locationSearch.id
            changePage({
                locationSearch: {
                    ...locationSearch,
                },
                path: `/${PAGES.USERS}`,
            })
    }

    function editUser() {
        dispatch(fetchUpdateUser(user, changePage, locationSearch))
    }

    useEffect(() => {
        dispatch(fetchGetProfessions())

        if (params.id) {
            dispatch(fetchGetUserById(params.id, setUser))
        }
    }, [])

    return (
        <div className={classes.wrapper}>
            {loader.loader
                ?
                <div>{loader.error ? loader.message.error : loader.message.loading}</div>
                :
                <div className={classes.form}>
                    <TextField
                        type="text"
                        key="name"
                        value={user.name}
                        label={formatMessage({id: "name"})}
                        onChange={(e) => {
                            setUser({
                                ...user,
                                name: e.target.value
                            })
                        }}
                    />
                    <TextField
                        type="number"
                        key="age"
                        label={formatMessage({id: "age"})}
                        value={user.age}
                        onChange={(e) => {
                            setUser(prevState => {
                                return {
                                ...prevState,
                                    age: e.target.value
                                }
                            })
                        }}
                    />
                    <Select
                        value={user.profession}
                        className={classes.select}
                        onChange={e => {
                            setUser(prevState => {
                                return {
                                    ...prevState,
                                    profession: e.target.value
                                }
                            })
                        }}
                    >{
                        professions.map(profession =>
                            <MenuItem key={profession.id} value={profession.name}>
                                <Typography>
                                    {profession.name}
                                </Typography>
                            </MenuItem>
                        )
                    }
                    </Select>
                    {params.id
                        ?
                        <Button onClick={editUser}>{formatMessage({id: "edit"})}</Button>
                        :
                        <Button onClick={createUser}>{formatMessage({id: "create"})}</Button>
                    }
                        <Button onClick={backToUsers}>{formatMessage({id: "cancel"})}</Button>
                </div>
            }
        </div>
    );
};

export default UserEdit;