import React, { useState } from "react";
import { message, Button, Form, Input } from "antd";
import { UserData } from "./interfaces/UserData";
import {
  LockOutlined,
  MailOutlined,
  UserOutlined,
} from "@ant-design/icons/lib";
import { registerService } from "./services/RegisterService";
import { RegisterLayout } from "./RegisterStyles";
import {Link, Redirect, withRouter} from "react-router-dom";
import { backendService } from "../../core/services/BackendService";
import auth from "../../core/services/AuthService";

const Register = (props: any) => {
  const [pendingCall, setPendingCall] = useState(false);

  if (auth.isAuthenticated()) {
    return <Redirect to={{
      pathname: "/"
    }}
    />
  }

  const onSubmit = (values: UserData) => {
    setPendingCall(true);
    values.role = [];
    registerService
      .register(values)
      .then((response: any) => {
        setPendingCall(false);
        message.success(response.data.data);
        props.history.push("/login?=reg");
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
    <RegisterLayout>
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
          name="email"
          rules={[{ required: true, message: "Please input your E-mail!" }]}
        >
          <Input
            prefix={<MailOutlined className="site-form-item-icon" />}
            placeholder="E-Mail"
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
            Register
          </Button>
          Already registered? <Link to={"/login"}>Log in</Link>.
        </Form.Item>
      </Form>
    </RegisterLayout>
  );
};

export default withRouter(Register);
