import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Link from 'components/Link';
import Typography from 'components/Typography';
import useAccessValidate from 'hooks/useAccessValidate';
import * as PAGES from 'constants/pages'

const getClasses = makeStyles(() => ({
    container: {
        display: 'flex',
        flexDirection: 'column',
    },
}));

const Initial = ({
                     authorities,
                 }) => {
    const classes = getClasses();
    const canSeeList = useAccessValidate({
        ownedAuthorities: authorities,
        neededAuthorities: ['МОЖНО_ВОТ_ЭТУ_ШТУКУ'],
    });

    return (
        <div className={classes.container}>
            {canSeeList && (
                <Link key={"users"} to={(location => ({
                    ...location,
                    pathname: `/${PAGES.USERS}`,
                    search: `${location.search}`,
                }))
                }>Users</Link>
            )}
            {!canSeeList && (
                <Typography component={'span'}>
                    Не могу ничего показать :(
                </Typography>
            )}
        </div>
    )
};

export default Initial;
