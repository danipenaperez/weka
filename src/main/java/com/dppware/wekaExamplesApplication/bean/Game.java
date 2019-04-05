package com.dppware.wekaExamplesApplication.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a game
 * @author dpena
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Game {

	private String uuid;
	private String name;
	private String description;
	private String imageURL;
	/**
	 * Tenant creator
	 */
	private Tenant tenant;
}
