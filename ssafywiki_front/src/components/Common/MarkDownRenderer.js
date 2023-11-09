
import MDX from '@mdx-js/runtime'
import { Link } from "react-router-dom";

const MoveDocs = ({children, docs}) => (
  <Link to={`/res/content/1/싸피위키:대문`}>
    {children}
  </Link>
)

const components = {
  MoveDocs// 여기서 'Highlight'는 MDX에서 사용할 컴포넌트 이름을 나타냅니다.
};


const MarkdownRenderer = ({ content }) => {
  return (
    <div>
      <MDX components={components}>{content}</MDX>
    </div>
  );
};

export default MarkdownRenderer;
