import React from "react";
import ArticlesNavigation from "./ArticlesNavigation";
import FilteredArticlesList from "./FilteredArticlesList";
import { Stack } from "@chakra-ui/react";
const filteredArticles = (props) => (
  <Stack align="center" margin={5}>
    <FilteredArticlesList filteredArticles={props.filteredArticles} />
    <ArticlesNavigation />
  </Stack>
);

export default filteredArticles;
