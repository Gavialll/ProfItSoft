import React from 'react';
import PageContainer from "../components/PageContainer";
import UserEditPage from "../pages/UserEdit";
import PageAccessValidator from "../components/PageAccessValidator";

const Users = () => {
    return (
        <PageAccessValidator>
            <PageContainer>
                <UserEditPage />
            </PageContainer>
        </PageAccessValidator>
    );
};

export default Users;