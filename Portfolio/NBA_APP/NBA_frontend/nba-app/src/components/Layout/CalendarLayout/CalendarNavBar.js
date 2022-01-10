import React from "react";
import { Flex, Box, Spacer, Button, Heading } from "@chakra-ui/react";
import { ArrowForwardIcon, ArrowBackIcon } from "@chakra-ui/icons";

const CalendarNavBar = (props) => {
  return (
    <Flex margin={5}>
      <Spacer />
      <Button
        leftIcon={<ArrowBackIcon />}
        colorScheme="pink"
        variant="outline"
        onClick={() => {
          props.backClick();
        }}
      >
        Poprzedni tydzień
      </Button>
      <Heading marginLeft={5} marginRight={5}>
        {props.startDate.toLocaleDateString()} -{" "}
        {props.endDate.toLocaleDateString()}
      </Heading>
      <Button
        rightIcon={<ArrowForwardIcon />}
        colorScheme="pink"
        variant="outline"
        onClick={() => {
          props.forwardClick();
        }}
      >
        Następny tydzień
      </Button>
      <Spacer />
    </Flex>
  );
};

export default CalendarNavBar;
