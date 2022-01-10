import { SearchIcon } from "@chakra-ui/icons";
import {
  Box,
  Input,
  InputGroup,
  InputLeftElement,
  Stack,
  Button,
} from "@chakra-ui/react";
import React from "react";
import { getPlayersByName, getFavouritePlayers } from "../../Api";
import { useOutsideClick } from "../../hooks/useOutsideClick";
import { UserContext } from "../../../App";

const SearchBar = (props) => {
  const [searchValue, setSearchValue] = React.useState("");
  const [players, setPlayers] = React.useState([]);
  const [stackVisible, setStackVisible] = React.useState(false);
  const wrapperRef = React.useRef(null);
  const { user } = React.useContext(UserContext);
  useOutsideClick(wrapperRef, () => {
    setStackVisible(false);
  });
  return (
    <>
      <Box padding={0} ref={wrapperRef} position="relative">
        <InputGroup>
          <InputLeftElement
            pointerEvents="none"
            children={<SearchIcon color="gray.300" />}
          />
          <Input
            placeholder="Wyszukaj zawodnika"
            type="text"
            value={searchValue}
            onChange={(event) => {
              setSearchValue(event.target.value);
              if (event.target.value.length >= 3) {
                getPlayersByName(event.target.value).then((response) => {
                  setPlayers(response.data.result);
                  setStackVisible(true);
                });
              } else {
                setStackVisible(false);
              }
            }}
            onClick={() =>
              searchValue.length >= 3
                ? setStackVisible(true)
                : setStackVisible(false)
            }
          />
        </InputGroup>
        <Stack
          spacing={0}
          align="stretch"
          width="100%"
          borderWidth="1px"
          borderColor="gray.200"
          maxHeight="300px"
          overflow="auto"
          display={stackVisible ? "block" : "none"}
          position="absolute"
          zIndex={1}
        >
          {players.map((player) => {
            return (
              <Box
                key={player.id}
                cursor="pointer"
                padding="5px"
                background="white"
                _hover={{
                  background: "gray.50",
                }}
                onClick={() => {
                  props.selectPlayer(player);
                  setStackVisible(false);
                  setSearchValue("");
                }}
              >
                {`${player.first_name} ${player.last_name}`}
              </Box>
            );
          })}
        </Stack>
      </Box>
      {user !== null && (
        <Button
          onClick={() => {
            getFavouritePlayers(user.id).then((response) => {
              props.selectPlayers(response.data);
            });
          }}
        >
          Porównaj ulubionych zawodników
        </Button>
      )}
    </>
  );
};

export default SearchBar;
