import React from "react";
import { List, ListItem, Text, Divider, Stack } from "@chakra-ui/react";
import SinglePopularArticle from "./SinglePopularArticle";

const popularArticlesList = (props) => (
  <Stack margin={5} padding="2px" p={5} shadow="lg" borderWidth="1px">
    <Text>Popularne artykuły</Text>
    <Divider />
    <List spacing={3}>
      {props.popularArticles.map((article) => {
        console.log(article);
        return (
          <ListItem key={article.id}>
            <SinglePopularArticle article={article} />
          </ListItem>
        );
      })}
    </List>
  </Stack>
);
export default popularArticlesList;
