import React from "react";
import { comparePlayerCareers } from "../../Api";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  Flex,
  Text,
  Select,
  StatArrow,
  StatNumber,
  Tooltip,
} from "@chakra-ui/react";

const SeasonAveragesTable = (props) => {
  const [selectedPlayer, setSelectedPlayer] = React.useState([]);
  const [playersData, setPlayersData] = React.useState([]);

  React.useEffect(() => {
    if (props.selectedPlayers.length !== 0) {
      comparePlayerCareers(
        props.selectedPlayers.map((player) => player.id),
        20
      ).then((response) => {
        setPlayersData(response.data);
        setSelectedPlayer(response.data[0]);
      });
    }
  }, [props.selectedPlayers]);

  return (
    <>
      <Flex>
        <Tooltip
          hasArrow
          label="Średnie uzyskiwane na mecz w sezonie"
          bg="gray.300"
          color="black"
        >
          <Text flex="2" fontWeight="semibold" fontSize="3xl">
            Średnie kariery
          </Text>
        </Tooltip>

        <Select
          flex="1"
          onChange={(event) => {
            setSelectedPlayer(
              playersData.find((career) => {
                return career[0]?.player_id.id === Number(event.target.value);
              }) ?? []
            );
          }}
        >
          {props.selectedPlayers.map((player) => {
            return (
              <option
                value={player.id}
                key={player.id}
              >{`${player.first_name} ${player.last_name}`}</option>
            );
          })}
        </Select>
      </Flex>

      <Table variant="simple">
        <Thead>
          <Tr>
            <Th>Sezon</Th>
            <Th>Czas na boisku</Th>
            <Th>Punkty</Th>
            <Th>Asysty</Th>
            <Th>Zbiórki</Th>
            <Th>Przechwyty</Th>
            <Th>Bloki</Th>
            <Th>Skuteczność</Th>
          </Tr>
        </Thead>
        <Tbody>
          {selectedPlayer.map((year, index) => {
            return (
              <Tr
                key={year.season}
                backgroundColor={
                  index % 2 === 0 ? "" : "rgba(199, 199, 199, 0.2)"
                }
              >
                <Td>{year.season}</Td>
                <Td>{year.min}</Td>
                <Td>{Math.round(year.pts * 100) / 100}</Td>
                <Td>{Math.round(year.ast * 100) / 100}</Td>
                <Td>{Math.round(year.reb * 100) / 100}</Td>
                <Td>{Math.round(year.stl * 100) / 100}</Td>
                <Td>{Math.round(year.blk * 100) / 100}</Td>
                <Td>
                  <StatNumber>
                    {Math.round(year.fg_pct * 100) / 100}%{" "}
                    <StatArrow
                      type={
                        Number(year.fg_pct) >= 0.42 ? "increase" : "decrease"
                      }
                    />
                  </StatNumber>
                </Td>
              </Tr>
            );
          })}
        </Tbody>
      </Table>
    </>
  );
};

export default SeasonAveragesTable;
