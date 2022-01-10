import React from "react";
import { Stack, Avatar, Spacer, Flex, Text } from "@chakra-ui/react";
import { teams } from "../../../assets/teams";

const Match = (props) => {
  return (
    <Flex
      borderWidth="5px"
      padding={5}
      maxWidth="xs"
      borderRadius="lg"
      borderColor="rgba(49, 37, 161, 0.8)"
      backgroundColor="rgba(20, 89, 245, 0.1)"
    >
      <Avatar
        name={
          teams.filter(
            (team) => team.shortName === props.match.home_team.abbreviation
          )[0].shortName
        }
        src={
          teams.filter(
            (team) => team.shortName === props.match.home_team.abbreviation
          )[0].logo
        }
      />
      <Spacer />
      <Stack>
        <Text fontWeight="semibold">
          {props.match.home_team.abbreviation} vs{" "}
          {props.match.visitor_team.abbreviation}
        </Text>
        <Text as="samp">
          {" "}
          {props.match.home_team_score} : {props.match.visitor_team_score}{" "}
        </Text>
      </Stack>
      <Spacer />
      <Avatar
        name={
          teams.filter(
            (team) => team.shortName === props.match.visitor_team.abbreviation
          )[0].shortName
        }
        src={
          teams.filter(
            (team) => team.shortName === props.match.visitor_team.abbreviation
          )[0].logo
        }
      />
    </Flex>
  );
};

export default Match;
