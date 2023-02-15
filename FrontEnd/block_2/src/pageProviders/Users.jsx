import React from 'react';
import PageContainer from "../components/PageContainer";
import UsersPage from "../pages/Users";
import PageAccessValidator from "../components/PageAccessValidator";

const Users = () => {
    return (
        <PageAccessValidator>
            <PageContainer>
                <UsersPage />
            </PageContainer>
        </PageAccessValidator>
    );
};

export default Users;