package com.dppware.wekaExamplesApplication.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dppware.wekaExamplesApplication.bean.Person;
import com.dppware.wekaExamplesApplication.dao.PersonsFileDAO;
import com.dppware.wekaExamplesApplication.service.PredicctionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PersonsController {

    @Autowired
    private PredicctionService predicctionService;

    @Autowired
    private PersonsFileDAO personsDAO;

    private List<Person> alreadyShown = new ArrayList<>();

    @GetMapping(value = "/persons/all")
    @ResponseBody
    public List<Person> getAllPersons() {
        return personsDAO.getAll();
    }

    @GetMapping(value = "/persons")
    @ResponseBody
    public List<Person> getRandomPersons() {
        List<Person> choices = personsDAO.getRandom(3, false, alreadyShown);
        if (choices == null) {
            log.info("no hay mas que buscar");
        } else {
            alreadyShown.addAll(choices);
        }
        return choices;
    }

    /**
     * Return a list of choices, and the selected of them
     * 
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/persons/prediction")
    @ResponseBody
    public List<Person> getPredictionBasedOnChoices() throws Exception {
        List<Person> choices = new ArrayList<>();
        Person p;
        int matchCounter = 0;
        int notMatchCounter = 0;
        while (choices.size() < 3) {
            p = personsDAO.getRandom(1, false, choices).get(0);
            p.setChoosed(predicctionService.predice(p));
            if (p.getChoosed()) {
                if (matchCounter != 1) {
                    choices.add(p);
                    matchCounter++;
                }
            } else {
                if (notMatchCounter != 2) {
                    choices.add(p);
                    notMatchCounter++;
                }
            }
        }
        return choices;
    }

    @PostMapping(value = "/persons/choice", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void processChoice(@RequestBody Person person) {
        log.info(person + "acceptada = " + person.getChoosed());
        try {
            predicctionService.learn(person);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @PostMapping(value = "/persons", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void createChoice(@RequestBody Person person) throws IOException {
        log.info(person.toString());
        personsDAO.saveOrUpdate(person);
    }

    @PutMapping(value = "/persons", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Person updateChoice(@RequestBody Person person) throws IOException {
        log.info(person.toString());
        return personsDAO.saveOrUpdate(person);
    }

    @DeleteMapping(value = "/persons", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Person deleteChoice(@RequestBody Person person) throws IOException {
        log.info(person.toString());
        return personsDAO.saveOrUpdate(person);
    }

    /**
     * Return the ObjectModel prototype
     * 
     * @return
     */
    @GetMapping(value = "/persons/prototype")
    @ResponseBody
    public Field[] getPersonPrototype() {
        return Person.class.getDeclaredFields();
    }

}
