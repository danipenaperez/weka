package com.dppware.wekaExamplesApplication;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.dppware.wekaExamplesApplication.bean.Person;

import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaPersonsTest {

	@Test
	public void contextLoads() {
	}

	/**
	 * J48 arbol de decision C4.5
	 * Naive Bayes
	 * SVM support Vector MAchine
	 * RNA redes neuronales
	 * @throws Exception
	 */
	@Test
	public void WekaTestJ48() throws Exception {
		DataSource source = new DataSource(new ClassPathResource("person_attrs_test.arff").getInputStream());
		Instances dataSet = source.getDataSet();
		
		// setting class attribute if the data format does not provide this information
		// For example, the XRFF format saves the class attribute information as well
		// establece cual es el class (el target a conseguir) generalmente el ultimo
		if (dataSet.classIndex() == -1) {
			dataSet.setClassIndex(dataSet.numAttributes() - 1);
		}

		// Prepare the filter
		J48 tree = new J48();
		tree.setUnpruned(true);
		tree.buildClassifier(dataSet);

		// execute
		Person person = new Person("man", "blonde",true, "actor", "http://image1.jpg", "Rocito");
		Instance ins = new DenseInstance(4);
		ins.setDataset(dataSet);
		ins.setValue(0, person.getGenre());
		ins.setValue(1, person.getHairColour());
		ins.setValue(2, person.getIsSpanish().toString());
		ins.setValue(3, person.getProfession());
		
		//Generate Report
		//String evalsummary = tree.evaluateModel();
        //System.out.println("Evaluation: " + evalsummary);
		
		double result = tree.classifyInstance(ins);
		
		//int result = (int) tree.classifyInstance(ins);
		System.out.println(result);
		
	}
	
	@Test
	public void WekaTestZeroR() throws Exception {
		DataSource source = new DataSource(new ClassPathResource("person_attrs_test.arff").getInputStream());
		Instances dataSet = source.getDataSet();

		// setting class attribute if the data format does not provide this information
		// For example, the XRFF format saves the class attribute information as well
		// establece cual es el class (el target a conseguir) generalmente el ultimo
		if (dataSet.classIndex() == -1) {
			dataSet.setClassIndex(dataSet.numAttributes() - 1);
		}

		// Prepare the filter
		ZeroR zero= new ZeroR();
		zero.buildClassifier(dataSet);

		// execute
		Person person = new Person("man", "blonde",true, "actor", "http://image1.jpg", "Rocito");
		Instance ins = new DenseInstance(4);
		ins.setDataset(dataSet);
		ins.setValue(0, person.getGenre());
		ins.setValue(1, person.getHairColour());
		ins.setValue(2, person.getIsSpanish().toString());
		ins.setValue(3, person.getProfession());
		
		//Generate Report
		//String evalsummary = tree.evaluateModel();
        //System.out.println("Evaluation: " + evalsummary);
		
		double result = zero.classifyInstance(ins);
		
		//int result = (int) tree.classifyInstance(ins);
		System.out.println(result);
		
	}
	
	
	@Test
	public void updateArfffileWithNewRegs() throws Exception {
		File f = new File("./data/person_attrs_new.arff");
		DataSource source = new DataSource(new FileInputStream(f));
		Instances dataSet = source.getDataSet();
		
		for(int i=0;i<3;i++) {
			Person person = new Person("man", "blonde",true, "actor", "http://new.jpg", "Rocito");
			Instance ins = new DenseInstance(5);
			ins.setDataset(dataSet);
			ins.setValue(0, person.getGenre());
			ins.setValue(1, person.getHairColour());
			ins.setValue(2, person.getIsSpanish().toString());
			ins.setValue(3, person.getProfession());
			ins.setValue(4, "false");	
			dataSet.add(ins);
		}
		
		
		
		
	}
	
	
	
	
//	
//	@Test
//	public void WekaTestZeroR() throws Exception {
//		DataSource source = new DataSource(new ClassPathResource("test1_data.arff").getInputStream());
//		Instances dataSet = source.getDataSet();
//
//		// setting class attribute if the data format does not provide this information
//		// For example, the XRFF format saves the class attribute information as well
//		// establece cual es el class (el target a conseguir) generalmente el ultimo
//		if (dataSet.classIndex() == -1) {
//			dataSet.setClassIndex(dataSet.numAttributes() - 1);
//		}
//
//		// Prepare the filter
//		ZeroR zero= new ZeroR();
//		zero.buildClassifier(dataSet);
//
//		// execute
//		MatchInformation match = new MatchInformation(true, true);
//		Instance ins = match.asInstance(dataSet);
//
//		int result = (int) zero.classifyInstance(ins);
//		System.out.println(result);
//		match = new MatchInformation(false, false);
//		ins = match.asInstance(dataSet);
//
//		result = (int) zero.classifyInstance(ins);
//		
//		System.out.println(result);
//
//	}



}
