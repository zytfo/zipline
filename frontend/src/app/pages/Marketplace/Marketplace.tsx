import React from "react";
import {Route, withRouter} from "react-router-dom";
import MarketMenu from "./components/MarketMenu/MarketMenu";
import Market from "./components/Market/Market";
import OpenNewTrade from "./components/OpenNewTrade/OpenNewTrade";

const Marketplace = () => {

  return (
    <div>
      <MarketMenu/>
      <Route path={"/marketplace/all"}>
        <Market positions="all_open"/>
      </Route>
      <Route path={"/marketplace/my"}>
        <Market positions="of_user"/>
      </Route>
      <Route path={"/marketplace/nft"}>
        <OpenNewTrade />
      </Route>
    </div>
  );
};

export default withRouter(Marketplace);
