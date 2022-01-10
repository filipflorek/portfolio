import React from "react";
import {
  Stack,
  Box,
  Heading,
  Text,
  Grid,
  GridItem,
  IconButton,
  HStack,
  Drawer,
  DrawerBody,
  DrawerFooter,
  DrawerHeader,
  DrawerOverlay,
  DrawerContent,
  DrawerCloseButton,
  useDisclosure,
  Button,
  FormLabel,
  Input,
  Textarea,
} from "@chakra-ui/react";
import { DeleteIcon, EditIcon } from "@chakra-ui/icons";
import DeleteCommentAlert from "./DeleteCommentAlert";
import { deleteComment, editComment } from "../../Api";
import { UserContext } from "../../../App";
import { useParams } from "react-router-dom";

const Comment = (props) => {
  const { author, content, id: commmentID } = props.comment;
  const [isEditing, setEditing] = React.useState(false);
  const [isDeleting, setDeleting] = React.useState(false);
  const [commentValue, setCommentValue] = React.useState(content);
  const { user } = React.useContext(UserContext);
  const { id: articleID } = useParams();

  return (
    <>
      <Grid
        templateColumns="repeat(6, 1fr)"
        gap={4}
        margin="5px"
        p={5}
        shadow="md"
        borderWidth="1px"
      >
        <GridItem colSpan={5}>
          <Stack>
            <Box textAlign="left">
              <Heading fontSize="l">{author.login}</Heading>
              {isEditing ? (
                <>
                  <Textarea
                    value={commentValue}
                    minHeight="100px"
                    onChange={(event) => setCommentValue(event.target.value)}
                  />
                  <Button
                    colorScheme="blue"
                    marginRight="5px"
                    onClick={() => {
                      editComment(user?.id, commmentID, {
                        content: commentValue,
                        article: Number(articleID),
                        author: author.id,
                        dateOfPublication: new Date().toISOString(),
                      }).then(() => props.reload());
                      setEditing(false);
                    }}
                  >
                    Zapisz
                  </Button>
                  <Button
                    onClick={() => {
                      setEditing(false);
                    }}
                  >
                    Anuluj
                  </Button>
                </>
              ) : (
                <Text textAlign="justify">{commentValue}</Text>
              )}
            </Box>
          </Stack>
        </GridItem>
        <GridItem colSpan={1}>
          {user !== null && user.id === author.id && (
            <HStack margin="15px">
              <IconButton
                variant="outline"
                colorScheme="blue"
                disabled={isEditing}
                icon={<EditIcon />}
                onClick={() => setEditing(true)}
              />

              <IconButton
                variant="outline"
                colorScheme="red"
                disabled={isEditing}
                onClick={() => setDeleting(true)}
                icon={<DeleteIcon />}
              />
            </HStack>
          )}
        </GridItem>
      </Grid>
      <DeleteCommentAlert
        isOpen={isDeleting}
        cancel={() => {
          console.log("canceled!");
          setDeleting(false);
        }}
        delete={() => {
          deleteComment(user?.id, commmentID).then(() => props.reload());
          // window.location.reload();
          setDeleting(false);
        }}
      />
    </>
  );
};

export default Comment;
