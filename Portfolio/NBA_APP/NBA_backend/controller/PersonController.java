package com.florek.NBA_backend.controller;


import com.florek.NBA_backend.model.basketball.Player;
import com.florek.NBA_backend.model.basketball.Team;
import com.florek.NBA_backend.model.people.FavouriteTeam;
import com.florek.NBA_backend.model.people.Person;
import com.florek.NBA_backend.service.PersonService;
import com.florek.NBA_backend.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("nba/accounts")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public RestResponse<Person> createPerson(@Valid @NonNull @RequestBody Person person){
       Optional<Person> createdPerson =  personService.createPerson(person);
       if(createdPerson.isPresent()){
           return new RestResponse<>(HttpStatus.CREATED, "Konto stworzone", createdPerson.get());
       }else{
           return new RestResponse<>(HttpStatus.BAD_REQUEST, "Operacja nie powiodła się");
       }
    }

    @DeleteMapping(path = "{id}")
    public String deletePerson(@PathVariable("id") int id) {
        return personService.deletePerson(id);
    }

    @PutMapping(path = "{id}/{role}")
    public String changePersonRole(@PathVariable("id") int id, @PathVariable ("role") String role){
        return personService.changePersonRole(id, role);
    }

    @PutMapping(path = "{id}")
    public void editPerson(@PathVariable("id") int id, @Valid @NonNull @RequestBody Person person){
        personService.editPerson(id, person);
    }

    @GetMapping
    public List<Person> getAllUsers() {
        return personService.getAllPeople();
    }

    @GetMapping(path = "{id}")
    public Person getUserById(@PathVariable("id") int id){
        return personService.getPersonByID(id).orElse(null);
    }

    @PutMapping(path = "/block/{id}")
    public void blockPerson(@PathVariable("id") int id){ personService.blockPerson(id); }

    @PutMapping(path = "/unblock/{id}")
    public void unblockPerson(@PathVariable("id") int id){ personService.unblockPerson(id); }

    @PutMapping(path = "/login")
    public RestResponse<Person> logIn(@RequestParam(name = "login") String login, @RequestParam(name = "password") String password){
        Optional<Person> person =  personService.logIn(login, password);
        if(person.isPresent()){
            return new RestResponse<>(HttpStatus.OK, "Zalogowano", person.get());
        }else{
            return new RestResponse<>(HttpStatus.NOT_ACCEPTABLE, "Operacja logowania nie powiodła się");
        }
    }

    @PutMapping(path = "/logout/{id}")
    public void logOut(@PathVariable int id){
        personService.logOut(id);
    }

    @GetMapping(path = "/fav/team/{id}")
    public List<Team> getFavouriteTeams(@PathVariable("id") int personID){
        return personService.getFavouriteTeams(personID);
    }

    @PostMapping(path = "/fav/team")
    public void addFavouriteTeam(@RequestParam(name = "personID") int personID, @RequestParam(name = "teamID") int teamID){
        personService.addTeamToFavourites(personID, teamID);
    }

    @DeleteMapping(path = "/fav/team")
    public void deleteFavouriteTeam(@RequestParam(name = "personID") int personID, @RequestParam(name = "teamID") int teamID){
        personService.deleteTeamFromFavourites(personID, teamID);
    }

    @GetMapping(path = "/fav/player/{id}")
    public List<Player> getFavouritePlayers(@PathVariable("id") int personID){
        return personService.getFavouritePlayers(personID);
    }

    @PostMapping(path = "/fav/player")
    public void addFavouritePlayer(@RequestParam(name = "personID") int personID, @RequestParam(name = "playerID") int playerID){
        personService.addPlayerToFavourites(personID, playerID);
    }

    @DeleteMapping(path = "/fav/player")
    public void deleteFavouritePlayer(@RequestParam(name = "personID") int personID, @RequestParam(name = "playerID") int playerID){
        personService.deletePlayerFromFavourites(personID, playerID);
    }

    @GetMapping(path = "/checkLogin/{login}")
    public boolean checkLogin(@PathVariable(name = "login") String login){
        return !personService.isLoginUnique(login);
    }

    @GetMapping(path = "/checkEmail/{email}")
    public boolean checkEmail(@PathVariable(name = "email") String email){
        return !personService.isEmailUnique(email);
    }
}
