import React from "react";
import { Select } from "@chakra-ui/react";

const FavouriteTeamsBar = (props) => {
  return (
    <>
      <Select
        placeholder="Dodaj drużyny do ulubionych"
        onChange={(event) => {
          props.addTeamToFavourites(event.target.value);
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
    </>
  );
};

export default FavouriteTeamsBar;
