package com.dppware.wekaExamplesApplication.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Represent a generic Object model
 * @author dpena
 *
 */
public class GenericModel {
	
	/**
	 * Attributes and types
	 * name, string
	 * haircolour, fromValues {brown|red} (determined list)
	 * age, int
	 * 
	 */
	private Map<String, String> attributes = new HashMap<String, String>();

}
