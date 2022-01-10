package com.florek.NBA_backend.service;

import com.florek.NBA_backend.model.basketball.Player;
import com.florek.NBA_backend.model.basketball.Team;
import com.florek.NBA_backend.model.people.FavouritePlayer;
import com.florek.NBA_backend.model.people.FavouriteTeam;
import com.florek.NBA_backend.model.people.Person;
import com.florek.NBA_backend.repository.*;
import com.florek.NBA_backend.utils.Utilities;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final FavouriteTeamRepository favouriteTeamRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final FavouritePlayerRepository favouritePlayerRepository;


    @Autowired
    public PersonService(PersonRepository personRepository,
                         FavouriteTeamRepository favouriteTeamRepository,
                         TeamRepository teamRepository,
                         PlayerRepository playerRepository,
                         FavouritePlayerRepository favouritePlayerRepository) {
        this.personRepository = personRepository;
        this.favouriteTeamRepository = favouriteTeamRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.favouritePlayerRepository = favouritePlayerRepository;
    }

    public Optional<Person> createPerson(Person person){
        if(isEmailCorrect(person.getEmail()) &&isLoginUnique(person.getLogin()) && isEmailUnique(person.getEmail())){
            if (personRepository.findById(person.getId()).isEmpty()) {
                person.setPassword(BCrypt.hashpw(person.getPassword(), BCrypt.gensalt()));
                person.setLoggedIn(true);
                personRepository.save(person);
                person.setPassword("");
                person.setEmail("");
                return Optional.of(person);
            }
        }
        return Optional.empty();
    }


    public String deletePerson(int personID){
        if(personRepository.findById(personID).isPresent()){
            personRepository.deleteById(personID);
            return "Deleted";
        }
        return "Deleting unsuccessful";
    }

    public String changePersonRole(int personID, String role){
        personRepository.findById(personID)
                .map(person -> {
                    person.setRole(role);
                    return personRepository.save(person);
                });
        return "Current role: " + role;
    }

    public void editPerson(int personID, Person newData){
        personRepository.findById(personID)
                .map(person -> {
                    person.setEmail(newData.getEmail());
                    person.setLogin(newData.getLogin());
                    person.setPassword(newData.getPassword());
                    return personRepository.save(person);
                });
    }

    public List<Person> getAllPeople(){return personRepository.findAll();}

    public Optional<Person> getPersonByID(int personID){return personRepository.findById(personID);}

    public void blockPerson(int personID){
        personRepository.findById(personID)
                .map(person -> {
                    person.setAbleToComment(false);
                    return personRepository.save(person);
                });
    }

    public void unblockPerson(int personID){
        personRepository.findById(personID)
                .map(person -> {
                    person.setAbleToComment(true);
                    return personRepository.save(person);
                });
    }

    public Optional<Person> logIn(String login, String password){
        if(personRepository.getPersonByLogin(login).isPresent()) {
            if (BCrypt.checkpw(password, personRepository.getUserHashedPassword(login))) {
                Person person = personRepository.getPersonByLogin(login).get();
                if (!person.isLoggedIn()) {
                    person.setLoggedIn(true);
                    personRepository.save(person);
                }
                person.setPassword("");
                person.setEmail("");
                return Optional.of(person);
            }
        }
            return Optional.empty();

    }

    public void logOut(int personID){
        personRepository.findById(personID).map(person -> {
            person.setLoggedIn(false);
            return personRepository.save(person);
        });
    }

    public boolean isLoginUnique(String login){
        return personRepository.countByLogin(login) == 0;
    }

    public boolean isEmailUnique(String email){
        return personRepository.countByEmail(email) == 0;
    }

    public void addAccountPoints(int personID){
        personRepository.findById(personID)
                .map(person -> {
                    person.setAccountPoints(person.getAccountPoints() + 5);
                    return personRepository.save(person);
                });
    }

    public void addTeamToFavourites(int personID, int teamID){
        if(teamRepository.findById(teamID).isPresent() && personRepository.findById(personID).isPresent()){
            FavouriteTeam favouriteTeam = new FavouriteTeam(teamRepository.findById(teamID).get(), personRepository.findById(personID).get());
            favouriteTeamRepository.save(favouriteTeam);
        }
    }

    public void deleteTeamFromFavourites(int personID, int teamID){
        favouriteTeamRepository.deleteFavouriteTeam(personID, teamID);
    }

    public List<Team> getFavouriteTeams(int personID){
        List<Integer> favourites = favouriteTeamRepository.getFavouriteTeams(personID);
        List<Team> favouriteTeams = new ArrayList<>();
        for (Integer favouriteTeamID : favourites) {
            favouriteTeams.add(teamRepository.findById(favouriteTeamID).get());
        }
        return favouriteTeams;
    }


    public void addPlayerToFavourites(int personID, int playerID){
        if(playerRepository.findById(playerID).isPresent() && personRepository.findById(personID).isPresent()){
            FavouritePlayer favouritePlayer = new FavouritePlayer(playerRepository.findById(playerID).get(), personRepository.findById(personID).get());
            favouritePlayerRepository.save(favouritePlayer);
        }
    }

    public void deletePlayerFromFavourites(int personID, int playerID){
        favouritePlayerRepository.deleteFavouritePlayer(personID, playerID);
    }

    public List<Player> getFavouritePlayers(int personID){
        List<Integer> favourites = favouritePlayerRepository.getFavouritePlayers(personID);
        List<Player> favouritePlayers = new ArrayList<>();
        for (Integer favouritePlayerID : favourites) {
            favouritePlayers.add(playerRepository.findById(favouritePlayerID).get());
        }
        return favouritePlayers;
    }


    public boolean isEmailCorrect(String email) {
        Matcher matcher = Utilities.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }


}
