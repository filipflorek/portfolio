import React from "react";

import {
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  Textarea,
  Button,
} from "@chakra-ui/react";

import { useParams } from "react-router-dom";
import { UserContext } from "../../../App";

const AddComment = (props) => {
  const [commentValue, setCommentValue] = React.useState();
  const { id } = useParams();
  const {
    user: { id: authorID },
  } = React.useContext(UserContext);

  return (
    <Modal blockScrollOnMount={false} isOpen={props.isOpen} size="lg">
      <ModalOverlay />
      <ModalContent>
        <ModalHeader fontWeight="bold">Nowy komentarz</ModalHeader>
        <ModalBody>
          <Textarea
            mb="1rem"
            minHeight="150px"
            placeholder="Skomentuj..."
            onChange={(event) => setCommentValue(event.target.value)}
            value={commentValue}
          ></Textarea>
        </ModalBody>

        <ModalFooter>
          <Button
            marginRight="7px"
            variant="ghost"
            onClick={() => {
              setCommentValue();
              props.cancel();
            }}
          >
            Anuluj
          </Button>
          <Button
            colorScheme="blue"
            mr={3}
            onClick={() => {
              setCommentValue();
              props.add(authorID, {
                content: commentValue,
                article: Number(id),
                author: authorID,
                dateOfPublication: new Date().toISOString(),
              });
            }}
          >
            Dodaj
          </Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};

export default AddComment;
