import React from "react";
import { Select, Button } from "@chakra-ui/react";
import { getFavouriteTeams } from "../../Api";

import { UserContext } from "../../../App";

const TeamFilterBar = (props) => {
  const { user } = React.useContext(UserContext);

  return (
    <>
      <Select
        placeholder="Wybierz drużyny"
        width="30%"
        onChange={(event) => {
          if (event.target.value !== "") {
            props.selectTeam(event.target.value);
          }
        }}
        value={"abcd"}
      >
        {props.teams.map((team) => {
          return (
            <option key={team.id} value={team.id}>
              {team.full_name}
            </option>
          );
        })}
      </Select>
      {user !== null && (
        <Button
          onClick={() => {
            getFavouriteTeams(user.id).then((response) => {
              props.selectTeams(response.data);
            });
          }}
        >
          Pokaż mecze ulubionych drużyn
        </Button>
      )}
    </>
  );
};

export default TeamFilterBar;
