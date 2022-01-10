import React from "react";
import { Grid, GridItem } from "@chakra-ui/react";
import PopularArticlesList from "../../../containers/PopularArticles/PopularArticlesList";
import SingleArticleView from "./SingleArticleView";
import axios from "axios";

const SingleArticleLayout = () => {
  const [popularArticles, setPopularArticles] = React.useState([]);

  React.useEffect(() => {
    axios
      .get("http://localhost:8080/nba/articles/popular/5")
      .then((response) => {
        setPopularArticles(response.data);
      });
  }, []);

  return (
    <Grid templateColumns="repeat(3, 1fr)" gap={10} height="200px">
      <GridItem colSpan={2}>
        <SingleArticleView />
      </GridItem>
      <GridItem colSpan={1}>
        <PopularArticlesList popularArticles={popularArticles} />
      </GridItem>
    </Grid>
  );
};

export default SingleArticleLayout;
