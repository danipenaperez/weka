package com.dppware.wekaExamplesApplication.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.dppware.wekaExamplesApplication.bean.MatchInformation;
import com.dppware.wekaExamplesApplication.bean.Person;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

@Service
public class PredicctionService {

	
	File f = new File("./data/person_attrs_new.arff");
	
	private DataSource source;
	private Instances dataSet;
	
	
	@PostConstruct
	private void init() throws Exception {
		this.source = new DataSource(new FileInputStream(f));
		this.dataSet = source.getDataSet();
		// setting class attribute if the data format does not provide this information
		// For example, the XRFF format saves the class attribute information as well
		// establece cual es el class (el target a conseguir) generalmente el ultimo
		if (dataSet.classIndex() == -1) {
			dataSet.setClassIndex(dataSet.numAttributes() - 1);
		}
	}
	
	/**
	 * Store into current DataSet the option passed
	 * @param person
	 * @throws Exception
	 */
	public void learn(Person person) throws Exception {
		//Create instance to Test
		Instance ins = new DenseInstance(5);
		ins.setDataset(dataSet);
		ins.setValue(0, person.getGenre());
		ins.setValue(1, person.getHairColour());
		ins.setValue(2, person.getIsSpanish().toString());
		ins.setValue(3, person.getProfession());
		ins.setValue(4, person.getChoosed().toString());	
		dataSet.add(ins);
		
		//log
		System.out.println("current learned data " +this.dataSet.size());
	}
	
	/**
	 * TODO muy tipado
	 * @param person
	 * @return
	 * @throws Exception
	 */
	public Boolean predice(Person person) throws Exception {
		// Prepare the filter
		J48 tree = new J48();
		tree.setUnpruned(true);
		tree.buildClassifier(dataSet);
		
		//Create instance to Test
		Instance ins = new DenseInstance(4);
		ins.setDataset(dataSet);
		ins.setValue(0, person.getGenre());
		ins.setValue(1, person.getHairColour());
		ins.setValue(2, person.getIsSpanish().toString());
		ins.setValue(3, person.getProfession());
		
		int result = (int) tree.classifyInstance(ins);
		
		return result==1;
	}
	
	
	/**
	 * Persist current DataSet to file
	 * @throws IOException
	 */
	private void persistCurrentDataSet() throws IOException {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataSet);
		saver.setFile(f);
		saver.writeBatch();
	}
	
	
	
	
	
	public Classifier buildClassifier(Instances traindataset) {
        MultilayerPerceptron m = new MultilayerPerceptron();
        try {
            m.buildClassifier(traindataset);

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return m;
    }
	
	public String evaluateModel(Classifier model, Instances traindataset, Instances testdataset) {
        Evaluation eval = null;
        try {
            // Evaluate classifier with test dataset
            eval = new Evaluation(traindataset);
            eval.evaluateModel(model, testdataset);
        } catch (Exception ex) {
        	System.out.println(ex.toString());
        }
        return eval.toSummaryString("", true);
    }
	
	
	public void saveModel(Classifier model, String modelpath) {

        try {
            SerializationHelper.write(modelpath, model);
        } catch (Exception ex) {
        	System.out.println(ex.toString());
        }
    }
	
	
	
}
