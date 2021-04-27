import React, { FunctionComponent, useEffect, useRef } from "react";
import { withRouter } from "react-router";

const InfiniteScroll: FunctionComponent<any> = ({
  fetchData,
  loader,
  componentData,
  endline,
  isMoreContent,
}) => {
  let prevY = 0;

  const loadingRef = useRef(null);
  let options = {
    root: null,
    rootMargin: "0px",
    threshold: 1.0,
  };

  useEffect(() => {
    fetchData();

    const observer = new IntersectionObserver(handleObserver, options);
    // @ts-ignore
    observer.observe(loadingRef.current);
  }, []);

  const handleObserver = (entities: any, observer: any) => {
    const y = entities[0].boundingClientRect.y;
    if (prevY > y) {
      fetchData();
    }
    prevY = y;
  };

  return (
    <div>
      {componentData}
      {isMoreContent ? endline : <div ref={loadingRef}> {loader} </div>}
    </div>
  );
};

export default withRouter(InfiniteScroll);
