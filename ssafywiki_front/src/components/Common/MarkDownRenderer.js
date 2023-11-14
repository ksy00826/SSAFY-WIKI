import MDX from "@mdx-js/runtime";
import { Link } from "react-router-dom";
import style from "./Component.module.css";
import { getSearchDoc } from "utils/DocsApi";
import { useState, useEffect, createContext, useContext, useRef } from "react";
const MoveDocs = ({ children, docs }) => {
  const [url, setUrl] = useState("");

  const onSearch = (keyword) => {
    getSearchDoc(keyword)
      .then((response) => {
        const output = response.data.hits.hits;
        const newSearched = output.map((element) => {
          return {
            label: element._source.docs_title,
            value: element._source.docs_id,
          };
        });
        if (newSearched.length > 0 && newSearched[0].label === keyword) {
          setUrl(
            `/res/content/${newSearched[0].value}/${newSearched[0].label}`
          );
        } else {
          setUrl(`/res/list?title=${keyword}`);
        }
      })
      .catch((error) => {
        console.error("Error searching documents:", error);
        setUrl(`/res/list?title=${keyword}`);
      });
  };

  // onSearch 함수를 호출해서 url 상태를 설정
  useEffect(() => {
    if (docs) {
      onSearch(docs);
    }
  }, [docs]);

  return (
    <Link to={url} className={style.MoveDocs}>
      {children}
    </Link>
  );
};

const Note = ({ children }) => {
  // 툴팁 표시 상태를 관리하기 위한 state를 선언합니다.
  const [isTooltipVisible, setTooltipVisible] = useState(false);

  return (
    <span
      className={style.noteContainer}
      onMouseEnter={() => setTooltipVisible(true)} // 마우스를 올리면 툴팁을 보여줍니다.
      onMouseLeave={() => setTooltipVisible(false)} // 마우스를 떼면 툴팁을 숨깁니다.
    >
      <span className={style.Note}>[주]</span>
      {isTooltipVisible && (
        <div className={style.tooltip}>
          <span className={style.toolText}>{children}</span>
        </div>
      )}
    </span>
  );
};

const LinkTo = ({ children, link }) => {
  const absoluteLink =
    link.includes("http://") || link.includes("https://")
      ? link
      : `https://${link}`;
  //console.log(absoluteLink);
  return (
    <a href={absoluteLink} className={style.LinkTo}>
      #{children}
    </a>
  );
};

const TableOfContentsContext = createContext();

const TableOfContentsProvider = ({ children }) => {
  const [toc, setToc] = useState([]);
  const headingIdCounter = useRef(0);

  return (
    <TableOfContentsContext.Provider value={{ toc, setToc, headingIdCounter  }}>
      {children}
    </TableOfContentsContext.Provider>
  );
};

const Subheading = ({ children }) => {
  const { setToc, headingIdCounter } = useContext(TableOfContentsContext);
  const [id, setId] = useState('');

  useEffect(() => {
    const newId = `heading-${headingIdCounter.current++}`;
    setId(newId);
    setToc((prev) => [...prev, { id: newId, title: children }]);
    return () => setToc((prev) => prev.filter((item) => item.id !== newId));
  }, [children]);
  return <h1 id={id}>{children}</h1>;
};

const TableOfContents = () => {
  const { toc } = useContext(TableOfContentsContext);

  const handleClick = (id) => {
    const element = document.getElementById(id);
    if (element) {
      element.scrollIntoView();
    } else {
      console.error("Element not found:", id);
    }
  };

  return (
    <ol className={style.index}>
      <p style={{margin:0, fontSize:"1.5rem"}}>목차</p>
      {toc.map((item) => (
        <li key={item.id} onClick={() => handleClick(item.id)}>
          {item.title}
        </li>
      ))}
    </ol>
  );
};

const components = {
  MoveDocs,
  Note,
  LinkTo,
  h1: Subheading,
};

const MarkdownRenderer = ({ content }) => {
  return (
    <div>
      <TableOfContentsProvider>
        <TableOfContents />
        <MDX components={components}>{content}</MDX>
      </TableOfContentsProvider>
    </div>
  );
};

export default MarkdownRenderer;
