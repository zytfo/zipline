import React, {useEffect, useState} from "react";
import { Menu } from "antd";
import { Logo, OTUSHeader } from "./HeaderStyles";
import auth from "../../services/AuthService";
import { Link, withRouter } from "react-router-dom";

const Header = (props: any) => {
  const [currentItem, setCurrentItem] = useState([props.location.pathname]);

  useEffect(() => {
      setCurrentItem([props.location.pathname]);
  }, [props.location.key]);

  return (
    <OTUSHeader>
      <Logo>ZIPLINE</Logo>
      <Menu
        theme="dark"
        mode="horizontal"
        style={{ float: "right" }}
        selectedKeys={currentItem}
      >
        {!auth.isAuthenticated() && (
          <Menu.Item key="/login">
            <Link to={"/login"}>Login</Link>
          </Menu.Item>
        )}
        {!auth.isAuthenticated() && (
          <Menu.Item key="/register">
            <Link to={"/register"}>Register</Link>
          </Menu.Item>
        )}
      </Menu>
    </OTUSHeader>
  );
};

export default withRouter(Header);
