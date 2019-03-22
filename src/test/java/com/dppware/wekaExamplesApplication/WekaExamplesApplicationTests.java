package com.dppware.wekaExamplesApplication;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.dppware.wekaExamplesApplication.bean.MatchInformation;
import com.dppware.wekaExamplesApplication.bean.Person;

import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WekaExamplesApplicationTests {

	@Test
	public void contextLoads() {
	}

	/**
	 * Inicializa las cuestiones para entrenarse
	 */
	private void initData() {
		List persons = Arrays.asList(
					new Person("man", "brown", true, "sport", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Iker_Casillas_2.jpg/245px-Iker_Casillas_2.jpg", "Iker Casillas" ),
					new Person("man", "nohair", false, "sport", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Zinedine_Zidane_by_Tasnim_01.jpg/245px-Zinedine_Zidane_by_Tasnim_01.jpg", "zidane" ),
					new Person("woman", "blonde", false, "actor", "https://st.depositphotos.com/1814084/1706/i/950/depositphotos_17062469-stock-photo-cameron-diaz.jpg", "Cameron diaz" ),
					new Person("woman", "blonde", false, "actor", "https://media.gettyimages.com/photos/actress-jennifer-aniston-arrives-at-the-21st-annual-screen-actors-picture-id462232162?s=612x612", "JenniferAniston" ),
					new Person("woman", "blonde", false, "actor", "https://fotografias.antena3.com/clipping/cmsimages02/2018/07/04/BCBEA4BC-4103-4F1A-9579-CDA4ED6E166F/63.jpg", "Kaley cuoco" ),
					new Person("man", "brown", false, "actor", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/George_Clooney_2016.jpg/250px-George_Clooney_2016.jpg", "Clooney" ),
					new Person("man", "blonde", false, "actor", "https://static.lamusica.com/wp-content/uploads/sites/14/2012/05/celebrities-brad-pitt-708595.jpg", "Brad Pitt" ),
					new Person("man", "brown", true, "actor", "https://mediamass.net/jdd/public/documents/celebrities/1166.jpg", "el duque" ),
					new Person("man", "brown", true, "actor", "http://es.web.img2.acsta.net/c_215_290/medias/nmedia/18/92/45/07/20200361.jpg", "Mario casas" ),
					new Person("man", "brown", true, "actor", "https://m.media-amazon.com/images/M/MV5BMTY1NTc4NTYzMF5BMl5BanBnXkFtZTcwNDIwOTY1NA@@._V1_.jpg", "Javier Barden" ),
					new Person("man", "blonde", false, "actor", "https://www.biography.com/.image/c_fit%2Ccs_srgb%2Cfl_progressive%2Ch_406%2Cq_auto:good%2Cw_620/MTE4MDAzNDEwNTAxNTM1MjQ2/leonardo-dicaprio-9273992-2-raw.jpg", "Leonardo Dicaprio" ),
					new Person("man", "blonde", false, "sport", "https://i.pinimg.com/originals/bf/51/80/bf5180726274e4433b1c52de38e41b12.jpg", "Beckham" ),
					new Person("man", "blonde", true, "sport", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Russia-Spain_2017_%286%29.jpg/270px-Russia-Spain_2017_%286%29.jpg", "Sergio Ramos" ),
					new Person("woman", "brown", true, "actor", "https://m.guiadelocio.com/var/guiadelocio.com/storage/images/cine/personajes/ursula-corbero/10294684-8-esl-ES/ursula-corbero.jpg", "Ursula" ),
					new Person("woman", "brown", true, "actor", "https://st-listas.20minutos.es/images/2012-02/321066/3402278_249px.jpg?1330726168", "Clara Lago" ),
					new Person("woman", "brown", true, "actor", "http://es.web.img2.acsta.net/c_215_290/pictures/18/03/23/15/18/0881779.jpg", "Penelope Cruz" ),
					new Person("woman", "brown", true, "actor", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Pilar_Rubio_2019.jpg/245px-Pilar_Rubio_2019.jpg", "Pilar Rubio" ),
					new Person("woman", "blonde", true, "sport", "https://www.quien.net/wp-content/uploads/Mireia-Belmonte.jpg", "Mireia belmonte" ),
					new Person("man", "brown", false, "actor", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/JohnnyDepp2018.jpg/220px-JohnnyDepp2018.jpg", "Jhonny Deep" ),
					new Person("man", "brown", false, "actor", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Tom_Cruis.jpg/220px-Tom_Cruis.jpg", "Tom cruise" ),
					new Person("man", "blonde", false, "actor", "https://www.alohacriticon.com/wp-content/uploads/2003/07/robert-redford-fotos.jpg", "Robert Refor" ),
					new Person("man", "brown", true, "sport", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/Messi_vs_Nigeria_2018.jpg/245px-Messi_vs_Nigeria_2018.jpg", "Leo Messi" ),
					new Person("man", "brown", true, "actor", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Spain-Tahiti%2C_Confederations_Cup_2013_%2802%29_%28Villa_crop%29.jpg/245px-Spain-Tahiti%2C_Confederations_Cup_2013_%2802%29_%28Villa_crop%29.jpg", "David Villa" ),
					new Person("man", "brown", true, "tv", "http://huelvaya.es/wp-content/uploads/2011/11/risto-mejide-1.jpg", "Risto" ),
					new Person("woman", "blonde", true, "tv", "https://s3.eestatic.com/2018/10/24/corazon/famosos/Belen_Esteban_Menendez-Empresas_de_famosos-Tono_Sanchis-Famosos_347978627_102848612_1706x1706.jpg", "Belen esteban" ),
					new Person("woman", "brown", true, "tv", "https://cdn.20m.es/img/2007/04/02/578567.jpg", "Nuria roca" ),
					new Person("man", "brown", true, "sport", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkHi90gCkuniebwTzc6tTwnirGJfq3eV5u_UVENKt8XNUmm03v", "Pau Gasol" ),
					new Person("man", "brown", false, "sport", "http://cdn.24.co.za/files/Cms/General/d/8205/1cb28728b5ee4ecca34eb588691e00a1.png", "Federer" ),
					new Person("man", "brown", true, "politician", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Felipe_Gonz%C3%A1lez_1986_%28cropped%29.jpg/200px-Felipe_Gonz%C3%A1lez_1986_%28cropped%29.jpg", "Felipe gonzales" ),
					new Person("man", "brown", false, "politician", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/President_Barack_Obama.jpg/220px-President_Barack_Obama.jpg", "Obama" ),
					new Person("man", "brown", true, "actor", "http://marketing.sandiego.edu/alumnihonors19/wp-content/uploads/sites/26/2017/01/jim-parsons-400-500.jpg", "Jim parson" )
				);
				
		
		
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
		DataSource source = new DataSource(new ClassPathResource("test1_data.arff").getInputStream());
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
		MatchInformation match = new MatchInformation(true, true);
		Instance ins = match.asInstance(dataSet);

		int result = (int) tree.classifyInstance(ins);
		System.out.println(result);
		match = new MatchInformation(false, false);
		ins = match.asInstance(dataSet);

		result = (int) tree.classifyInstance(ins);
		
		System.out.println(result);

	}
	
	@Test
	public void WekaTestZeroR() throws Exception {
		DataSource source = new DataSource(new ClassPathResource("test1_data.arff").getInputStream());
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
		MatchInformation match = new MatchInformation(true, true);
		Instance ins = match.asInstance(dataSet);

		int result = (int) zero.classifyInstance(ins);
		System.out.println(result);
		match = new MatchInformation(false, false);
		ins = match.asInstance(dataSet);

		result = (int) zero.classifyInstance(ins);
		
		System.out.println(result);

	}

}
