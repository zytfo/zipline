import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import {
  Publications,
  UserData,
  UserDataWrapper,
  UserDescription,
  UserInfo,
  Username,
  UserPageContainer,
} from "./UserPageStyles";
import { userPageService } from "./services/UserPageService";
import { backendService } from "../../core/services/BackendService";
import { UserPageObject } from "./interfaces/UserPage";
import { Avatar, Skeleton } from "antd";
import { SmileOutlined } from "@ant-design/icons/lib";
import PublicationsFeed from "../PublicationsFeed/PublicationsFeed";

const UserPage = (props) => {
  const [currentUser, setCurrentUser] = useState<UserPageObject | null>(null);
  const { username } = props.match.params;

  useEffect(() => {
    userPageService
      .getUser(username)
      .then((response) => {
        setCurrentUser(response.data.data);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  }, [username]);

  return (
    <UserPageContainer>
      <UserInfo>
        {currentUser ? (
          <UserDataWrapper>
            <Avatar size={128} icon={<SmileOutlined />} />
            <UserData>
              <Username>{currentUser.username}</Username>
              <UserDescription>
                Here will be some other user data. For example, user
                description. Or his bio if he/she specifies (i dunno)
              </UserDescription>
            </UserData>
          </UserDataWrapper>
        ) : (
          <Skeleton avatar active paragraph={{ rows: 3 }} />
        )}
      </UserInfo>
      <Publications>
        {currentUser ? (
          <PublicationsFeed author={currentUser.username} />
        ) : (
          <Skeleton active paragraph={{ rows: 4 }} />
        )}
      </Publications>
    </UserPageContainer>
  );
};

export default withRouter(UserPage);
