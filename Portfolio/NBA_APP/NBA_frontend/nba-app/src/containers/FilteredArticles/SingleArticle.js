import React from "react";
import {
  Box,
  Image,
  Tag,
  Wrap,
  WrapItem,
  Stack,
  Text,
  HStack,
  Spacer,
  Flex,
} from "@chakra-ui/react";
import { ChatIcon } from "@chakra-ui/icons";
import { getArticleImage } from "../../components/Api";
import { Link } from "react-router-dom";

const SingleArticle = (props) => {
  const [image, setImage] = React.useState([]);

  React.useEffect(() => {
    getArticleImage(props.id).then((response) => {
      setImage(response.data);
    });
  }, []);

  const property = {
    imageAlt: "Nie załadowano obrazka",
  };

  return (
    <Box
      maxW="m"
      borderWidth="1px"
      borderRadius="lg"
      overflow="hidden"
      backgroundColor="rgba(20, 89, 245, 0.08)"
    >
      <Link to={`/artykul/${props.id}`}>
        <Image src={image[0]?.url} alt={property.imageAlt} width="100%" />
        <Box p="6">
          <Flex alignItems="baseline" marginBottom={2}>
            <WrapItem>
              {props.tags.split(";").map((tag) => {
                return (
                  <Tag
                    borderRadius="full"
                    px="2"
                    colorScheme="red"
                    key={tag}
                    marginRight="5px"
                  >
                    {tag}
                  </Tag>
                );
              })}
            </WrapItem>
            <Spacer />
            <WrapItem>
              <Text fontWeight="semibold">{props.date}</Text>
            </WrapItem>
          </Flex>
          <Stack>
            <Text textAlign="left" fontWeight="semibold">
              {props.title}
            </Text>
            <Text textAlign="justify" noOfLines={3}>
              {props.content}
            </Text>
            <Box textAlign="right" justifyContent="space-evenly">
              {props.comments} <ChatIcon />
            </Box>
          </Stack>
        </Box>
      </Link>
    </Box>
  );
};

export default SingleArticle;
