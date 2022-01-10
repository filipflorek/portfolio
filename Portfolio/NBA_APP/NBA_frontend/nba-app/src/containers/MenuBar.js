import React from "react";
import {
  Box,
  Flex,
  Spacer,
  Button,
  ButtonGroup,
  Avatar,
} from "@chakra-ui/react";
import { EditIcon, AddIcon, UnlockIcon } from "@chakra-ui/icons";
import { Link } from "react-router-dom";

import SignUpDrawer from "../components/Layout/AccountLayout/SignUpDrawer";
import LogInDrawer from "../components/Layout/AccountLayout/LogInDrawer";
import { UserContext } from "../App";
const MenuBar = () => {
  const [loginDrawer, setLoginDrawer] = React.useState(false);
  const [signUpDrawer, setSignUpDrawer] = React.useState(false);
  const { user, setUser } = React.useContext(UserContext);
  return (
    <>
      <Flex
        paddingTop={1}
        borderBottomWidth="1px"
        borderColor="rgba(20, 89, 245, 0.2)"
        backgroundColor="rgba(20, 89, 245, 0.1)"
      >
        <Box p="2" marginLeft={20}>
          <Link to="/">
            <Avatar
              size="lg"
              name="NBA"
              src="https://www.clipartmax.com/png/middle/249-2493894_computer-wallpaper-logo-circle-nba-logo-transparent.png"
            />
          </Link>
        </Box>
        <Spacer />
        <ButtonGroup
          spacing={5}
          marginRight={20}
          variant="outline"
          paddingTop={4}
        >
          <Button variant="ghost" colorScheme="blue">
            <Link to="/">Artykuły</Link>
          </Button>
          <Button variant="ghost" colorScheme="blue">
            <Link to="/kalendarz">Kalendarz</Link>
          </Button>
          {user !== null && user.role === "Publicist" && (
            <Button variant="ghost" colorScheme="blue">
              <Link to="/nowyArtykul">Zarządzaj artykułami</Link>
            </Button>
          )}
          <Button variant="ghost" colorScheme="blue">
            <Link to="/zawodnicy">Zawodnicy</Link>
          </Button>
          {user !== null && (
            <Button rightIcon={<EditIcon />} variant="ghost" colorScheme="blue">
              <Link to="/konto">Moje konto</Link>
            </Button>
          )}
          {user !== null ? (
            <Button
              variant="ghost"
              colorScheme="blue"
              rightIcon={<UnlockIcon />}
              onClick={() => {
                setUser(null);
                localStorage.clear();
              }}
            >
              Wyloguj
            </Button>
          ) : (
            <>
              <Button
                variant="ghost"
                colorScheme="blue"
                rightIcon={<UnlockIcon />}
                onClick={() => {
                  setLoginDrawer(true);
                }}
              >
                Zaloguj się
              </Button>
              <Button
                variant="ghost"
                colorScheme="blue"
                rightIcon={<AddIcon />}
                onClick={() => {
                  setSignUpDrawer(true);
                }}
              >
                Utwórz konto
              </Button>
            </>
          )}
        </ButtonGroup>
      </Flex>
      <SignUpDrawer
        isOpen={signUpDrawer}
        closeDrawer={() => setSignUpDrawer(false)}
      />
      <LogInDrawer
        isOpen={loginDrawer}
        closeDrawer={() => setLoginDrawer(false)}
      />
    </>
  );
};

export default MenuBar;
