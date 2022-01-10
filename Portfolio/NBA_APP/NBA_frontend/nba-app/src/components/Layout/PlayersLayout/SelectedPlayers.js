import React from "react";
import { Button, Box } from "@chakra-ui/react";
import { CloseIcon } from "@chakra-ui/icons";

const SelectedPlayers = (props) => {
  return (
    <Box>
      {props.selectedPlayers.map((player) => {
        return (
          <Button
            marginLeft={5}
            key={player.id}
            rightIcon={<CloseIcon boxSize={3} />}
            onClick={() => props.deselectPlayer(player)}
          >
            {player.first_name} {player.last_name}
          </Button>
        );
      })}
    </Box>
  );
};

export default SelectedPlayers;
