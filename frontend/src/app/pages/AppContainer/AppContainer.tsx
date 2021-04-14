import React, { useEffect } from "react";
import {BackTop, Layout, Tabs, notification, Button} from "antd";
import Header from "../../core/components/Header/Header";
import { Redirect, Route, Switch } from "react-router-dom";
import auth from "../../core/services/AuthService";
import Login from "../Login/Login";
import Register from "../Register/Register";
import NewsFeed from "../NewsFeed/NewsFeed";
import PublicationsFeed from "../PublicationsFeed/PublicationsFeed";
import { backendService } from "../../core/services/BackendService";
import { MainContainer } from "./AppContainerStyles";
import UserCard from "../../core/components/UserCard/UserCard";
import UserPage from "../UserPage/UserPage";
import Wallets from "../Wallets/Wallets";
import NftPage from "../NftPage/NftPage";
import Marketplace from "../Marketplace/Marketplace";
const { TabPane } = Tabs;

const LoggedRouter = (props) => {
  const defaultTab = props.location.pathname;

  useEffect(() => {
    if (!auth.hasMetadata()) {
      backendService
        .getMetadata()
        .then((response) => {
          auth.saveMetadata(response.data.data);
        })
        .catch((error) => {
          backendService.errorHandler(error.response);
        });
    }

    if (!auth.hasAcceptedTerms()) {
      const btn = (
        <Button type="primary" onClick={() => {
          notification.close("cookie-window");
          auth.saveTerms();
        }}>
          Confirm
        </Button>
      );
      const cookiesNotification = {
        message: 'Data Protection',
        description:
          'This website uses necessary cookies to improve your experience',
        duration: 0,
        btn,
        key: "cookie-window"
      };
      notification.open(cookiesNotification);
    }
  }, []);

  return (
    <MainContainer>
      <UserCard />
      <Switch>
        <Route path={["/news", "/publications"]}>
          <section>
            <Tabs
              defaultActiveKey={defaultTab}
              onTabClick={(key) => props.history.push(key)}
              centered
            >
              <TabPane tab="Publications" key="/publications" />
              <TabPane tab="News" key="/news" />
            </Tabs>
            <Route path={"/news"} exact>
              <NewsFeed />
            </Route>
            <Route path={"/publications"} exact>
              <PublicationsFeed />
            </Route>
          </section>
        </Route>
        <Route path={"/user/:username"}>
          <UserPage />
        </Route>
        <Route path={"/wallets"}>
          <Wallets />
        </Route>
        <Route path={"/nft"}>
          <NftPage />
        </Route>
        <Route path={"/marketplace/:subpage"}>
          <Marketplace />
        </Route>
        <Redirect from="*" to="/publications" />
      </Switch>
    </MainContainer>
  );
};
// @ts-ignore
const PrivateRoute = ({ component: Component, ...rest }) => {
  return (
    <Route
      {...rest}
      render={(props) =>
        auth.isAuthenticated() ? (
          <Component {...props} />
        ) : (
          <Redirect
            to={{
              pathname: "/login",
              state: { from: props.location },
            }}
          />
        )
      }
    />
  );
};

const AppContainer = () => {
  return (
    <Layout style={{ minHeight: "100vh" }}>
      <Header />
      <Switch>
        <Route path="/login">
          <Login />
        </Route>
        <Route path="/register">
          <Register />
        </Route>
        <PrivateRoute path="/" component={LoggedRouter} />
      </Switch>
      <BackTop />
    </Layout>
  );
};

export default AppContainer;
