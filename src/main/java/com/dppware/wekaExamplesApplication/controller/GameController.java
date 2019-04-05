package com.dppware.wekaExamplesApplication.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dppware.wekaExamplesApplication.bean.Game;
import com.dppware.wekaExamplesApplication.bean.Tenant;

import lombok.extern.slf4j.Slf4j;
/**
 * Game list controller
 * @author dpena
 *
 */
@RestController
@RequestMapping("game")
@Slf4j
public class GameController {

	private List<Game> availableGames = Arrays.asList(
			new Game("game23ds","Personas", "Adivina personas de tus preferencias","https://cdn1.iconfinder.com/data/icons/people-set-2/148/Mens-512.png",new Tenant("1", "danipenaperez@gmail.com") ),
	    	new Game("game43fgh","Ciudades", "Adivina ciudades que te gustan","https://www.infobae.com/new-resizer/9et19KTqW7-TvCYJJuNcNKppmso=/1200x0/filters:quality(100)/s3.amazonaws.com/arc-wordpress-client-uploads/infobae-wp/wp-content/uploads/2017/03/21102319/ciudades-mas-caras-HOME-2.jpg",new Tenant("2", "cities@trips.com") ),
	    	new Game("game67gg","Productos", "Cuentame algo de los productos que te interesan","https://cdnus.melaleuca.com/Images/about/home-page/desktop/products--large.png", new Tenant("3", "badulaque@shoping.com"))
	);
	
	/**
     * Create a model for the passed PrototypeId
     * @param person
     * @throws Exception 
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGames() throws Exception {

    	return availableGames;
    }
	
}
