package com.dppware.wekaExamplesApplication.bean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonChoiceInfo {
	/**
	 * @ATTRIBUTE genre	{man, woman}
	 */
	private String genre;
	/**
	 * @ATTRIBUTE hairColour {blode,red,brown,nohair}
	 */
	private String hairColour;
	/**
	 * @ATTRIBUTE isSpanish {yes,no}
	 */
	private Boolean isSpanish;
	/**
	 * @ATTRIBUTE profession {actor,sport}   
	 */
	private String profession;

	/**
	 * class (yes,no)
	 */
	private String choosed;
}
