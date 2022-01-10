import React from "react";
import Match from "./Match";
import { Grid, Box, Stack } from "@chakra-ui/react";

const Day = (props) => {
  return (
    <Stack
      borderWidth="5px"
      margin={5}
      padding={5}
      borderRadius="lg"
      borderColor="rgba(20, 89, 245, 0.1)"
    >
      <Box height="50px" fontWeight="semibold" fontSize="lg">
        {props.date.toLocaleDateString()} -{" "}
        {props.date.toLocaleDateString("pl-PL", { weekday: "long" })}
      </Box>
      {props.matches.length === 0 ? (
        <div> Brak meczy w danym dniu</div>
      ) : (
        <Grid templateColumns="repeat(4, 1fr)" gap={5}>
          {props.matches.map((match) => (
            <Match key={match.id} match={match} />
          ))}
        </Grid>
      )}
    </Stack>
  );
};

export default Day;
