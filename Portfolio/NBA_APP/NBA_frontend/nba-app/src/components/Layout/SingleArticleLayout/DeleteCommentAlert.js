import React from "react";
import {
  AlertDialog,
  AlertDialogBody,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogContent,
  AlertDialogOverlay,
  Button,
} from "@chakra-ui/react";

const DeleteCommentAlert = (props) => {
  return (
    <AlertDialog isOpen={props.isOpen}>
      <AlertDialogOverlay>
        <AlertDialogContent>
          <AlertDialogHeader fontSize="lg" fontWeight="bold">
            Usuń komentarz
          </AlertDialogHeader>

          <AlertDialogBody>
            Czy na pewno chcesz usunąć komentarz? Tej operacji nie można
            przywrócić.
          </AlertDialogBody>

          <AlertDialogFooter>
            <Button onClick={() => props.cancel()}>Anuluj</Button>
            <Button colorScheme="red" onClick={() => props.delete()} ml={3}>
              Usuń
            </Button>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialogOverlay>
    </AlertDialog>
  );
};

export default DeleteCommentAlert;
