import axios from "axios";

export const getArticleImage = (articleID) => {
  return axios.get(`http://localhost:8080/nba/articles/images/${articleID}`);
};

export const getArticleByID = (articleID) => {
  return axios.get(`http://localhost:8080/nba/articles/${articleID}`);
};

export const deleteComment = (authorID, commentID) => {
  return axios.delete(
    `http://localhost:8080/nba/comments/${authorID}/${commentID}`
  );
};

export const addComment = (authorID, newComment) => {
  return axios.post(
    `http://localhost:8080/nba/comments/${authorID}`,
    newComment
  );
};

export const editComment = (authorID, commentID, newComment) => {
  return axios.put(
    `http://localhost:8080/nba/comments/${authorID}/${commentID}`,
    newComment
  );
};

export const getPlayersByName = (lastName) => {
  return axios.get(`http://localhost:8080/nba/players/name/${lastName}`);
};

export const comparePlayers = (playerIDs, matches, season) => {
  return axios.get(
    `http://localhost:8080/nba/players/games-stats/compare?playerIDs=${playerIDs.join()}&matches=${matches}&season=${season}`
  );
};

export const comparePlayerCareers = (playerIDs, seasons) => {
  return axios.get(
    `http://localhost:8080/nba/players/season-stats/compare?playerIDs=${playerIDs.join()}&seasons=${seasons}`
  );
};

export const checkLogin = (login) => {
  return axios.get(`http://localhost:8080/nba/accounts/checkLogin/${login}`);
};

export const checkEmail = (email) => {
  return axios.get(`http://localhost:8080/nba/accounts/checkEmail/${email}`);
};

export const createUserAccount = (newUser) => {
  return axios.post(`http://localhost:8080/nba/accounts`, newUser);
};

export const logIn = (login, password) => {
  return axios.put(
    `http://localhost:8080/nba/accounts/login?login=${login}&password=${password}`
  );
};

export const getAllTeams = () => {
  return axios.get(`http://localhost:8080/nba/teams`);
};

export const getTeamsMatchesInTimePeriod = (startDate, endDate, teams) => {
  return axios.get(
    `http://localhost:8080/nba/matches/between/teams?startDate=${startDate}&endDate=${endDate}&teamIDs=${teams
      .map((team) => team.id)
      .join()}`
  );
};

export const getAllCategories = () => {
  return axios.get("http://localhost:8080/nba/articles/categories");
};

export const addArticle = (newArticle) => {
  return axios.post("http://localhost:8080/nba/articles", newArticle);
};

export const getFavouriteTeams = (userID) => {
  return axios.get(`http://localhost:8080/nba/accounts/fav/team/${userID}`);
};

export const addFavouritePlayer = (userID, playerID) => {
  return axios.post(
    `http://localhost:8080/nba/accounts/fav/player?personID=${userID}&playerID=${playerID}`
  );
};

export const deleteFavouritePlayer = (userID, playerID) => {
  return axios.delete(
    `http://localhost:8080/nba/accounts/fav/player?personID=${userID}&playerID=${playerID}`
  );
};

export const getFavouritePlayers = (userID) => {
  return axios.get(`http://localhost:8080/nba/accounts/fav/player/${userID}`);
};

export const uploadImage = (image, articleID) => {
  let formData = new FormData();
  formData.append("file", image[0]);
  debugger;
  return axios.post(
    `http://localhost:8080/nba/articles/images-upload/${articleID}`,
    formData,
    {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    }
  );
};

export const addFavouriteTeam = (userID, teamID) => {
  return axios.post(
    `http://localhost:8080/nba/accounts/fav/team?personID=${userID}&teamID=${teamID}`
  );
};

export const deleteFavouriteTeam = (userID, teamID) => {
  return axios.delete(
    `http://localhost:8080/nba/accounts/fav/team?personID=${userID}&teamID=${teamID}`
  );
};

export const getArticlesByAuthor = (personID) => {
  return axios.get(`http://localhost:8080/nba/articles/author/${personID}`);
};

export const deleteArticle = (articleID) => {
  return axios.delete(`http://localhost:8080/nba/articles/${articleID}`);
};

export const editArticle = (articleID, updatedArticle) => {
  return axios.put(
    `http://localhost:8080/nba/articles/${articleID}`,
    updatedArticle
  );
};

export const getSinglePlayerStats = (playerID) => {
  return axios.get(
    `http://localhost:8080/nba/players/games-stats/single?playerID=${playerID}&matches=10&season=2020`
  );
};
