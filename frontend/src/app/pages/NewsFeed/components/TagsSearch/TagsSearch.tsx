import React, { FunctionComponent, useEffect, useRef, useState } from "react";
import { withRouter } from "react-router";
import { PlusOutlined } from "@ant-design/icons/lib";
import {AddTag, EditTag, TagInput} from "./TagsSearchStyles";

const TagsSearch: FunctionComponent<any> = ({ tags, updateTags, label }) => {
  const [editInputIndex, setEditInputIndex] = useState(-1);
  const [editInputValue, setEditInputValue] = useState("");
  const [inputVisible, setInputVisible] = useState(false);
  const [inputValue, setInputValue] = useState("");
  const editInput = useRef<any>(null);
  const inputRef = useRef<any>(null);

  useEffect(() => {
    if (editInput.current) {
      editInput.current.focus();
    }
  }, [editInputIndex, editInputValue]);

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, [inputVisible]);

  const showInput = () => {
    setInputVisible(true);
  };

  const handleInputChange = (e) => {
    setInputValue(e.target.value);
  };

  const handleEditInputChange = (e) => {
    setEditInputValue(e.target.value);
  };

  const handleClose = (removedTag) => {
    const newTags = tags.filter((tag) => tag !== removedTag);
    updateTags(newTags);
  };

  const handleInputConfirm = () => {
    let newTags = tags;
    if (inputValue && tags.indexOf(inputValue) === -1) {
      newTags = [...tags, inputValue];
      updateTags(newTags);
    }

    setInputVisible(false);
    setInputValue("");
  };

  const handleEditInputConfirm = () => {
    const newTags = [...tags];

    if (editInputValue === "") {
      newTags.splice(editInputIndex, 1);
    } else {
      newTags[editInputIndex] = editInputValue;
    }

    updateTags(newTags);
    setEditInputIndex(-1);
    setEditInputValue("");
  };

  return (
    <>
      {tags.map((tag, index) => {
        if (editInputIndex === index) {
          return (
            <TagInput
              ref={editInput}
              key={tag}
              size="small"
              value={editInputValue}
              onChange={handleEditInputChange}
              onBlur={handleEditInputConfirm}
              onPressEnter={handleEditInputConfirm}
            />
          );
        }
        return (
          <EditTag
            key={tag}
            closable={true}
            onClose={() => handleClose(tag)}
          >
            <span
              onDoubleClick={(e) => {
                setEditInputIndex(index);
                setEditInputValue(tag);
                e.preventDefault();
              }}
            >
              {tag}
            </span>
          </EditTag>
        );
      })}
      {inputVisible && (
        <TagInput
          ref={inputRef}
          type="text"
          size="small"
          value={inputValue}
          onChange={handleInputChange}
          onBlur={handleInputConfirm}
          onPressEnter={handleInputConfirm}
        />
      )}
      {!inputVisible && (
        <AddTag className="site-tag-plus" onClick={showInput}>
          <PlusOutlined /> Add {label}
        </AddTag>
      )}
    </>
  );
};

export default withRouter(TagsSearch);
