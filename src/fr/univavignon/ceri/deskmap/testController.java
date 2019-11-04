package fr.univavignon.ceri.deskmap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.controlsfx.control.textfield.TextFields;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * @author zhengzihao
 *
 */
public class testController implements Initializable {

	@FXML TextField nameCity;

	@FXML Button setCity;

	@FXML TextField nameStreetFrom;

	@FXML ComboBox<String> cbbFrom;

	@FXML TextField nameStreetTo;

	@FXML ComboBox<String> cbbTo;

	@FXML AnchorPane anchorPane1,anchorPane2;

	@FXML SplitPane splitPane;

	private ObservableList<String> originalItemsTo;

	private ObservableList<String> originalItemsFrom;

	private ObservableList<String> originalItemsCity;

	final private String URL_OSM = new String("https://lz4.overpass-api.de/api/interpreter?data=");

	/**
	 * filter for combobox
	 */
	private String filter = "";


    /* (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    /*
     * Initial
     * */
	public void initialize(URL location, ResourceBundle resources) {

    	this.anchorPane1.setMinWidth(345);
    	//count visible row
    	this.cbbTo.setVisibleRowCount(6);
    	this.cbbFrom.setVisibleRowCount(6);

		// Initial disable
		this.setCity.setDisable(true);
		this.nameStreetFrom.setDisable(true);
		this.nameStreetTo.setDisable(true);
		this.cbbFrom.setDisable(true);
		this.cbbTo.setDisable(true);

		try {
			autocompleteCity();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


    /**
     * @throws FileNotFoundException
     */
    private void autocompleteCity() throws FileNotFoundException {
    	BufferedReader br = null;

    	ObservableList<String> filteredList = FXCollections.observableArrayList();

		this.filter = this.nameCity.getText();

		File f = new File("AllCity.csv");

		try {
			br = new BufferedReader(new FileReader(f));
		} catch (Exception e) {
			System.err.println(e);
		}

		String line = "";
	    String everyLine = "";
	        List<String> allString = new ArrayList<>();

        try {
			while ((line = br.readLine()) != null)
			{
			    everyLine = line;
			    allString.add(everyLine);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
        this.originalItemsCity= FXCollections.observableArrayList(allString);
        TextFields.bindAutoCompletion(this.nameCity, this.originalItemsCity);
	}
    /**
	 * Send the HTTP GET request to the Overpass API
     * @param city name of city
	 * @throws Exception e
	 */
	private void sendGet(String city) throws Exception {
		try {
			int bytesum = 0;
	        int byteread = 0;

			String spec = this.URL_OSM + "[out:csv(\"name\";false)];area[name=\"" + city + "\"];way(area)[highway][name];out;";
			URL httpUrl = new URL(spec);
			String saveFile = city + ".csv";
			File file=new File(saveFile);

			if (!file.exists()) {


				URLConnection conn = httpUrl.openConnection();
				InputStream inStream = conn.getInputStream();
				FileOutputStream fs = new FileOutputStream(saveFile);

				byte[] buffer = new byte[1204];
				System.out.println("Loading..");
	            while ((byteread = inStream.read(buffer)) != -1) {
	                bytesum += byteread;
	                fs.write(buffer, 0, byteread);

	            }
	            System.out.println("Finished !");
			}
			loadIntoCbb(file);
        } catch (Exception e) {
        	System.err.println(e);
		}
	}

	/**
	 * @param f file
	 * @throws FileNotFoundException case not found
	 */
	private void loadIntoCbb(File f) throws FileNotFoundException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (Exception e) {
			System.err.println(e);
		}
		String line = "";
	    String everyLine = "";
	    try {
	        List<String> allString = new ArrayList<>();

	        while ((line = br.readLine()) != null)
	        {
	            everyLine = line;
	            allString.add(everyLine);
	        }
	        List<String> distinctList = allString.stream().distinct().collect(Collectors.toList());
            this.cbbTo.getItems().setAll(distinctList);
            this.cbbFrom.getItems().setAll(distinctList);
            this.originalItemsTo= FXCollections.observableArrayList(this.cbbTo.getItems());
    		this.originalItemsFrom= FXCollections.observableArrayList(this.cbbFrom.getItems());


	    } catch (Exception e) {

	    }
	}

	/**
	 * @param event e
	 * number of street type INTEGER
	 */
	@FXML
	private void TextType(KeyEvent event){
		if(!Character.isDigit(event.getCharacter().charAt(0))){
			event.consume();
		}
	}

	/**
	 * @param event e
	 */
	@FXML
	private void resetCity(KeyEvent event){
		if (this.nameCity.getText().isEmpty()){
			this.setCity.setDisable(true);
			this.nameStreetFrom.setDisable(true);
			this.nameStreetTo.setDisable(true);
			this.cbbFrom.setDisable(true);
			this.cbbTo.setDisable(true);
		}
		else {
			this.setCity.setDisable(false);
			this.nameStreetFrom.setDisable(false);
			this.nameStreetTo.setDisable(false);
			this.cbbFrom.setDisable(false);
			this.cbbTo.setDisable(false);
		}

	}

	/**
	 * @param event e
	 * @throws Exception e
	 */
	@FXML
	private void okClick(MouseEvent event) throws Exception
    {
		sendGet(nameCity.getText());
    }

	/**
	 * @param event e
	 */
	@FXML
	private void comboboxStreetFrom(KeyEvent event){

		comboboxStreet(event,this.cbbFrom,this.originalItemsFrom);
	}

	/**
	 * @param event e
	 */
	@FXML
	private void comboboxStreetTo(KeyEvent event){
		comboboxStreet(event,this.cbbTo,this.originalItemsTo);

	}

	/**
	 * @param event e
	 * @param cbb content
	 * @param originalItems counter-part
	 */
	private void comboboxStreet(KeyEvent event,ComboBox<String> cbb,ObservableList<String> originalItems){
		ObservableList<String> filteredList = FXCollections.observableArrayList();

		this.filter = cbb.getEditor().getText();

		// origin content
		if (event.getCode().equals(KeyCode.BACK_SPACE)) {
			cbb.getItems().setAll(originalItems);
		}

		if(this.filter.length() >= 0){
			Stream<String> itens = cbb.getItems().stream();
			String txtUsr = this.filter.toString().toLowerCase();
			itens.filter(el -> el.toString().toLowerCase().contains(txtUsr)).forEach(filteredList::add);
			if (event.getCode().equals(KeyCode.ENTER)) {
				cbb.getSelectionModel().selectFirst();

				cbb.hide();
			}else{
				cbb.show();
			}
		}

		cbb.getItems().setAll(filteredList);
	}

	/**
	 * @param event e
	 * reduce windows
	 */
	@FXML
	private void reduce(MouseEvent event)
    {
		this.anchorPane1.setMinWidth(0);
		this.splitPane.setDividerPositions(0.0);
		System.out.println("Searching clicked");
    }

	/**
	 * @param event e
	 * upstep windows
	 */
	@FXML
	private void upstep(MouseEvent event)
    {
		this.anchorPane1.setMinWidth(345);
    }

	/**
	 * @param event e
	 */
	@FXML
	private void Search(MouseEvent event)
    {
		System.out.println("Search clicked");

    }

	/**
	 * @param event e
	 */
	@FXML
	private void Stop(MouseEvent event)
    {
		System.out.println("Stop to search clicked");

    }

	private void drawLine() throws Exception{
		try {
			String spec = this.URL_OSM + "[bbox];node[amenity=post_box];out;&bbox=7.0,50.6,7.3,50.8";
		} catch (Exception e) {

		}
	}



}