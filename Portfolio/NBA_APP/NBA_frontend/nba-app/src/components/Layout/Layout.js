import React from "react";
import Aux from "../../hoc/Auxiliary";
import MenuBar from "../../containers/MenuBar";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import MainArticleLayout from "../MainArticleLayout/MainArticleLayout";
import AccountLayout from "./AccountLayout/AccountLayout";
import SingleArticleLayout from "./SingleArticleLayout/SingleArticleLayout";
import PlayersLayout from "./PlayersLayout/PlayersLayout";
import CalendarLayout from "./CalendarLayout/CalendarLayout";
import TeamsLayout from "./TeamsLayout/TeamsLayout";

const layout = () => (
  <Router>
    <Aux>
      <MenuBar />
      <Switch>
        <Route path="/">
          <MainArticleLayout />
        </Route>
        <Route path="/kalendarz">
          <CalendarLayout />
        </Route>
        <Route path="/zawodnicy">
          <PlayersLayout />
        </Route>
        <Route path="/konto">
          <AccountLayout />
        </Route>
        <Route path="/druzyny">
          <TeamsLayout />
        </Route>
        <Route path="/artykul">
          <SingleArticleLayout />
        </Route>
      </Switch>
    </Aux>
  </Router>
);

export default layout;
