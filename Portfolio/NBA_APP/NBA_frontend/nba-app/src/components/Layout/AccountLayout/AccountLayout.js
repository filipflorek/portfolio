import { Box, Button, Grid, Heading, Select, Stack } from "@chakra-ui/react";
import React from "react";
import {
  getFavouriteTeams,
  getAllTeams,
  addFavouritePlayer,
  addFavouriteTeam,
  deleteFavouritePlayer,
  deleteFavouriteTeam,
  getFavouritePlayers,
} from "../../Api";
import { CloseIcon } from "@chakra-ui/icons";
import FavouriteTeamsBar from "./FavouriteTeamsBar";
import FavouritePlayersBar from "./FavouritePlayersBar";
import { UserContext } from "../../../App";

const AccountLayout = () => {
  const [allTeams, setAllTeams] = React.useState([]);
  const [favouriteTeams, setFavouriteTeams] = React.useState([]);
  const [favouritePlayers, setFavouritePlayers] = React.useState([]);
  const { user } = React.useContext(UserContext);

  React.useEffect(() => {
    getAllTeams().then((response) => {
      setAllTeams(response.data);
    });
  }, []);

  React.useEffect(() => {
    getFavouriteTeams(user.id).then((response) => {
      setFavouriteTeams(response.data);
    });
  }, []);

  React.useEffect(() => {
    getFavouritePlayers(user.id).then((response) => {
      setFavouritePlayers(response.data);
    });
  }, []);

  return (
    <Grid templateColumns="repeat(2, 1fr)" gap={6}>
      <Grid
        templateColumns="repeat(2, 1fr)"
        gap={6}
        borderWidth="5px"
        margin={5}
        padding={5}
        borderRadius="lg"
        borderColor="rgba(57, 118, 223, 0.3)"
      >
        <Heading size="lg">Ulubione drużyny</Heading>
        <FavouriteTeamsBar
          teams={allTeams}
          addTeamToFavourites={(teamID) => {
            if (!favouriteTeams.find((fav) => fav.id === Number(teamID))) {
              addFavouriteTeam(user.id, teamID).then(() => {
                setFavouriteTeams([
                  ...favouriteTeams,
                  allTeams.find((t) => t.id === Number(teamID)),
                ]);
                console.log(favouriteTeams);
              });
            }
          }}
        />
        <Box>
          {favouriteTeams.map((favouriteTeam) => {
            return (
              <Button
                key={favouriteTeam.id}
                margin={2}
                rightIcon={<CloseIcon boxSize={3} />}
                minWidth="250px"
                colorScheme="blue"
                variant="outline"
                onClick={() =>
                  deleteFavouriteTeam(user.id, favouriteTeam.id).then(() => {
                    setFavouriteTeams(
                      favouriteTeams.filter(
                        (team) => team.id !== favouriteTeam.id
                      )
                    );
                  })
                }
              >
                {favouriteTeam.full_name}
              </Button>
            );
          })}
        </Box>
      </Grid>
      <Grid
        templateColumns="repeat(2, 1fr)"
        gap={6}
        borderWidth="5px"
        margin={5}
        padding={5}
        borderRadius="lg"
        borderColor="rgba(255, 0, 17, 0.3)"
      >
        <Heading size="lg">Ulubieni zawodnicy</Heading>
        <FavouritePlayersBar
          addFavouritePlayer={(player) => {
            if (!favouritePlayers.find((fav) => fav.id === player.id)) {
              addFavouritePlayer(user.id, player.id).then(() => {
                setFavouritePlayers([...favouritePlayers, player]);
              });
            }
          }}
        />
        <Box>
          {favouritePlayers.map((favouritePlayer) => {
            return (
              <Button
                key={favouritePlayer.id}
                margin={2}
                rightIcon={<CloseIcon boxSize={3} />}
                minWidth="250px"
                colorScheme="pink"
                variant="outline"
                onClick={() =>
                  deleteFavouritePlayer(user.id, favouritePlayer.id).then(
                    () => {
                      setFavouritePlayers(
                        favouritePlayers.filter(
                          (player) => player.id !== favouritePlayer.id
                        )
                      );
                    }
                  )
                }
              >
                {`${favouritePlayer.first_name} ${favouritePlayer.last_name}`}
              </Button>
            );
          })}
        </Box>
      </Grid>
    </Grid>
  );
};

export default AccountLayout;
