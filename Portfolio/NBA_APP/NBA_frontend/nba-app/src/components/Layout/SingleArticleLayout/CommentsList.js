import React from "react";
import {
  List,
  ListItem,
  Text,
  Divider,
  Stack,
  IconButton,
  Flex,
  Spacer,
} from "@chakra-ui/react";
import { AddIcon } from "@chakra-ui/icons";
import Comment from "./Comment";
import AddComment from "./AddComment";
import { UserContext } from "../../../App";

const CommentsList = (props) => {
  const [isAdding, setAdding] = React.useState(false);
  const { user } = React.useContext(UserContext);
  return (
    <>
      <Stack margin={5} padding="2px" p={5} shadow="md" borderWidth="1px">
        <Flex>
          <Text fontWeight="semibold">Komentarze</Text>
          <Spacer />
          {user !== null && (
            <IconButton
              variant="outline"
              colorScheme="blue"
              icon={<AddIcon />}
              onClick={() => {
                setAdding(true);
              }}
            />
          )}
        </Flex>

        <Divider />
        <List spacing={3}>
          {props.comments.map((comment) => {
            return (
              <ListItem key={comment.id}>
                <Comment comment={comment} reload={props.shouldReload} />
              </ListItem>
            );
          })}
        </List>
      </Stack>
      {user !== null && (
        <AddComment
          isOpen={isAdding}
          add={(...args) => {
            props.addComment(...args);
            setAdding(false);
          }}
          cancel={() => {
            console.log("anulowano dodawanie komentarza");
            setAdding(false);
          }}
        />
      )}
    </>
  );
};

export default CommentsList;
