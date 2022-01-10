import {
  Alert,
  AlertDescription,
  AlertIcon,
  AlertTitle,
} from "@chakra-ui/react";
import React from "react";

const NotAuthorized = () => {
  return (
    <Alert status="error">
      <AlertIcon />
      <AlertTitle mr={2}>Teren prywatny!</AlertTitle>
      <AlertDescription>
        Nie posiadasz odpowienich urpawnień, aby odwiedzić tę stronę
      </AlertDescription>
    </Alert>
  );
};

export default NotAuthorized;
