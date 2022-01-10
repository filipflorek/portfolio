import {
  FormControl,
  FormLabel,
  Input,
  Textarea,
  Select,
  Box,
  Button,
  Flex,
  Spacer,
  useToast,
  AlertDialogOverlay,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogBody,
  AlertDialogFooter,
  AlertDialog,
} from "@chakra-ui/react";
import React from "react";
import { getAllCategories, editArticle } from "../../Api";
import { UserContext } from "../../../App";

const publicistID = 1;

const ArticleToEditView = (props) => {
  const [selectedCategory, setSelectedCategory] = React.useState();
  const [title, setTitle] = React.useState();
  const [tags, setTags] = React.useState();
  const [articleContent, setArticleContent] = React.useState();
  const [categories, setCategories] = React.useState([]);
  const [image, setImage] = React.useState();
  const [isOpen, setIsOpen] = React.useState(false);
  const { user } = React.useContext(UserContext);
  const cancelRef = React.useRef();
  const toast = useToast();

  React.useEffect(() => {
    getAllCategories().then((response) => {
      setCategories(response.data);
    });
  }, []);

  React.useEffect(() => {
    if (props.article !== undefined) {
      setTitle(props.article.title);
      setArticleContent(props.article.content);
      setTags(props.article.tags);
      setSelectedCategory(props.article.category.id);
    }
  }, [props.article]);

  const clearState = () => {
    setTitle("");
    setTags("");
    setArticleContent("");
    setCategories([]);
  };

  return (
    <>
      <Box margin={10}>
        <Box width="50%">
          <FormControl id="category" isRequired>
            <FormLabel>Kategoria</FormLabel>
            <Select
              placeholder="Wybierz kategorię"
              onChange={(event) => setSelectedCategory(event.target.value)}
              value={selectedCategory}
            >
              {categories.map((category) => {
                return (
                  <option key={category.id} value={category.id}>
                    {category.category}
                  </option>
                );
              })}
            </Select>
          </FormControl>
          <FormControl id="title" isRequired>
            <FormLabel>Tytuł</FormLabel>
            <Input
              placeholder="Tytuł artykułu"
              onChange={(event) => {
                setTitle(event.target.value);
              }}
              value={title}
            />
          </FormControl>
          <FormControl id="tags" isRequired>
            <FormLabel>Tagi</FormLabel>
            <Input
              placeholder="Wpisz tagi oddzielone średnikiem"
              onChange={(event) => {
                setTags(event.target.value);
              }}
              value={tags}
            />
          </FormControl>
        </Box>
        <FormControl id="content" isRequired>
          <FormLabel>Treść artykułu</FormLabel>
          <Textarea
            minHeight="300px"
            placeholder="Treść artykułu"
            onChange={(event) => {
              setArticleContent(event.target.value);
            }}
            value={articleContent}
          />
        </FormControl>
        <Flex>
          <Button
            colorScheme="red"
            onClick={() => setIsOpen(true)}
            disabled={props.article === undefined}
          >
            Usuń artykuł
          </Button>
          <Spacer />
          <Button
            disabled={props.article === undefined}
            onClick={() => {
              editArticle(props.article.id, {
                publicist: user.id,
                title: title,
                category: {
                  id: Number(selectedCategory),
                },
                tags: tags,
                content: articleContent,
                dateOfPublication: new Date().toISOString(),
              })
                .then(() => {
                  props.changeCounter();
                  toast({
                    title: "Edytowano artykuł pomyślnie!",
                    status: "success",
                    duration: 9000,
                    isClosable: true,
                  });
                })
                .catch(() => {
                  toast({
                    title: "Edytowanie artykułu nie powiodło się",
                    description: "Spróbuj ponownie",
                    status: "error",
                    duration: 9000,
                    isClosable: true,
                  });
                });
            }}
          >
            Zapisz zmiany
          </Button>
        </Flex>
      </Box>
      <AlertDialog
        isOpen={isOpen}
        leastDestructiveRef={cancelRef}
        onClose={() => setIsOpen(false)}
      >
        <AlertDialogOverlay>
          <AlertDialogContent>
            <AlertDialogHeader fontSize="lg" fontWeight="bold">
              Usuń artykuł
            </AlertDialogHeader>

            <AlertDialogBody>
              Czy na pewno chcesz usunąć artykuł? Tej akcji nie można odwrócić.
            </AlertDialogBody>

            <AlertDialogFooter>
              <Button
                ref={cancelRef}
                onClick={() => {
                  setIsOpen(false);
                }}
                disabled={props.article === undefined}
              >
                Anuluj
              </Button>
              <Button
                colorScheme="red"
                onClick={() => {
                  props.deleteArticle(props.article.id);
                  clearState();
                  setIsOpen(false);
                }}
                ml={3}
              >
                Usuń
              </Button>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialogOverlay>
      </AlertDialog>
    </>
  );
};

export default ArticleToEditView;
