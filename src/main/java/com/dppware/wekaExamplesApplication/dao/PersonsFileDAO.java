package com.dppware.wekaExamplesApplication.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dppware.wekaExamplesApplication.bean.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * REad and maintain data from json local file
 * 
 * @author dpena
 *
 */
@Repository
@Slf4j
public class PersonsFileDAO {

    @Value("${data.repositoryFile}")
    private String filePath;
    /**
     * warehouse
     */
    private List<Person> inMemoryStore = new ArrayList<>();

    /**
     * Utilities
     */
    ObjectMapper mapper = new ObjectMapper();
    Random r = new Random();

    @PostConstruct
    private void initDataFromFile() {
        try {
            Path path = Paths.get(filePath);
            if (path.toFile().exists()) {
                String content = String.join("", Files.readAllLines(path));
                inMemoryStore = mapper.readValue(content, new TypeReference<List<Person>>() {
                });
            } else {
                path.toFile().createNewFile();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    /**
     * Save or update
     * 
     * @param person
     * @return
     * @throws IOException
     */
    public Person saveOrUpdate(Person person) throws IOException {
        if (inMemoryStore.contains(person)) {
            inMemoryStore.remove(person);
        }
        inMemoryStore.add(person);
        persist();
        return person;
    }

    public Person delete(Person person) throws IOException {
        if (inMemoryStore.contains(person)) {
            inMemoryStore.remove(person);
        }
        persist();
        return person;
    }

    /**
     * Persist file info
     * 
     * @throws IOException
     */
    private void persist() throws IOException {
        Path path = Paths.get(filePath);
        String str = mapper.writeValueAsString(inMemoryStore);
        byte[] strToBytes = str.getBytes();
        Files.write(path, strToBytes);
    }

    /**
     * Return a random sublist
     * 
     * @param size
     * @param acceptRepeat
     * @return
     */
    public List<Person> getRandom(int size, boolean acceptRepeat, List<Person> nonAccepted) {
        List<Person> samples = new ArrayList<>();
        if (inMemoryStore.size() >= nonAccepted.size() + size) {// exists a probability
            while (samples.size() < size) {
                int index = r.nextInt((inMemoryStore.size() - 1));
                Person p = inMemoryStore.get(index);
                // discart nonAccepted
                if (!nonAccepted.contains(p)) {
                    if (samples.contains(p)) {
                        if (acceptRepeat) {
                            samples.add(p);
                        }
                    } else {
                        samples.add(p);
                    }
                }
            }
        } else {
            samples = null;
        }
        return samples;
    }

    public List<Person> getAll() {
        return inMemoryStore;
    }

}
