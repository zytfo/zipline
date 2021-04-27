import React, { FunctionComponent } from "react";
import {
  ActionArea,
  AuthorContainer,
  AuthorTitle,
  DateInfo,
  Likes,
  LikesCount,
  LowerMenuContainer,
  NewsItem,
  NewsTag,
  TagContainer,
} from "../../NewsFeedStyles";
import moment from "moment";
import { Divider, Image, Typography } from "antd";
import { HeartOutlined, HeartTwoTone } from "@ant-design/icons/lib";
import { colors } from "../../../../core/helpers/Helpers";
import { withRouter } from "react-router";
const { Title, Paragraph } = Typography;

const NewsFeedItem: FunctionComponent<any> = ({
  item,
  onTagClick,
  onLikeClick,
  openNewsItem,
}) => {
  return (
    <NewsItem>
      <ActionArea onClick={openNewsItem}>
        <AuthorContainer>
          <AuthorTitle>{item.source}</AuthorTitle>
          <DateInfo>{moment(item.created).format("LLL")}</DateInfo>
        </AuthorContainer>
        <Title level={5}>{item.title}</Title>
        <Paragraph>{item.description}</Paragraph>
      </ActionArea>
      {item.imageUrl && <Image alt={item.title} src={item.imageUrl} />}

      <Divider />

      <LowerMenuContainer>
        <Likes>
          {item.selfLiked ? (
            <HeartTwoTone
              onClick={onLikeClick}
              twoToneColor="#eb2f96"
              style={{ fontSize: "24px" }}
            />
          ) : (
            <HeartOutlined
              onClick={onLikeClick}
              style={{ fontSize: "24px" }}
            />
          )}
          <LikesCount>{item.likesCount} liked</LikesCount>
        </Likes>
        <TagContainer>
          {item.tags.map((tag, index) => (
            <NewsTag
              key={index}
              color={colors[index % 10]}
              onClick={() => onTagClick(tag)}
            >
              {tag}
            </NewsTag>
          ))}
        </TagContainer>
      </LowerMenuContainer>
    </NewsItem>
  );
};

export default withRouter(NewsFeedItem);
