import React, { useEffect, useState } from "react";
import { useLocation, withRouter } from "react-router-dom";
import { publicationsService } from "./services/PublicationsService";
import { backendService } from "../../core/services/BackendService";
import {
  PublicationLayout,
  PublicationsContainer,
} from "./PublicationsFeedStyles";
import InfiniteScroll from "../../core/components/InfiniteScroll/InfiniteScroll";
import { Result, Skeleton } from "antd";
import { PublicationObject } from "./interfaces/PublicationItem";
import PublicationsItem from "./components/PublicationsItem/PublicationsItem";
import PublicationModal from "./components/PublicationModal/PublicationModal";
import TagsSearch from "../NewsFeed/components/TagsSearch/TagsSearch";
import NewPublication from "./components/NewPublication/NewPublication";
import { User } from "../../core/interfaces/User";
import auth from "../../core/services/AuthService";

const PublicationsFeed = (props: any) => {
  const [content, setContent] = useState<PublicationObject[]>([]);
  const [isEndOfContent, setIsEndOfContent] = useState(false);
  const [renderBlocker, setRenderBlocker] = useState(true);

  const [visible, setVisible] = useState<boolean>(false);
  const [openedPub, setOpenedPub] = useState<number | null>(null);
  const [previousLocation, setPreviousLocation] = useState("");

  const [tickerToSearch, setTickerToSearch] = useState<string[]>([]);

  const metadata: User = auth.getMetadata();

  const queryService = new URLSearchParams(useLocation().search);
  let pageNumber: number = 0;

  /**
   * Open Publication post if link receives parameter
   */
  useEffect(() => {
    const page = queryService.get("n");
    if (page) {
      setVisible(true);
      setOpenedPub(Number.parseInt(page));
    }

    const ticker = queryService.get("t");

    if (ticker !== null && ticker !== tickerToSearch.join("-")) {
      setRenderBlocker(true);
      setVisible(false);
      pageNumber = 0;
      if (ticker !== "") {
        setTickerToSearch(ticker.split("-"));
      } else {
        setTickerToSearch([]);
      }

      setIsEndOfContent(false);
      setContent([]);
    }
  }, [props.location.key]);

  /**
   * Remove blocker when tickers are set
   */
  useEffect(() => {
    setRenderBlocker(false);
  }, [tickerToSearch]);

  /**
   * Get page of Publications
   */
  const getPublications = () => {
    const username = props.author || null;
    publicationsService
      .getPublications(pageNumber, tickerToSearch, username)
      .then((response) => {
        setContent((oldContent) => [...oldContent, ...response.data.data]);
        if (response.data.data.length < 10) {
          setIsEndOfContent(true);
        }
        pageNumber++;
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  };

  const addTickerToSearch = (tickers: string[]) => {
    updateSearchHistory(tickers);
  };

  /**
   * Close Publication Post Dialog
   */
  const closeModal = () => {
    props.history.push(`${props.location.pathname}${previousLocation}`);
    setVisible(false);
  };

  /**
   * Change url and open publication
   * @param id
   */
  const changeUrlAndOpenItem = (id: number) => {
    setPreviousLocation(props.location.search);
    props.history.push(`${props.location.pathname}?n=${id}`);
  };

  /**
   * Like or unlike Publication
   * @param id
   */
  const likePost = (id: number) => {
    publicationsService
      .likePublication(id)
      .then(() => {
        const index = content.findIndex((element: PublicationObject) => {
          return element.publicationId === id;
        });
        let newContent = [...content];

        newContent[index].selfLiked = !newContent[index].selfLiked;
        newContent[index].likesCount += newContent[index].selfLiked ? 1 : -1;

        setContent(newContent);
      })
      .catch((error: any) => {
        backendService.errorHandler(error.response);
      });
  };

  /**
   * Update link for search
   * @param tickers
   */
  const updateSearchHistory = (tickers: string[]) => {
    props.history.push(`${props.location.pathname}?t=${tickers.join("-")}`);
  };

  /**
   * Publish new publication
   * @param publicationValue
   * @param chosenTrades
   */
  const publishPublication = (publicationValue: string, chosenTrades: any[]) => {
    const publicationDTO = {
      content: publicationValue,
      tradeIds: chosenTrades
    };
    publicationsService
      .newPublication(publicationDTO)
      .then((response) => {
        setContent((oldContent) => [response.data.data, ...oldContent]);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  };

  /**
   * Delete publication
   * @param id
   */
  const deletePublication = (id: number) => {
    const index = content.findIndex((elem) => {
      return elem.publicationId === id;
    });
    const newArray = [...content];
    newArray.splice(index, 1);
    setContent(newArray);
  };

  /**
   * Render Infinite Scroll
   */
  const renderContentWithInfiniteScroll = () => {
    if (!renderBlocker)
      return (
        <InfiniteScroll
          fetchData={getPublications}
          loader={<Skeleton avatar paragraph={{ rows: 4 }} active />}
          endline={<Result status="success" title="You reached the end!" />}
          isMoreContent={isEndOfContent}
          componentData={content?.map((item) => (
            <PublicationsItem
              key={item.publicationId}
              item={item}
              metadata={metadata}
              openPublication={() => {
                changeUrlAndOpenItem(item.publicationId);
              }}
              onLikeClick={() => likePost(item.publicationId)}
              onTickerClick={(ticker) => {
                if (!tickerToSearch.includes(ticker)) {
                  addTickerToSearch([ticker]);
                }
              }}
              onPublicationDelete={deletePublication}
            />
          ))}
        />
      );
  };

  return (
    <PublicationLayout>
      <PublicationsContainer>
        {(!props.author || props.author === metadata.username) && <NewPublication onPublish={publishPublication} />}
        <TagsSearch
          label="ticker"
          tags={tickerToSearch}
          updateTags={addTickerToSearch}
        />
        {renderContentWithInfiniteScroll()}
      </PublicationsContainer>

      {visible && (
        <PublicationModal
          itemId={openedPub}
          visible={visible}
          metadata={metadata}
          onLike={(id) => {
            likePost(id);
          }}
          closePublication={closeModal}
          onTickerClick={(ticker) => {
            if (!tickerToSearch.includes(ticker)) {
              addTickerToSearch([ticker]);
              setVisible(false);
            }
          }}
          onPublicationDelete={deletePublication}
        />
      )}
    </PublicationLayout>
  );
};

export default withRouter(PublicationsFeed);
