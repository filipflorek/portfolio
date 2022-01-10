import React from "react";
import SingleGamesTable from "./SingleGamesTable";
import { ResponsiveLine } from "@nivo/line";
import { comparePlayers } from "../../Api";
import ChartFilters from "./ChartFilters";
import { Select } from "@chakra-ui/react";
import { stats } from "../../../assets/stats";

const normalizeData = (response, stat) => {
  return response.map((playerGames) => {
    const data = playerGames.map((game, index) => {
      return { y: game[stat], x: index + 1 };
    });
    debugger;
    return {
      id: `${playerGames[0].player.firstName} ${playerGames[0].player.lastName}`,
      data,
    };
  });
};

const matchesAmount = 10;
const currentSeason = 2020;

const mapStatFields = (game) => {
  const forDeletion = ["id", "player", "game", "season"];
  return Object.keys(game).filter((field) => !forDeletion.includes(field));
};

const SingleGamesChart = (props) => {
  const [playersData, setPlayersData] = React.useState([]);
  const [chartData, setChartData] = React.useState([]);
  const [selectedStat, setSelectedStat] = React.useState();
  const [statFields, setStatFields] = React.useState([]);

  React.useEffect(() => {
    if (props.selectedPlayers.length !== 0) {
      comparePlayers(
        props.selectedPlayers.map((player) => player.id),
        matchesAmount,
        currentSeason
      ).then((response) => {
        debugger;
        if (!response.data.some((playerGames) => playerGames.length === 0)) {
          setPlayersData(response.data);
          setChartData(normalizeData(response.data, selectedStat));
          setStatFields(mapStatFields(response.data[0][0]));
        }
      });
    }
  }, [props.selectedPlayers]);

  return (
    <>
      <Select
        width="20%"
        onChange={(event) => {
          setSelectedStat(event.target.value);
          setChartData(normalizeData(playersData, event.target.value));
        }}
      >
        {statFields.map((field) =>
          field !== "min" ? (
            <option value={field}>
              {stats.filter((stat) => stat.name === field)[0].value}
            </option>
          ) : null
        )}
      </Select>
      <ResponsiveLine
        data={chartData}
        margin={{ top: 50, right: 150, bottom: 50, left: 60 }}
        xScale={{ type: "point" }}
        yScale={{
          type: "linear",
          min: 0,
          max: "auto",
          stacked: false,
          reverse: false,
        }}
        axisTop={null}
        axisRight={null}
        axisBottom={{
          orient: "bottom",
          tickSize: 10,
          tickPadding: 5,
          tickRotation: 0,
          legend: "Ostatnie mecze",
          legendOffset: 36,
          legendPosition: "middle",
        }}
        axisLeft={{
          orient: "left",
          tickSize: 10,
          tickPadding: 5,
          tickRotation: 0,
        }}
        pointLabelYOffset={-12}
        useMesh={true}
        legends={[
          {
            anchor: "bottom-right",
            direction: "column",
            justify: false,
            translateX: 100,
            translateY: 0,
            itemsSpacing: 0,
            itemDirection: "left-to-right",
            itemWidth: 80,
            itemHeight: 20,
            itemOpacity: 0.75,
            symbolSize: 12,
            symbolShape: "circle",
            symbolBorderColor: "rgba(0, 0, 0, .5)",
          },
        ]}
        curve="monotoneX"
        enableGridX={false}
        animate="true"
        enableSlices="x"
        sliceTooltip={({ slice }) => {
          return (
            <div
              style={{
                background: "white",
                padding: "9px 12px",
                border: "1px solid #ccc",
              }}
            >
              {slice.points.map((point) => (
                <div
                  key={point.id}
                  style={{
                    color: point.serieColor,
                    padding: "3px 0",
                  }}
                >
                  <strong>{point.serieId}</strong>: {point.data.yFormatted}{" "}
                  {selectedStat}
                </div>
              ))}
            </div>
          );
        }}
      />
    </>
  );
};

export default SingleGamesChart;
