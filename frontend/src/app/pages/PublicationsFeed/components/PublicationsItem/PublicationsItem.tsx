import React, { FunctionComponent, useState } from "react";
import { Link, withRouter } from "react-router-dom";
import moment from "moment";
import { publicationsService } from "../../services/PublicationsService";
import { backendService } from "../../../../core/services/BackendService";
import {Button, List, Modal} from "antd";
import Highlighter from "react-highlight-words";
import {
  ActionArea,
  ActionSet,
  AuthorContainer,
  AuthorTitle,
  ConfirmDeletion,
  DateInfo,
  DeleteIcon,
  LikedIcon,
  Likes,
  LikesCount,
  LowerMenuContainer,
  PublicationContent,
  PublicationDivider,
  PublicationItem,
  UnlikeIcon,
} from "./PublicationsItemStyles";
import { QuestionCircleOutlined } from "@ant-design/icons/lib";
import Market from "../../../Marketplace/components/Market/Market";

const PublicationsItem: FunctionComponent<any> = ({
  item,
  onLikeClick,
  openPublication,
  onTickerClick,
  onPublicationDelete,
  metadata,
}) => {
  const [modalOpened, setModalOpened] = useState(false);
  const [liked, setLiked] = useState([]);
  const [visible, setVisible] = useState(false);

  const showUsersWhoLiked = () => {
    publicationsService
      .showLikes(item.publicationId)
      .then((response: any) => {
        setModalOpened(true);
        setLiked(response.data.data);
      })
      .catch((error: any) => {
        backendService.errorHandler(error.response);
      });
  };

  const closeModal = () => {
    setModalOpened(false);
  };

  const tickerClick = (event) => {
    event.stopPropagation();
    const tick = event.target.textContent;
    onTickerClick(tick);
  };

  const onDeletePost = () => {
    publicationsService
      .deletePublication(item.publicationId)
      .then(() => {
        onPublicationDelete(item.publicationId);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  };

  const getTicker = ({ children }) => {
    return (
      <span
        style={{
          color: "#1890ff",
          textDecoration: "underline",
          background: "none",
        }}
        onClick={tickerClick}
      >
        {children.toUpperCase()}
      </span>
    );
  };

  return (
    <>
      <PublicationItem
        isAuthor={metadata?.username === item.createdBy}
        onBlur={() => setVisible(false)}
        visible={visible}
      >
        <ActionArea onClick={openPublication}>
          <AuthorContainer>
            <AuthorTitle>
              <Link
                to={`/user/${item.createdBy}`}
                onClick={(e) => {
                  e.stopPropagation();
                }}
              >
                Posted by {item.createdBy}
              </Link>
            </AuthorTitle>
            <DateInfo>{moment(item.created).format("LLL")}</DateInfo>
          </AuthorContainer>
          <PublicationContent>
            <Highlighter
              searchWords={item.tickers}
              autoEscape={true}
              textToHighlight={item.content}
              highlightTag={getTicker}
            />
            {
              item.tradeIds && item.tradeIds.length !== 0 && <Market publicationPositions={item.marketTradeDTOs} positions={"all_open"}/>
            }
          </PublicationContent>
        </ActionArea>

        <PublicationDivider />

        <LowerMenuContainer>
          <Likes>
            {item.selfLiked ? (
              <LikedIcon onClick={onLikeClick} twoToneColor="#eb2f96" />
            ) : (
              <UnlikeIcon onClick={onLikeClick} />
            )}
            <LikesCount onClick={showUsersWhoLiked}>
              {item.likesCount} liked
            </LikesCount>
          </Likes>
          <ActionSet>
            {/*<EditIcon />*/}
            <ConfirmDeletion
              placement="top"
              title={"Delete this publication?"}
              icon={<QuestionCircleOutlined style={{ color: "red" }} />}
              onConfirm={onDeletePost}
              okText="Yes"
              cancelText="No"
            >
              <DeleteIcon twoToneColor="red" onClick={() => setVisible(true)} />
            </ConfirmDeletion>
          </ActionSet>
        </LowerMenuContainer>
      </PublicationItem>

      <Modal
        visible={modalOpened}
        title={`${item.likesCount} liked`}
        onCancel={closeModal}
        footer={[
          <Button key="back" onClick={closeModal}>
            Close
          </Button>,
        ]}
      >
        <List
          style={{ maxHeight: "300px", overflow: "scroll" }}
          size="large"
          dataSource={liked}
          locale={{ emptyText: "Nobody liked this publication" }}
          renderItem={(item: any) => <List.Item>{item.username}</List.Item>}
        />
      </Modal>
    </>
  );
};

export default withRouter(PublicationsItem);
