import React from 'react';
import {makeStyles} from "@material-ui/core/styles";

const getClasses = makeStyles(() => ({
    container: {
        display: 'flex',
        width: '100%',
        flexDirection: 'column',
        gap: '10px',
    },
}));

const List = ({children}) => {
    const classes = getClasses();

    return (
        <div className={classes.container}>
            {children}
        </div>
    );
};

export default List;