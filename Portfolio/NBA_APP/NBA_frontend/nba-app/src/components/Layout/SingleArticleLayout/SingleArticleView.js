import React from "react";
import {
  Divider,
  Heading,
  Stack,
  Box,
  Image,
  Text,
  Flex,
  Spacer,
} from "@chakra-ui/react";
import { useParams } from "react-router-dom";
import CommentsList from "./CommentsList";
import { getArticleByID, getArticleImage, addComment } from "../../Api";

const SingleArticleView = () => {
  const [article, setArticle] = React.useState();
  const [articleImage, setArticleImage] = React.useState([]);
  const [shouldReload, setShouldReload] = React.useState(false);
  const { id } = useParams();
  React.useEffect(() => {
    getArticleByID(id).then((response) => {
      setArticle(response.data);
    });
    getArticleImage(id).then((response) => {
      setArticleImage(response.data);
    });
  }, [id, shouldReload]);
  return (
    <Stack margin={5} padding="2px" p={5} shadow="md" borderWidth="1px">
      <Flex textAlign="left" padding="2px">
        <Text>Opublikowane przez: {article?.publicist.login}</Text>
        <Spacer />
        <Text fontWeight="semibold" fontSize="lg">
          {article?.dateOfPublication.split("T")[0]}
        </Text>
      </Flex>
      <Heading>{article?.title}</Heading>
      <Divider />
      <Flex boxSize="m" margin={5} width="100%" bg="black" alignItems="center">
        <Spacer />
        <Image src={articleImage[0]?.url} alt={articleImage[0]?.name} />
        <Spacer />
      </Flex>
      <Text textAlign="justify" padding={5}>
        {article?.content}
      </Text>
      <CommentsList
        comments={article?.comments ?? []}
        shouldReload={() => {
          setShouldReload(!shouldReload);
        }}
        addComment={(...args) => {
          addComment(...args).then(() => setShouldReload(!shouldReload));
        }}
      />
    </Stack>
  );
};

export default SingleArticleView;
