CREATE TABLE "Administartor" (id SERIAL NOT NULL, "login" varchar(255) NOT NULL UNIQUE, "password" varchar(255) NOT NULL, email varchar(255) NOT NULL UNIQUE, PRIMARY KEY (id));
CREATE TABLE "Publicist" (id SERIAL NOT NULL, "login" varchar(255) NOT NULL UNIQUE, "password" varchar(255) NOT NULL, email varchar(255) NOT NULL UNIQUE, PRIMARY KEY (id));
CREATE TABLE "Comment" (id SERIAL NOT NULL, contnent varchar(255) NOT NULL, points integer NOT NULL, dateOfPublication timestamp(5) NOT NULL, idArticle integer NOT NULL, idUser integer NOT NULL, PRIMARY KEY (id));
CREATE TABLE "Article" (id SERIAL NOT NULL, title varchar(255) NOT NULL UNIQUE, category varchar(255) NOT NULL, content varchar(255) NOT NULL, tags varchar(255) NOT NULL, dateOfPublication date NOT NULL, idPublicist integer NOT NULL, PRIMARY KEY (id));
CREATE TABLE "User" (id SERIAL NOT NULL, "login" varchar(255) NOT NULL UNIQUE, "password" varchar(255) NOT NULL, email varchar(255) NOT NULL UNIQUE, PRIMARY KEY (id));
CREATE TABLE "Favourites" (idUser integer NOT NULL, PRIMARY KEY (idUser));
CREATE TABLE "FavouriteTeam" (idFavourites integer NOT NULL, idTeam integer NOT NULL, PRIMARY KEY (idFavourites, idTeam));
CREATE TABLE "FavouritePlayer" (idFavourites integer NOT NULL, idPlayer integer NOT NULL, PRIMARY KEY (idFavourites, idPlayer));
CREATE TABLE "Player" (id SERIAL NOT NULL, firstName integer NOT NULL, lastName integer NOT NULL, position varchar(10) NOT NULL, heightFeet integer, heightInches integer, weightPounds integer, PRIMARY KEY (id));
CREATE TABLE "Match" (id SERIAL NOT NULL, "date" timestamp(5) NOT NULL, homeTeamScore integer NOT NULL, visitorTeamScore integer NOT NULL, period integer NOT NULL, status varchar(20) NOT NULL, "time" varchar(20), postseason bool NOT NULL, idHomeTeam integer NOT NULL, idVisitorTeam integer NOT NULL, PRIMARY KEY (id));
CREATE TABLE "Season" ("year" SERIAL NOT NULL, PRIMARY KEY ("year"));
CREATE TABLE "Team" (id SERIAL NOT NULL, abbreviation varchar(5) NOT NULL, city varchar(255) NOT NULL, conference varchar(255) NOT NULL, division varchar(255) NOT NULL, fullName varchar(255) NOT NULL, name varchar(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE "Performance" (id SERIAL NOT NULL, ast integer NOT NULL, blk integer NOT NULL, dreb integer NOT NULL, oreb integer NOT NULL, reb integer NOT NULL, pts integer NOT NULL, pf integer NOT NULL, fg3Pct integer NOT NULL, fg3a integer NOT NULL, fg3m integer NOT NULL, fgPct integer NOT NULL, fga integer NOT NULL, fgm integer NOT NULL, stl integer NOT NULL, turnover integer NOT NULL, fta integer NOT NULL, ftm integer NOT NULL, ftPct integer NOT NULL, "min" varchar(20) NOT NULL, idMatch integer NOT NULL, idSeasonPlayer integer NOT NULL, idSeason integer NOT NULL, PRIMARY KEY (id));
CREATE TABLE "SeasonPlayer" (idPlayer integer NOT NULL, idSeason integer NOT NULL, ast real NOT NULL, blk real NOT NULL, dreb real NOT NULL, oreb real NOT NULL, reb real NOT NULL, pts real NOT NULL, fg3Pct real NOT NULL, fg3a real NOT NULL, fg3m real NOT NULL, fgPct real NOT NULL, fga real NOT NULL, fgm real NOT NULL, stl real NOT NULL, turnover real NOT NULL, fta real NOT NULL, ftm real NOT NULL, ftPct real NOT NULL, pf real NOT NULL, "min" varchar(20) NOT NULL, gamesPlayed integer NOT NULL, PRIMARY KEY (idPlayer, idSeason));
ALTER TABLE "Article" ADD CONSTRAINT FKArticle293326 FOREIGN KEY (idPublicist) REFERENCES "Publicist" (id);
ALTER TABLE "Comment" ADD CONSTRAINT FKComment824657 FOREIGN KEY (idArticle) REFERENCES "Article" (id);
ALTER TABLE "Comment" ADD CONSTRAINT FKComment794266 FOREIGN KEY (idUser) REFERENCES "User" (id);
ALTER TABLE "Favourites" ADD CONSTRAINT FKFavourites260098 FOREIGN KEY (idUser) REFERENCES "User" (id);
ALTER TABLE "FavouriteTeam" ADD CONSTRAINT FKFavouriteT731867 FOREIGN KEY (idFavourites) REFERENCES "Favourites" (idUser);
ALTER TABLE "FavouritePlayer" ADD CONSTRAINT FKFavouriteP801641 FOREIGN KEY (idFavourites) REFERENCES "Favourites" (idUser);
ALTER TABLE "FavouritePlayer" ADD CONSTRAINT FKFavouriteP522041 FOREIGN KEY (idPlayer) REFERENCES "Player" (id);
ALTER TABLE "FavouriteTeam" ADD CONSTRAINT FKFavouriteT50009 FOREIGN KEY (idTeam) REFERENCES "Team" (id);
ALTER TABLE "Match" ADD CONSTRAINT FKMatch261919 FOREIGN KEY (idHomeTeam) REFERENCES "Team" (id);
ALTER TABLE "Match" ADD CONSTRAINT FKMatch61182 FOREIGN KEY (idVisitorTeam) REFERENCES "Team" (id);
ALTER TABLE "Performance" ADD CONSTRAINT FKPerformanc854503 FOREIGN KEY (idMAtch) REFERENCES "Match" (id);
ALTER TABLE "SeasonPlayer" ADD CONSTRAINT FKSeasonPlay67382 FOREIGN KEY (idPlayer) REFERENCES "Player" (id);
ALTER TABLE "SeasonPlayer" ADD CONSTRAINT FKSeasonPlay603774 FOREIGN KEY (idSeason) REFERENCES "Season" (year);
ALTER TABLE "Performance" ADD CONSTRAINT FKPerformanc704552 FOREIGN KEY (idSeasonPlayer, idSeason) REFERENCES "SeasonPlayer" (idPlayer, idSeason);
