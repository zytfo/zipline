import React, { FunctionComponent, useEffect, useState } from "react";
import { Link, withRouter } from "react-router-dom";
import moment from "moment";
import { QuestionCircleOutlined } from "@ant-design/icons/lib";
import { publicationsService } from "../../services/PublicationsService";
import { backendService } from "../../../../core/services/BackendService";
import { Button, List, Modal } from "antd";
import Highlighter from "react-highlight-words";
import { PublicationObject } from "../../interfaces/PublicationItem";
import {
  AuthorContainer,
  AuthorTitle,
  CommentDivider,
  ConfirmDeletion,
  DateInfo,
  DeleteIcon,
  EditIcon,
  LikedIcon,
  Likes,
  LikesCount,
  LowerMenuContainer,
  UnlikeIcon,
} from "./PublicationModalStyles";
import Comments from "../../../../core/components/Comments/Comments";
import Market from "../../../Marketplace/components/Market/Market";

const PublicationModal: FunctionComponent<any> = ({
  itemId,
  visible,
  onLike,
  closePublication,
  onTickerClick,
  onPublicationDelete,
  metadata,
}) => {
  const [modalOpened, setModalOpened] = useState(false);
  const [publication, setPublication] = useState<PublicationObject | null>(
    null
  );
  const [liked, setLiked] = useState([]);

  useEffect(() => {
    openPublication(itemId);
  }, []);

  /**
   * Show all the user who like publication
   */
  const showUsersWhoLiked = () => {
    publicationsService
      .showLikes(itemId)
      .then((response: any) => {
        setModalOpened(true);
        setLiked(response.data.data);
      })
      .catch((error: any) => {
        backendService.errorHandler(error.response);
      });
  };

  /**
   * Fetch data about one publication post
   * @param id of publication
   */
  const openPublication = (id: number) => {
    publicationsService
      .getPublicationById(id)
      .then((response: any) => {
        setPublication(response.data.data);
      })
      .catch((error: any) => {
        closePublicationModal();
        backendService.errorHandler(error.response);
      });
  };

  /**
   * Like or unlike Publication
   */
  const likePost = () => {
    let newPub: any = publication;
    newPub.selfLiked = !newPub.selfLiked;
    newPub.likesCount += newPub.selfLiked ? 1 : -1;

    onLike(itemId);
  };

  /**
   * Close publication modal
   */
  const closePublicationModal = () => {
    setPublication(null);
    closePublication();
  };

  /**
   * Close modal that shows users who liked publication
   */
  const closeModal = () => {
    setModalOpened(false);
  };

  /**
   * Emits parents function on ticker click
   * @param event
   */
  const tickerClick = (event) => {
    event.stopPropagation();
    const tick = event.target.textContent;
    onTickerClick(tick);
  };

  /**
   * Delete publication
   */
  const onDeletePost = () => {
    if (publication) {
      publicationsService
        .deletePublication(publication.publicationId)
        .then(() => {
          onPublicationDelete(publication.publicationId);
          closePublicationModal();
        })
        .catch((error) => {
          backendService.errorHandler(error.response);
        });
    }
  };

  const getTicker = ({ children }) => {
    return (
      <span
        style={{
          color: "#1890ff",
          textDecoration: "underline",
          background: "none",
          cursor: "pointer",
        }}
        onClick={tickerClick}
      >
        {children}
      </span>
    );
  };

  return (
    <>
      <Modal
        visible={visible}
        title="Publication Post"
        onCancel={closePublicationModal}
        width={800}
        footer={[
          <div key="footer">
            <LowerMenuContainer>
              <Likes>
                {publication?.selfLiked ? (
                  <LikedIcon onClick={likePost} twoToneColor="#eb2f96" />
                ) : (
                  <UnlikeIcon onClick={likePost} />
                )}
                <LikesCount onClick={showUsersWhoLiked}>
                  {publication?.likesCount} liked
                </LikesCount>
              </Likes>
              {metadata?.username === publication?.createdBy && (
                <div>
                  <EditIcon />
                  <ConfirmDeletion
                    placement="top"
                    title={"Delete this publication?"}
                    icon={<QuestionCircleOutlined style={{ color: "red" }} />}
                    onConfirm={onDeletePost}
                    okText="Yes"
                    cancelText="No"
                  >
                    <DeleteIcon />
                  </ConfirmDeletion>
                </div>
              )}
            </LowerMenuContainer>
            <CommentDivider />
            <Comments key="comm" postId={itemId} postType="PUBLICATION" />
          </div>,
        ]}
      >
        <AuthorContainer>
          <AuthorTitle>
            <Link
              to={`/user/${publication?.createdBy}`}
              onClick={(e) => {
                e.stopPropagation();
              }}
            >
              {publication?.createdBy}
            </Link>
          </AuthorTitle>
          <DateInfo>{moment(publication?.created).format("LLL")}</DateInfo>
        </AuthorContainer>
        <Highlighter
          searchWords={publication?.tickers || []}
          autoEscape={true}
          textToHighlight={publication?.content || ""}
          highlightTag={getTicker}
        />
        {
          publication?.tradeIds && <Market publicationPositions={publication?.marketTradeDTOs} positions={"all_open"}/>
        }
      </Modal>

      <Modal
        visible={modalOpened}
        title={`${publication?.likesCount} liked`}
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

export default withRouter(PublicationModal);
