import React, { Component } from "react";
import { Grid, Box, GridItem } from "@chakra-ui/react";
import ArticlesFilter from "../../containers/ArticlesFilter/ArticlesFilter";
import PopularArticlesList from "../../containers/PopularArticles/PopularArticlesList";
import FilteredArticles from "../../containers/FilteredArticles/FilteredArticles";
import axios from "axios";
import { ArticleSearchContext } from "./ArticleSearchContext";

class MainArticleLayout extends Component {
  state = {
    categories: [],
    popularArticles: [],
    filteredArticles: [],
    firstSearchFilter: true,
    firstCategoryFilter: true,
    searchContextValue: {
      currentPage: 0,
      isLastPage: false,
      navButtonFunction: (nextPage) => {
        this.getAllArticles(nextPage, true);
      },
    },
  };

  componentDidMount() {
    axios
      .get("http://localhost:8080/nba/articles/categories")
      .then((response) => {
        this.setState({ categories: response.data });
      });
    axios
      .get("http://localhost:8080/nba/articles/popular/5")
      .then((response) => {
        this.setState({ popularArticles: response.data });
      });

    this.getAllArticles(this.state.searchContextValue.currentPage);
  }

  searchArticleByTag = (tag, page, isPaging = false) => {
    axios
      .get(`http://localhost:8080/nba/articles/tags/${tag}/${page}`)
      .then((response) => {
        if (isPaging) {
          this.setState({
            filteredArticles: [
              ...this.state.filteredArticles,
              ...response.data.content,
            ],
            searchContextValue: {
              navButtonFunction: (nextPage) => {
                this.searchArticleByTag(tag, nextPage, true);
              },
              isLastPage: response.data.last,
              currentPage: page,
            },
          });
        } else {
          this.setState({
            filteredArticles: response.data.content,
            searchContextValue: {
              navButtonFunction: (nextPage) => {
                this.searchArticleByTag(tag, nextPage, true);
              },
              isLastPage: response.data.last,
              currentPage: 0,
            },
          });
        }
      });
  };

  searchArticleByCategory = (category, page, isPaging = false) => {
    axios
      .get(`http://localhost:8080/nba/articles/category/${category}/${page}`)
      .then((response) => {
        if (isPaging) {
          this.setState({
            filteredArticles: [
              ...this.state.filteredArticles,
              ...response.data.content,
            ],
            searchContextValue: {
              currentPage: page,
              isLastPage: response.data.last,
              navButtonFunction: (nextPage) => {
                this.searchArticleByCategory(category, nextPage, true);
              },
            },
          });
        } else {
          this.setState({
            filteredArticles: response.data.content,
            searchContextValue: {
              currentPage: 0,
              isLastPage: response.data.last,
              navButtonFunction: (nextPage) => {
                this.searchArticleByCategory(category, nextPage, true);
              },
            },
          });
        }
      });
  };

  getAllArticles = (page = 0, isPaging = false) => {
    axios
      .get(`http://localhost:8080/nba/articles/all/${page}`)
      .then((response) => {
        if (isPaging) {
          this.setState({
            filteredArticles: [
              ...this.state.filteredArticles,
              ...response.data.content,
            ],
            searchContextValue: {
              currentPage: page,
              isLastPage: response.data.last,
              navButtonFunction: (nextPage) => {
                this.getAllArticles(nextPage, true);
              },
            },
          });
        } else {
          this.setState({
            filteredArticles: response.data.content,
            searchContextValue: {
              currentPage: 0,
              isLastPage: response.data.last,
              navButtonFunction: (nextPage) => {
                this.getAllArticles(nextPage, true);
              },
            },
          });
        }
      });
  };

  searchArticleByFavourites = (isPaging = false) => {
    axios
      .get(`http://localhost:8080/nba/articles/favourite/${this.props.user.id}`)
      .then((response) => {
        if (isPaging) {
          this.setState({
            filteredArticles: [
              ...this.state.filteredArticles,
              ...response.data,
            ],
            searchContextValue: {
              currentPage: 0,
              isLastPage: true,
              navButtonFunction: (nextPage) => {
                this.getAllArticles(nextPage, true);
              },
            },
          });
        } else {
          this.setState({
            filteredArticles: response.data,
            searchContextValue: {
              currentPage: 0,
              isLastPage: true,
              navButtonFunction: (nextPage) => {
                this.getAllArticles(nextPage, true);
              },
            },
          });
        }
      });
  };

  render() {
    return (
      <ArticleSearchContext.Provider value={this.state.searchContextValue}>
        <Grid templateColumns="repeat(3, 1fr)" gap={10}>
          <GridItem>
            <ArticlesFilter
              categories={this.state.categories}
              search={this.searchArticleByTag}
              getByCategory={this.searchArticleByCategory}
              resetFilters={this.getAllArticles}
              filterByFavourites={this.searchArticleByFavourites}
            />
          </GridItem>
          <GridItem>
            <FilteredArticles filteredArticles={this.state.filteredArticles} />
          </GridItem>
          <GridItem>
            <PopularArticlesList popularArticles={this.state.popularArticles} />
          </GridItem>
        </Grid>
      </ArticleSearchContext.Provider>
    );
  }
}

export default MainArticleLayout;
