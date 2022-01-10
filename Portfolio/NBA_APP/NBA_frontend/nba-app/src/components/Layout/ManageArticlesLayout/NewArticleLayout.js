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
} from "@chakra-ui/react";
import React from "react";
import { getAllCategories, addArticle, uploadImage } from "../../Api";
import { useDropzone } from "react-dropzone";
import { UserContext } from "../../../App";

const NewArticleLayout = (props) => {
  const [selectedCategory, setSelectedCategory] = React.useState();
  const [title, setTitle] = React.useState();
  const [tags, setTags] = React.useState();
  const [articleContent, setArticleContent] = React.useState();
  const [categories, setCategories] = React.useState([]);
  const [image, setImage] = React.useState();
  const [submitting, setSubmitting] = React.useState(false);
  const toast = useToast();
  const onDrop = React.useCallback((acceptedFiles) => {
    setImage(acceptedFiles);
  }, []);
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });
  const { user } = React.useContext(UserContext);

  React.useEffect(() => {
    getAllCategories().then((response) => {
      setCategories(response.data);
    });
  }, []);

  const clearState = () => {
    setTitle("");
    setTags("");
    setArticleContent("");
    setImage("");
  };

  return (
    <Box margin={10}>
      <Box width="50%">
        <FormControl id="category" isRequired>
          <FormLabel>Kategoria</FormLabel>
          <Select
            placeholder="Wybierz kategorię"
            onChange={(event) => setSelectedCategory(event.target.value)}
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
        </FormControl>

        <Input
          placeholder="Wpisz tagi oddzielone średnikiem"
          onChange={(event) => {
            setTags(event.target.value);
          }}
          value={tags}
        />
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

      <Box
        {...getRootProps()}
        border="1px"
        borderColor="rgba(209, 206, 245, 0.9)"
        minHeight="100px"
        borderRadius="lg"
      >
        <input {...getInputProps()} />
        {isDragActive ? (
          <p>Upuść zdjecie tutaj ...</p>
        ) : (
          <p>Przeciągnij i upuść zdjęcie, aby dodac je do artykułu</p>
        )}
      </Box>

      <Flex>
        <Spacer />
        <Button
          onClick={() => {
            setSubmitting(true);
            addArticle({
              publicist: user.id,
              title: title,
              category: {
                id: Number(selectedCategory),
              },
              tags: tags,
              content: articleContent,
              dateOfPublication: new Date().toISOString(),
            })
              .then((response) => {
                props.changeCounter();
                debugger;
                uploadImage(image, response.data)
                  .then(() => {
                    clearState();
                    toast({
                      title: "Dodano nowy artykuł!",
                      status: "success",
                      duration: 9000,
                      isClosable: true,
                    });
                  })
                  .catch(() => {
                    clearState();
                    toast({
                      title: "Dodano artykuł bez zdjęcia!",
                      status: "error",
                      duration: 9000,
                      isClosable: true,
                    });
                  });
                setSubmitting(false);
              })
              .catch((error) => {
                debugger;
                toast({
                  title: "Artykuł nie został dodany",
                  description: "Spróbuj ponownie",
                  status: "error",
                  duration: 9000,
                  isClosable: true,
                });
                setSubmitting(false);
              });
          }}
          disabled={
            title === "" ||
            articleContent === "" ||
            tags === "" ||
            title === undefined ||
            articleContent === undefined ||
            tags === undefined
          }
          isLoading={submitting}
          loadingText="Dodawanie artykułu"
        >
          Dodaj artykuł
        </Button>
      </Flex>
    </Box>
  );
};

export default NewArticleLayout;
