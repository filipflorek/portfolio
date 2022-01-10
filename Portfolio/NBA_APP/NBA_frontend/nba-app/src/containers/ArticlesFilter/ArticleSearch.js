import React from "react";
import {
  Stack,
  Divider,
  Input,
  InputGroup,
  InputLeftElement,
  Text,
  Button,
} from "@chakra-ui/react";
import { SearchIcon } from "@chakra-ui/icons";

const ArticleSearch = (props) => {
  const [searchValue, setSearchValue] = React.useState("");
  return (
    <Stack p={5} shadow="lg" borderWidth="1px">
      <Text>Wyszukaj artykuły</Text>
      <Divider />
      <InputGroup>
        <InputLeftElement
          pointerEvents="none"
          children={<SearchIcon color="gray.300" />}
        />
        <Input
          placeholder="Szukaj artykułów"
          onChange={(event) => setSearchValue(event.target.value)}
          value={searchValue}
        />
      </InputGroup>
      <Button
        onClick={() => {
          props.search(searchValue, 0);
          setSearchValue("");
        }}
      >
        Wyszukaj
      </Button>
    </Stack>
  );
};

export default ArticleSearch;
