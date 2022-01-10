import React from "react";
import {
  Avatar,
  Flex,
  WrapItem,
  Box,
  Heading,
  Text,
  Spacer,
} from "@chakra-ui/react";
import { Link } from "react-router-dom";

const SinglePopularArticle = (props) => {
  const { publicist, id, title, dateOfPublication } = props.article;

  return (
    <Link to={`/artykul/${id}`}>
      <Flex
        margin="5px"
        p={5}
        shadow="lg"
        borderWidth="1px"
        backgroundColor="rgba(20, 89, 245, 0.08)"
      >
        <Avatar name="Dan Abrahmov" src="https://bit.ly/dan-abramov" />

        <Box textAlign="left" marginLeft={2}>
          <Heading fontSize="l">{title}</Heading>
          <Flex>
            <Text>{publicist?.login}</Text>
            <Spacer />
            <Text>{dateOfPublication.split("T")[0]}</Text>
          </Flex>
        </Box>
      </Flex>
    </Link>
  );
};

export default SinglePopularArticle;
