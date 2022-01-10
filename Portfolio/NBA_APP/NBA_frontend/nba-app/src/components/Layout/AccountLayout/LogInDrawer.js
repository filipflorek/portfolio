import React from "react";
import {
  Drawer,
  DrawerBody,
  DrawerFooter,
  DrawerHeader,
  DrawerOverlay,
  DrawerContent,
  DrawerCloseButton,
  Button,
  Input,
  Stack,
  Box,
  FormLabel,
  InputRightElement,
  InputGroup,
  useToast,
} from "@chakra-ui/react";
import { logIn } from "../../Api";
import { UserContext } from "../../../App";

const LogInDrawer = (props) => {
  const { setUser } = React.useContext(UserContext);
  const firstField = React.useRef();
  const [showPassword, setShowPassword] = React.useState(false);
  const [login, setLogin] = React.useState("");
  const [password, setPassword] = React.useState("");
  const handleClickP = () => setShowPassword(!showPassword);
  const toast = useToast();

  const logIntoAccount = (login, password) => {
    console.log(login === {});
    logIn(login, password)
      .then((response) => {
        setUser(response.data.result ?? null);
        if (response.data.result !== null) {
          localStorage.setItem(
            "nbaAppUser",
            JSON.stringify(response.data.result)
          );
        }
        response.data.status === "OK"
          ? toast({
              title: "Zostałeś zalogowany!",
              status: "success",
              duration: 9000,
              isClosable: true,
            })
          : toast({
              title: "Błędny login lub hasło!",
              description: "Spróbuj ponownie",
              status: "error",
              duration: 9000,
              isClosable: true,
            });

        props.closeDrawer();
      })
      .catch(() => {
        toast({
          title: "Błędny login lub hasło!",
          description: "Spróbuj ponownie",
          status: "error",
          duration: 9000,
          isClosable: true,
        });
      });
  };

  const clearState = () => {
    setLogin("");
    setPassword("");
  };
  return (
    <Drawer
      isOpen={props.isOpen}
      placement="right"
      onClose={() => props.closeDrawer()}
      initialFocusRef={firstField}
    >
      <DrawerOverlay>
        <DrawerContent>
          <DrawerCloseButton
            onClick={() => {
              clearState();
              props.closeDrawer();
            }}
          />
          <DrawerHeader borderBottomWidth="1px">Zaloguj się</DrawerHeader>

          <DrawerBody>
            <Stack spacing="24px">
              <Box>
                <FormLabel htmlFor="login">Login</FormLabel>
                <Input
                  ref={firstField}
                  id="login"
                  placeholder="Podaj login"
                  value={login}
                  onChange={(event) => {
                    setLogin(event.target.value);
                  }}
                />
              </Box>
              <Box>
                <FormLabel>Hasło</FormLabel>
                <InputGroup>
                  <Input
                    pr="4.5rem"
                    type={showPassword ? "text" : "password"}
                    placeholder="Podaj hasło"
                    value={password}
                    onChange={(event) => {
                      setPassword(event.target.value);
                    }}
                  />
                  <InputRightElement width="4.5rem">
                    <Button h="1.75rem" size="sm" onClick={handleClickP}>
                      {showPassword ? "Hide" : "Show"}
                    </Button>
                  </InputRightElement>
                </InputGroup>
              </Box>
            </Stack>
          </DrawerBody>

          <DrawerFooter borderTopWidth="1px">
            <Button
              variant="outline"
              mr={3}
              onClick={() => {
                clearState();
                props.closeDrawer();
              }}
            >
              Anuluj
            </Button>
            <Button
              colorScheme="blue"
              onClick={() => {
                logIntoAccount(login, password);
                clearState();
              }}
            >
              Submit
            </Button>
          </DrawerFooter>
        </DrawerContent>
      </DrawerOverlay>
    </Drawer>
  );
};

export default LogInDrawer;
