import React from "react";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  Flex,
  Heading,
  Select,
  StatNumber,
  StatArrow,
  Text,
} from "@chakra-ui/react";

import { comparePlayers } from "../../Api";

const seasons = [2020];

const SingleGamesTable = (props) => {
  const [selectedPlayer, setSelectedPlayer] = React.useState([]);
  const [playersData, setPlayersData] = React.useState([]);
  const [selectedSeason, setSelectedSeason] = React.useState(seasons[0]);

  React.useEffect(() => {
    if (props.selectedPlayers.length !== 0) {
      comparePlayers(
        props.selectedPlayers.map((player) => player.id),
        10,
        selectedSeason
      ).then((response) => {
        setPlayersData(response.data);
        setSelectedPlayer(response.data[0]);
      });
    }
  }, [props.selectedPlayers, selectedSeason]);

  return (
    <>
      <Flex>
        <Text flex="2" fontWeight="semibold" fontSize="3xl">
          Ostatnie 10 spotkań
        </Text>
        <Select
          flex="1"
          onChange={(event) => {
            setSelectedPlayer(
              playersData.find((games) => {
                return games[0]?.player.id === Number(event.target.value);
              }) ?? []
            );
          }}
        >
          {props.selectedPlayers.map((player) => {
            return (
              <option
                // selected={selectedPlayer.id === player.id}
                value={player.id}
                key={player.id}
              >{`${player.first_name} ${player.last_name}`}</option>
            );
          })}
        </Select>
        <Select
          flex="1"
          onChange={(event) => {
            setSelectedSeason(event.target.value);
          }}
        >
          {seasons.map((season) => {
            return (
              <option value={season} key={season}>
                {season}
              </option>
            );
          })}
        </Select>
      </Flex>

      <Table variant="simple">
        <Thead>
          <Tr>
            <Th>Mecz</Th>
            <Th>Czas na boisku</Th>
            <Th>Punkty</Th>
            <Th>Asysty</Th>
            <Th>Zbiórki</Th>
            <Th>Przechwyty</Th>
            <Th>Bloki</Th>
            <Th>Skuteczność</Th>
          </Tr>
        </Thead>
        {selectedPlayer === undefined ? (
          <Text>Brak danych</Text>
        ) : (
          <Tbody>
            {selectedPlayer.map((fixture, index) => {
              return (
                <Tr
                  key={fixture.id}
                  backgroundColor={
                    index % 2 === 0 ? "" : "rgba(199, 199, 199, 0.2)"
                  }
                >
                  <Td>
                    {`${fixture.game.homeTeam.abbreviation} vs ${fixture.game.visitorTeam.abbreviation}`}
                    , {fixture.game.match_date.split("T")[0]}
                  </Td>
                  <Td>{fixture.min === "" ? "00:00" : fixture.min}</Td>
                  <Td>{fixture.pts}</Td>
                  <Td>{fixture.ast}</Td>
                  <Td>{fixture.reb}</Td>
                  <Td>{fixture.stl}</Td>
                  <Td>{fixture.blk}</Td>
                  <Td>
                    <StatNumber>
                      {Math.round(fixture.fg_pct * 100) / 100}%{" "}
                      <StatArrow
                        type={
                          Number(fixture.fg_pct) >= 42 ? "increase" : "decrease"
                        }
                      />
                    </StatNumber>
                  </Td>
                </Tr>
              );
            })}
          </Tbody>
        )}
      </Table>
    </>
  );
};

export default SingleGamesTable;
