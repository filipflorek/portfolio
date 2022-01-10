import React, { Component } from "react";
import { RadioGroup, Stack, Radio } from "@chakra-ui/react";
import ArticleSearch from "./ArticleSearch";
import ArticleCategories from "./ArticleCategories";

class ArticlesFilter extends Component {
  render() {
    return (
      <Stack margin={5}>
        <ArticleSearch search={this.props.search} />
        <ArticleCategories
          categories={this.props.categories}
          filterByCategory={this.props.getByCategory}
          filterByAll={this.props.resetFilters}
          filterByFavourites={this.props.filterByFavourites}
        />
      </Stack>
    );
  }
}

export default ArticlesFilter;
