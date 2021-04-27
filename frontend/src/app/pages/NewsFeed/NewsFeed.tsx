import React, { useEffect, useState } from "react";
import {
  AuthorContainer,
  AuthorTitle,
  DateInfo,
  Likes,
  LikesCount,
  LowerMenuContainer,
  NewsContainer,
  NewsLayout,
  NewsSearch,
  NewsTag,
  TagContainer,
} from "./NewsFeedStyles";
import { useLocation, withRouter } from "react-router-dom";
import { Skeleton, Typography, Image, Modal, Result } from "antd";
import { newsService } from "./services/NewsService";
import { backendService } from "../../core/services/BackendService";
import InfiniteScroll from "../../core/components/InfiniteScroll/InfiniteScroll";
import { NewsObject } from "./interfaces/NewsFeed";
import moment from "moment";
import { HeartOutlined, HeartTwoTone } from "@ant-design/icons/lib";
import { colors } from "../../core/helpers/Helpers";
import NewsFeedItem from "./components/NewsFeedItem/NewsFeedItem";
import TagsSearch from "./components/TagsSearch/TagsSearch";
import { CommentDivider } from "../PublicationsFeed/components/PublicationModal/PublicationModalStyles";
import Comments from "../../core/components/Comments/Comments";
const { Paragraph } = Typography;

const NewsFeed = (props: any) => {
  const [content, setContent] = useState<NewsObject[]>([]);
  const [visible, setVisible] = useState<boolean>(false);
  const [newsItem, setNewsItem] = useState<NewsObject | null>(null);
  const [tagsToSearch, setTagsToSearch] = useState<string[]>([]);
  const [renderBlocker, setRenderBlocker] = useState(true);
  const [isEndOfContent, setIsEndOfContent] = useState(false);
  const [searchContent, setSearchContent] = useState("");
  const [previousLocation, setPreviousLocation] = useState("");
  const queryService = new URLSearchParams(useLocation().search);
  let pageNumber: number = 0;

  /**
   * Open News post if link receives parameter
   * Search by value and tags if link receives parameters
   */
  useEffect(() => {
    const page = queryService.get("n");
    if (page) {
      openNewsItem(Number.parseInt(page));
    }

    const searchField = queryService.get("sv");
    const tags = queryService.get("tg");
    if (searchField !== null && tags !== null) {
      setRenderBlocker(true);
      pageNumber = 0;

      setSearchContent(searchField);
      if (tags !== "") {
        setTagsToSearch(tags.split("-"));
      } else {
        setTagsToSearch([]);
      }

      setIsEndOfContent(false);
      setContent([]);
    }
  }, [props.location.key]);

  /**
   * Remove blocker as soon as tags added
   */
  useEffect(() => {
    setRenderBlocker(false);
  }, [tagsToSearch, searchContent]);

  /**
   * Fetch one page of news
   */
  const getNews = () => {
    newsService
      .getNews(pageNumber, tagsToSearch, searchContent)
      .then((response: any) => {
        setContent((oldContent) => [...oldContent, ...response.data.data]);
        if (response.data.data.length < 10) {
          setIsEndOfContent(true);
        }
        pageNumber++;
      })
      .catch((error: any) => {
        backendService.errorHandler(error.response);
      });
  };

  /**
   * Change url and open news item
   * @param id
   */
  const changeUrlAndOpenItem = (id: number) => {
    setPreviousLocation(props.location.search);
    props.history.push(`/news?n=${id}`);
  };

  /**
   * Fetch data about one news post
   * @param id of news post
   */
  const openNewsItem = (id: number) => {
    newsService
      .getNewsById(id)
      .then((response: any) => {
        setVisible(true);
        setNewsItem(response.data.data);
      })
      .catch((error: any) => {
        backendService.errorHandler(error.response);
      });
  };

  /**
   * Close News Post Dialog
   */
  const closeModal = () => {
    props.history.push(`/news${previousLocation}`);
    setVisible(false);
    setNewsItem(null);
  };

  /**
   * Like or unlike News Post
   * @param item
   */
  const likePost = (item: NewsObject) => {
    newsService
      .likeNews(item.newsId)
      .then(() => {
        const index = content.findIndex((element: NewsObject) => {
          return element.newsId === item.newsId;
        });
        let newContent = [...content];

        newContent[index].selfLiked = !item.selfLiked;
        newContent[index].likesCount += newContent[index].selfLiked ? 1 : -1;

        setContent(newContent);

        if (newsItem) {
          setNewsItem(newContent[index]);
        }
      })
      .catch((error: any) => {
        backendService.errorHandler(error.response);
      });
  };

  /**
   * Search for news
   * @param value
   */
  const onSearch = (value: string) => {
    updateSearchHistory(tagsToSearch, value);
  };

  /**
   * Search news by tag with rerunning Infinite Scroll
   * @param tag
   */
  const getNewsByTag = (tag: string) => {
    const index = tagsToSearch.indexOf(tag);
    if (index === -1) {
      updateSearchHistory([...tagsToSearch, tag], searchContent);
    } else {
      updateSearchHistory([...tagsToSearch], searchContent);
    }
  };

  /**
   * Search news by tags with rerunning Infinite Scroll
   * @param tags
   */
  const addTagsToSearch = (tags: string[]) => {
    updateSearchHistory(tags, searchContent);
  };

  /**
   * Update link for search
   * @param tags
   * @param searchValue
   */
  const updateSearchHistory = (tags: string[], searchValue: string) => {
    props.history.push(`/news?sv=${searchValue}&tg=${tags.join("-")}`);
  };

  /**
   * Render Infinite Scroll
   */
  const renderContentWithInfiniteScroll = () => {
    if (!renderBlocker)
      return (
        <InfiniteScroll
          fetchData={getNews}
          loader={<Skeleton avatar paragraph={{ rows: 4 }} active />}
          endline={<Result status="success" title="You reached the end!" />}
          isMoreContent={isEndOfContent}
          componentData={content?.map((item, index) => (
            <NewsFeedItem
              key={index}
              item={item}
              openNewsItem={() => changeUrlAndOpenItem(item.newsId)}
              onLikeClick={() => likePost(item)}
              onTagClick={getNewsByTag}
            />
          ))}
        />
      );
  };

  return (
    <NewsLayout>
      <NewsContainer>
        <TagsSearch
          label="tag"
          tags={tagsToSearch}
          updateTags={addTagsToSearch}
        />
        <NewsSearch
          placeholder="Search..."
          allowClear
          defaultValue={queryService.get("sv") || ""}
          onSearch={onSearch}
          size="large"
          enterButton
        />
        {renderContentWithInfiniteScroll()}
      </NewsContainer>

      {newsItem && (
        <Modal
          visible={visible}
          title={newsItem?.title}
          onCancel={closeModal}
          width={800}
          footer={[
            <div key="footer">
              <LowerMenuContainer>
                <Likes onClick={() => likePost(newsItem)}>
                  {newsItem.selfLiked ? (
                    <HeartTwoTone
                      twoToneColor="#eb2f96"
                      style={{ fontSize: "24px" }}
                    />
                  ) : (
                    <HeartOutlined style={{ fontSize: "24px" }} />
                  )}
                  <LikesCount>{newsItem.likesCount} liked</LikesCount>
                </Likes>
                <TagContainer>
                  {newsItem?.tags.map((tag, index) => (
                    <NewsTag
                      key={index}
                      color={colors[index % 10]}
                      onClick={() => {
                        setVisible(false);
                        setNewsItem(null);
                        getNewsByTag(tag);
                      }}
                    >
                      {tag}
                    </NewsTag>
                  ))}
                </TagContainer>
              </LowerMenuContainer>
              <CommentDivider />
              <Comments key="comment" postId={newsItem.newsId} postType="NEWS" />
            </div>,
          ]}
        >
          <Paragraph>{newsItem?.content}</Paragraph>
          {newsItem.imageUrl && (
            <Image alt={newsItem.title} src={newsItem.imageUrl} />
          )}
          <AuthorContainer>
            <AuthorTitle>{newsItem?.source}</AuthorTitle>
            <DateInfo>{moment(newsItem?.created).format("LLL")}</DateInfo>
          </AuthorContainer>
        </Modal>
      )}
    </NewsLayout>
  );
};

export default withRouter(NewsFeed);
