import React, { useEffect, useState } from "react";
import { Button, Form, Input, Modal } from "antd";
import { Credentials } from "./interfaces/User";
import { LockOutlined, UserOutlined } from "@ant-design/icons/lib";
import { loginService } from "./services/LoginService";
import auth from "../../core/services/AuthService";
import { LoginLayout } from "./LoginStyles";
import {Link, Redirect, useLocation, withRouter} from "react-router-dom";
import { backendService } from "../../core/services/BackendService";

const Login = (props: any) => {
  const [pendingCall, setPendingCall] = useState(false);
  const queryService = new URLSearchParams(useLocation().search);

  useEffect(() => {
    const param = queryService.get("");
    switch (param) {
      case "conf":
        Modal.success({
          title: "E-mail Confirmed!",
          content: (
            <div>
              <p>E-mail was successfully confirmed! Now you can log in.</p>
            </div>
          ),
        });
        break;
      case "reg":
        Modal.info({
          title: "Please, confirm your email",
          content: (
            <div>
              <p>
                Click the link the message that was sent to your email to
                activate your account.
              </p>
            </div>
          ),
        });
        break;
    }
  });

  if (auth.isAuthenticated()) {
    return <Redirect to={{
      pathname: "/"
    }}
    />
  }

  const onSubmit = (values: Credentials) => {
    setPendingCall(true);
    loginService
      .login(values.username, values.password)
      .then((response: any) => {
        setPendingCall(false);
        auth.login(response.data.data);
        props.history.push("/publications");
      })
      .catch((errorResponse: any) => {
        setPendingCall(false);
        backendService.errorHandler(errorResponse.response);
      });
  };

  const layout = {
    wrapperCol: { sm: { offset: 8, span: 8 } },
  };

  return (
    <LoginLayout>
      <Form {...layout} name="basic" onFinish={onSubmit} size="large">
        <Form.Item
          name="username"
          rules={[{ required: true, message: "Please input your username!" }]}
        >
          <Input
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="Username"
          />
        </Form.Item>

        <Form.Item
          name="password"
          rules={[{ required: true, message: "Please input your password!" }]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="Password"
          />
        </Form.Item>

        <Form.Item {...layout}>
          <Button loading={pendingCall} type="primary" htmlType="submit" block>
            Submit
          </Button>
          Or <Link to={"/register"}>register now!</Link>
        </Form.Item>
      </Form>
    </LoginLayout>
  );
};

export default withRouter(Login);
