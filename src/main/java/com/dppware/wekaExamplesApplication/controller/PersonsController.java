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

    /**
     * @RequestMapping(value = "/write", method = RequestMethod.GET) public void createfile() throws IOException { for(Person p:persons) {
     *                       personsDAO.save(p); }
     * 
     *                       personsDAO.getAll();
     * 
     *                       }
     * 
     * 
     * 
     *                       List<Person> persons = Arrays.asList( new Person("man", "brown", true, "sport",
     *                       "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Iker_Casillas_2.jpg/245px-Iker_Casillas_2.jpg",
     *                       "Iker Casillas" ), new Person("man", "nohair", false, "sport",
     *                       "https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Zinedine_Zidane_by_Tasnim_01.jpg/245px-Zinedine_Zidane_by_Tasnim_01.jpg",
     *                       "zidane" ), new Person("woman", "blonde", false, "actor",
     *                       "https://st.depositphotos.com/1814084/1706/i/950/depositphotos_17062469-stock-photo-cameron-diaz.jpg", "Cameron
     *                       diaz" ), new Person("woman", "blonde", false, "actor",
     *                       "https://media.gettyimages.com/photos/actress-jennifer-aniston-arrives-at-the-21st-annual-screen-actors-picture-id462232162?s=612x612",
     *                       "JenniferAniston" ), new Person("woman", "blonde", false, "actor",
     *                       "https://fotografias.antena3.com/clipping/cmsimages02/2018/07/04/BCBEA4BC-4103-4F1A-9579-CDA4ED6E166F/63.jpg",
     *                       "Kaley cuoco" ), new Person("man", "brown", false, "actor",
     *                       "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/George_Clooney_2016.jpg/250px-George_Clooney_2016.jpg",
     *                       "Clooney" ), new Person("man", "blonde", false, "actor",
     *                       "https://static.lamusica.com/wp-content/uploads/sites/14/2012/05/celebrities-brad-pitt-708595.jpg", "Brad Pitt"
     *                       ), new Person("man", "brown", true, "actor", "https://mediamass.net/jdd/public/documents/celebrities/1166.jpg",
     *                       "el duque" ), new Person("man", "brown", true, "actor",
     *                       "http://es.web.img2.acsta.net/c_215_290/medias/nmedia/18/92/45/07/20200361.jpg", "Mario casas" ), new
     *                       Person("man", "brown", true, "actor",
     *                       "https://m.media-amazon.com/images/M/MV5BMTY1NTc4NTYzMF5BMl5BanBnXkFtZTcwNDIwOTY1NA@@._V1_.jpg", "Javier
     *                       Barden" ), new Person("man", "blonde", false, "actor",
     *                       "https://www.biography.com/.image/c_fit%2Ccs_srgb%2Cfl_progressive%2Ch_406%2Cq_auto:good%2Cw_620/MTE4MDAzNDEwNTAxNTM1MjQ2/leonardo-dicaprio-9273992-2-raw.jpg",
     *                       "Leonardo Dicaprio" ), new Person("man", "blonde", false, "sport",
     *                       "https://i.pinimg.com/originals/bf/51/80/bf5180726274e4433b1c52de38e41b12.jpg", "Beckham" ), new Person("man",
     *                       "blonde", true, "sport",
     *                       "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Russia-Spain_2017_%286%29.jpg/270px-Russia-Spain_2017_%286%29.jpg",
     *                       "Sergio Ramos" ), new Person("woman", "brown", true, "actor",
     *                       "https://m.guiadelocio.com/var/guiadelocio.com/storage/images/cine/personajes/ursula-corbero/10294684-8-esl-ES/ursula-corbero.jpg",
     *                       "Ursula" ), new Person("woman", "brown", true, "actor",
     *                       "https://st-listas.20minutos.es/images/2012-02/321066/3402278_249px.jpg?1330726168", "Clara Lago" ), new
     *                       Person("woman", "brown", true, "actor",
     *                       "http://es.web.img2.acsta.net/c_215_290/pictures/18/03/23/15/18/0881779.jpg", "Penelope Cruz" ), new
     *                       Person("woman", "brown", true, "actor",
     *                       "https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Pilar_Rubio_2019.jpg/245px-Pilar_Rubio_2019.jpg",
     *                       "Pilar Rubio" ), new Person("woman", "blonde", true, "sport",
     *                       "https://www.quien.net/wp-content/uploads/Mireia-Belmonte.jpg", "Mireia belmonte" ), new Person("man", "brown",
     *                       false, "actor",
     *                       "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/JohnnyDepp2018.jpg/220px-JohnnyDepp2018.jpg",
     *                       "Jhonny Deep" ), new Person("man", "brown", false, "actor",
     *                       "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Tom_Cruis.jpg/220px-Tom_Cruis.jpg", "Tom cruise" ),
     *                       new Person("man", "blonde", false, "actor",
     *                       "https://www.alohacriticon.com/wp-content/uploads/2003/07/robert-redford-fotos.jpg", "Robert Refor" ), new
     *                       Person("man", "brown", true, "sport",
     *                       "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/Messi_vs_Nigeria_2018.jpg/245px-Messi_vs_Nigeria_2018.jpg",
     *                       "Leo Messi" ), new Person("man", "brown", true, "actor",
     *                       "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Spain-Tahiti%2C_Confederations_Cup_2013_%2802%29_%28Villa_crop%29.jpg/245px-Spain-Tahiti%2C_Confederations_Cup_2013_%2802%29_%28Villa_crop%29.jpg",
     *                       "David Villa" ), new Person("man", "brown", true, "tv",
     *                       "http://huelvaya.es/wp-content/uploads/2011/11/risto-mejide-1.jpg", "Risto" ), new Person("woman", "blonde",
     *                       true, "tv",
     *                       "https://s3.eestatic.com/2018/10/24/corazon/famosos/Belen_Esteban_Menendez-Empresas_de_famosos-Tono_Sanchis-Famosos_347978627_102848612_1706x1706.jpg",
     *                       "Belen esteban" ), new Person("woman", "brown", true, "tv", "https://cdn.20m.es/img/2007/04/02/578567.jpg",
     *                       "Nuria roca" ), new Person("man", "brown", true, "sport",
     *                       "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkHi90gCkuniebwTzc6tTwnirGJfq3eV5u_UVENKt8XNUmm03v",
     *                       "Pau Gasol" ), new Person("man", "brown", false, "sport",
     *                       "http://cdn.24.co.za/files/Cms/General/d/8205/1cb28728b5ee4ecca34eb588691e00a1.png", "Federer" ), new
     *                       Person("man", "brown", true, "politician",
     *                       "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Felipe_Gonz%C3%A1lez_1986_%28cropped%29.jpg/200px-Felipe_Gonz%C3%A1lez_1986_%28cropped%29.jpg",
     *                       "Felipe gonzales" ), new Person("man", "brown", false, "politician",
     *                       "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/President_Barack_Obama.jpg/220px-President_Barack_Obama.jpg",
     *                       "Obama" ), new Person("man", "brown", true, "actor",
     *                       "http://marketing.sandiego.edu/alumnihonors19/wp-content/uploads/sites/26/2017/01/jim-parsons-400-500.jpg",
     *                       "Jim parson" ) );
     * 
     **/

}
