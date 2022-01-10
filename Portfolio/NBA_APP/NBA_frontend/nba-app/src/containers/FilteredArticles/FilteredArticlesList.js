import React from "react";
import { List, ListItem, Text } from "@chakra-ui/react";
import SingleArticle from "./SingleArticle";

const filteredArticlesList = (props) =>
  props.filteredArticles.length === 0 ? (
    <Text>Brak artykułów spełniajacych kryteria wyszukiwania</Text>
  ) : (
    <List spacing={3}>
      {props.filteredArticles.map((article) => {
        return (
          <ListItem key={article.id}>
            <SingleArticle
              id={article.id}
              title={article.title}
              tags={article.tags}
              content={article.content}
              comments={article.comments.length}
              date={article.dateOfPublication.split("T")[0]}
            />
          </ListItem>
        );
      })}
    </List>
  );

export default filteredArticlesList;
