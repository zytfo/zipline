import React, {useEffect} from "react";
import {Layout, Menu} from "antd";
import {UserOutlined, HomeOutlined, NotificationOutlined} from '@ant-design/icons';
import {Link} from "react-router-dom";

const {Sider} = Layout;

const SideMenu = () => {
  let currentItem: string[] = [];
  useEffect(() => {
    currentItem.push(window.location.pathname);
  }, []);

  const menuItems = [
    {
      link: '/home',
      name: 'Home',
      logo: <HomeOutlined/>
    },
    {
      link: '/user',
      name: 'User',
      logo: <UserOutlined/>
    },
    {
      link: '/notifications',
      name: 'Notifications',
      logo: <NotificationOutlined/>
    },
  ];

  return (
    <Sider width={300}>
      <Menu
        mode="inline"
        defaultSelectedKeys={currentItem}
        style={{height: '100%', borderRight: 0}}
      >
        {menuItems.map((item) => (
          <Menu.Item key={item.link} icon={item.logo}>
            <Link to={item.link}>{item.name}</Link>
          </Menu.Item>
        ))}
      </Menu>
    </Sider>
  )
};

export default SideMenu;
