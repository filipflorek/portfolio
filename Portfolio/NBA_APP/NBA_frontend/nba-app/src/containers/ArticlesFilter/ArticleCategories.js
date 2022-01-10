import React from "react";
import { Stack, Divider, Radio, Text, RadioGroup } from "@chakra-ui/react";
import { UserContext } from "../../App";

const ArticleCategories = (props) => {
  const { user } = React.useContext(UserContext);
  const [value, setValue] = React.useState();
  const allCategories =
    user === null
      ? [...props.categories, { id: "all", category: "Wszystkie" }]
      : [
          ...props.categories,
          { id: "favourite", category: "Ulubione" },
          { id: "all", category: "Wszystkie" },
        ];
  return (
    <Stack p={5} shadow="lg" borderWidth="1px">
      <Text>Kategorie artykułów</Text>
      <Divider />
      <RadioGroup
        onChange={(event) => {
          if (event === "all") {
            props.filterByAll();
          } else {
            if (event === "favourite") {
              props.filterByFavourites();
            } else {
              props.filterByCategory(event, 0);
            }
          }
          setValue(event);
        }}
        value={value}
      >
        <Stack>
          {allCategories.map((category) => {
            return (
              <Radio key={category.id} value={category.id.toString()}>
                {category.category}
              </Radio>
            );
          })}
        </Stack>
      </RadioGroup>
    </Stack>
  );
};

export default ArticleCategories;
