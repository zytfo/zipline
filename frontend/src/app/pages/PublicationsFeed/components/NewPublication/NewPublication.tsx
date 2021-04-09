import React, { FunctionComponent, useState } from "react";
import { withRouter } from "react-router";
import { NewPublicationContainer, PublishButton } from "./NewPublicationStyles";
import { Input } from "antd";
const { TextArea } = Input;

const NewPublication: FunctionComponent<any> = ({ onPublish }) => {
  const [newPubFocused, setNewPubFocused] = useState(false);
  const [inputValue, setInputValue] = useState("");

  return (
    <NewPublicationContainer>
      <TextArea
        size="large"
        rows={newPubFocused ? 4 : 1}
        placeholder={"Do you want to share something?"}
        onFocus={() => setNewPubFocused(true)}
        value={inputValue}
        onChange={(e) => setInputValue(e.target.value)}
      />
      {newPubFocused && (
        <PublishButton
          type="primary"
          size="large"
          onClick={() => {
            onPublish(inputValue);
            setNewPubFocused(false);
            setInputValue("");
          }}
        >
          Publish
        </PublishButton>
      )}
    </NewPublicationContainer>
  );
};

export default withRouter(NewPublication);
