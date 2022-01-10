import React from "react";
import SearchBar from "./SearchBar";
import SingleGamesTable from "./SingleGamesTable";
import SeasonAveragesTable from "./SeasonAveragesTable";
import {
  Box,
  Tabs,
  TabList,
  TabPanels,
  Tab,
  TabPanel,
  HStack,
  Spacer,
} from "@chakra-ui/react";
import SelectedPlayers from "./SelectedPlayers";
import SingleGamesChart from "./SingleGamesChart";
import { getSinglePlayerStats } from "../../Api";

const PlayersLayout = () => {
  const [selectedPlayers, setSelectedPlayers] = React.useState([]);
  return (
    <Box>
      <HStack margin={5}>
        <SearchBar
          selectPlayer={(player) => {
            if (!selectedPlayers.find((p) => p.id === player.id)) {
              setSelectedPlayers([...selectedPlayers, player]);
            }
          }}
          selectPlayers={(players) => {
            setSelectedPlayers([
              ...selectedPlayers,
              ...players.filter(
                (player) =>
                  !selectedPlayers.some(
                    (selectedPlayer) => selectedPlayer.id === player.id
                  )
              ),
            ]);
          }}
        />
        <Spacer />
        <SelectedPlayers
          selectedPlayers={selectedPlayers}
          deselectPlayer={(player) => {
            setSelectedPlayers(
              selectedPlayers.filter((p) => p.id !== player.id)
            );
          }}
        />
      </HStack>
      {!!selectedPlayers.length && (
        <Tabs isFitted variant="enclosed">
          <TabList mb="1em">
            <Tab>Statystyki z ostatnich meczy</Tab>
            <Tab>Statystyki kariery</Tab>
            <Tab>Wykres z ostatnich meczy</Tab>
          </TabList>
          <TabPanels>
            <TabPanel>
              <SingleGamesTable selectedPlayers={selectedPlayers} />
            </TabPanel>
            <TabPanel>
              <SeasonAveragesTable selectedPlayers={selectedPlayers} />
            </TabPanel>
            <TabPanel height="600px">
              <SingleGamesChart selectedPlayers={selectedPlayers} />
            </TabPanel>
          </TabPanels>
        </Tabs>
      )}
    </Box>
  );
};

export default PlayersLayout;
