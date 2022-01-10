import {
  Avatar,
  Text,
  Grid,
  Box,
  GridItem,
  Heading,
  Flex,
  WrapItem,
} from "@chakra-ui/react";
import React from "react";
import { getArticlesByAuthor, deleteArticle } from "../../Api";
import ArticleToEditView from "./ArticleToEditView";
import { UserContext } from "../../../App";

const EditDeleteArticle = (props) => {
  const [articles, setArticles] = React.useState([]);
  const [selectedArticle, setSelectedArticle] = React.useState();
  const { user } = React.useContext(UserContext);

  React.useEffect(() => {
    getArticlesByAuthor(user.id).then((response) => {
      setArticles(response.data);
    });
  }, [props.counter]);

  return (
    <Grid h="200px" templateColumns="repeat(3, 1fr)" gap={4}>
      <GridItem colSpan={1}>
        {articles.map((article) => {
          return (
            <Flex
              margin={5}
              p={5}
              shadow="md"
              borderWidth="1px"
              key={article.id}
              onClick={() => {
                setSelectedArticle(article);
                console.log(article);
              }}
            >
              <Avatar
                name={article.publicist.login}
                src="https://bit.ly/broken-link"
              />

              <Heading fontSize="l" marginLeft={2} textAlign="left">
                {article.title}
              </Heading>
            </Flex>
          );
        })}
      </GridItem>

      <GridItem colSpan={2}>
        <ArticleToEditView
          changeCounter={props.changeCounter}
          article={selectedArticle}
          deleteArticle={(articleID) => {
            deleteArticle(articleID).then(() => {
              setArticles(
                articles.filter((article) => article.id !== Number(articleID))
              );
              setSelectedArticle(undefined);
            });
          }}
        />
      </GridItem>
    </Grid>
  );
};

export default EditDeleteArticle;
