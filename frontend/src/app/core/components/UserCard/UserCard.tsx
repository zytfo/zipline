import React, { useEffect, useState } from "react";
import { Link, withRouter } from "react-router-dom";
import {
  BlocksDivider,
  Email,
  LogOutButton,
  MainInfo,
  UserBlockContainer,
  UserCardContainer,
  UserCardDivider,
  Username,
} from "./UserCardStyles";
import { Avatar, Menu } from "antd";
import {
  BookOutlined,
  FireOutlined,
  SmileOutlined,
  UserOutlined,
} from "@ant-design/icons/lib";
import auth from "../../services/AuthService";

const UserCard = (props) => {
  const [metadata, setMetadata] = useState<any>(null);
  const [currentItem, setCurrentItem] = useState<any>([
    props.location.pathname,
  ]);

  useEffect(() => {
    setMetadata(auth.getMetadata());
  }, []);

  useEffect(() => {
    if (props.location.pathname === "/news") {
      setCurrentItem(["/publications"]);
    } else {
      setCurrentItem([props.location.pathname]);
    }
  }, [props.location.key]);

  const logOut = () => {
    auth.logout();
    props.history.push("/");
  };

  return (
    <UserBlockContainer>
      <UserCardContainer>
        <MainInfo>
          <Avatar size={64} icon={<SmileOutlined />} />
          <Username>{metadata?.username}</Username>
          <Email>{metadata?.email}</Email>
        </MainInfo>
        <UserCardDivider />
        <Menu selectedKeys={currentItem} mode="inline">
          <Menu.Item
            key={`/user/${metadata?.username}`}
            icon={<UserOutlined />}
          >
            <Link to={`/user/${metadata?.username}`}>My Page</Link>
          </Menu.Item>
          <Menu.Item key="/publications" icon={<FireOutlined />}>
            <Link to={`/publications`}>Main</Link>
          </Menu.Item>
          <Menu.Item key="/favourite" icon={<BookOutlined />}>
            Favourite News
          </Menu.Item>
        </Menu>
      </UserCardContainer>
      <BlocksDivider />
      <LogOutButton type="text" size="large" onClick={logOut}>
        Log Out
      </LogOutButton>
    </UserBlockContainer>
  );
};

export default withRouter(UserCard);
