import { Box, HStack, Spacer } from "@chakra-ui/react";
import React from "react";
import CalendarNavBar from "./CalendarNavBar";
import Day from "./Day";
import { getAllTeams, getTeamsMatchesInTimePeriod } from "../../Api";
import TeamFilterBar from "./TeamFilterBar";
import SelectedTeams from "./SelectedTeams";

Date.prototype.addDays = function (days) {
  var date = new Date(this.valueOf());
  date.setDate(date.getDate() + days);
  return date;
};

function getMonday(d) {
  d = new Date(d);
  var day = d.getDay(),
    diff = d.getDate() - day + (day == 0 ? -6 : 1); // adjust when day is sunday
  return new Date(d.setDate(diff));
}

const sDate = getMonday(new Date());
const eDate = new Date(sDate).addDays(7);

const filterMatchesFromSingleDay = (matches, day) => {
  return matches.filter((match) => {
    return (
      new Date(match.match_date).toLocaleDateString() ===
      day.toLocaleDateString()
    );
  });
};

const helperArray = [0, 1, 2, 3, 4, 5, 6];

const CalendarLayout = () => {
  const [weeklyMatches, setWeeklyMatches] = React.useState([]);
  const [selectedTeams, setSelectedTeams] = React.useState([]);
  const [startDate, setStartDate] = React.useState(sDate);
  const [endDate, setEndDate] = React.useState(eDate);

  const [teams, setTeams] = React.useState([]);

  React.useEffect(() => {
    getAllTeams().then((response) => setTeams(response.data));
  }, []);

  React.useEffect(() => {
    getTeamsMatchesInTimePeriod(
      new Date(startDate).toISOString().split("T")[0],
      new Date(endDate).toISOString().split("T")[0],
      selectedTeams
    ).then((response) => {
      setWeeklyMatches(response.data);
    });
  }, [selectedTeams]);

  return (
    <Box>
      <HStack margin={5}>
        <TeamFilterBar
          teams={teams}
          selectTeam={(teamID) => {
            if (!selectedTeams.find((t) => t.id === Number(teamID))) {
              debugger;
              setSelectedTeams([
                ...selectedTeams,
                teams.find((t) => t.id === Number(teamID)),
              ]);
            }
          }}
          selectTeams={(teams) => {
            setSelectedTeams([
              ...selectedTeams,
              ...teams.filter(
                (team) =>
                  !selectedTeams.some(
                    (selectedTeam) => selectedTeam.id === team.id
                  )
              ),
            ]);
          }}
        />
        <Spacer />
        <SelectedTeams
          selectedTeams={selectedTeams}
          deselectTeam={(team) => {
            setSelectedTeams(selectedTeams.filter((t) => t.id !== team.id));
          }}
        />
      </HStack>
      <CalendarNavBar
        forwardClick={() => {
          setStartDate(new Date(startDate).addDays(7));
          setEndDate(new Date(endDate).addDays(7));

          getTeamsMatchesInTimePeriod(
            new Date(startDate).addDays(7).toISOString().split("T")[0],
            new Date(endDate).addDays(7).toISOString().split("T")[0],
            selectedTeams
          ).then((response) => setWeeklyMatches(response.data));
        }}
        backClick={() => {
          setStartDate(new Date(startDate).addDays(-7));
          setEndDate(new Date(endDate).addDays(-7));
          getTeamsMatchesInTimePeriod(
            new Date(startDate).addDays(-7).toISOString().split("T")[0],
            new Date(endDate).addDays(-7).toISOString().split("T")[0],
            selectedTeams
          ).then((response) => setWeeklyMatches(response.data));
        }}
        startDate={startDate}
        endDate={endDate.addDays(-1)}
      />

      {helperArray.map((i) => {
        return (
          <Day
            key={i}
            date={new Date(startDate).addDays(i)}
            matches={filterMatchesFromSingleDay(
              weeklyMatches,
              new Date(startDate).addDays(i)
            )}
          />
        );
      })}
    </Box>
  );
};

export default CalendarLayout;
