import React from "react";
import { ChevronRightIcon, ChevronUpIcon } from "@chakra-ui/icons";
import { Grid, GridItem, Button } from "@chakra-ui/react";
import { ArticleSearchContext } from "../../components/MainArticleLayout/ArticleSearchContext";

const ArticlesNavigation = (props) => {
  const context = React.useContext(ArticleSearchContext);

  return (
    <Grid templateColumns="repeat(6, 1fr)" gap={4}>
      <GridItem>
        <Button
          leftIcon={<ChevronUpIcon />}
          width={200}
          onClick={() => {
            window.scrollTo(0, 0);
          }}
        >
          Powrót na górę
        </Button>
      </GridItem>
      <GridItem colStart={6}>
        <Button
          rightIcon={<ChevronRightIcon />}
          width={200}
          onClick={() => {
            context.navButtonFunction(context.currentPage + 1);
          }}
          disabled={context.isLastPage}
        >
          Załaduj więcej
        </Button>
      </GridItem>
    </Grid>
  );
};

export default ArticlesNavigation;
