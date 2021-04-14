import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import { Menu } from "antd";
import { Link } from "react-router-dom";

const MarketMenu = (props) => {
  const [currentItem, setCurrentItem] = useState<any>([
    props.location.pathname,
  ]);

  useEffect(() => {
    if (props.location.pathname.includes("my")) {
      setCurrentItem(["/user_trades"]);
    } else if (props.location.pathname.includes("nft")) {
      setCurrentItem(["/nfts"]);
    } else {
      setCurrentItem(["/market"]);
    }
  }, [props.location.key]);

  return (
    <Menu
      selectedKeys={currentItem}
      mode="horizontal"
      style={{ marginTop: "24px" }}
    >
      <Menu.Item key="/market">
        <Link to={`/marketplace/all`}> Marketplace </Link>
      </Menu.Item>
      <Menu.Item key="/user_trades">
        <Link to={`/marketplace/my`}> My trades </Link>
      </Menu.Item>
      <Menu.Item key="/nfts">
        <Link to={`/marketplace/nft`}> My NFTs </Link>
      </Menu.Item>
    </Menu>
  );
};

export default withRouter(MarketMenu);
