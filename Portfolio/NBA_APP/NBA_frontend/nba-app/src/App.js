import "./App.css";
import { ChakraProvider } from "@chakra-ui/react";
import MainArticleLayout from "./components/MainArticleLayout/MainArticleLayout";
import React from "react";
import Aux from "./hoc/Auxiliary";
import MenuBar from "./containers/MenuBar";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import AccountLayout from "./components/Layout/AccountLayout/AccountLayout";
import SingleArticleLayout from "./components/Layout/SingleArticleLayout/SingleArticleLayout";
import PlayersLayout from "./components/Layout/PlayersLayout/PlayersLayout";
import CalendarLayout from "./components/Layout/CalendarLayout/CalendarLayout";
import ManageArticleLayout from "./components/Layout/ManageArticlesLayout/ManageArticlesLayout";
import NotAuthorized from "./containers/NotAuthorized";

export const UserContext = React.createContext({ user: null, setUser: null });

const calendarPath = "/kalendarz";
const playersPath = "/zawodnicy";
const accountPath = "/konto";
const newArticlePath = "/nowyArtykul";
const singleArticlePath = "/artykul/:id";
const homePath = "/";

function App() {
  const [user, setUser] = React.useState(null);
  React.useEffect(() => {
    const loggedInUser = localStorage.getItem("nbaAppUser");
    if (loggedInUser) {
      const foundUser = JSON.parse(loggedInUser);
      setUser(foundUser);
    }
  }, []);
  return (
    <ChakraProvider>
      <UserContext.Provider value={{ user, setUser }}>
        <div className="App">
          <Router>
            <Aux>
              <MenuBar />
              <Switch>
                <Route path={calendarPath}>
                  <CalendarLayout />
                </Route>
                <Route path={playersPath}>
                  <PlayersLayout />
                </Route>
                <Route path={accountPath}>
                  {user !== null ? <AccountLayout /> : <NotAuthorized />}
                </Route>
                <Route path={newArticlePath}>
                  {user !== null && user.role === "Publicist" ? (
                    <ManageArticleLayout />
                  ) : (
                    <NotAuthorized />
                  )}
                </Route>
                <Route path={singleArticlePath}>
                  <SingleArticleLayout />
                </Route>
                <Route path={homePath}>
                  <MainArticleLayout user={user} />
                </Route>
              </Switch>
            </Aux>
          </Router>
        </div>
      </UserContext.Provider>
    </ChakraProvider>
  );
}

export default App;
