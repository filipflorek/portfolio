import React from "react";
import { Button, Box } from "@chakra-ui/react";
import { CloseIcon } from "@chakra-ui/icons";

const SelectedTeams = (props) => {
  return (
    <Box>
      {props.selectedTeams.map((team) => {
        return (
          <Button
            marginLeft={5}
            key={team.id}
            rightIcon={<CloseIcon boxSize={3} />}
            onClick={() => props.deselectTeam(team)}
          >
            {team.abbreviation}
          </Button>
        );
      })}
    </Box>
  );
};

export default SelectedTeams;
