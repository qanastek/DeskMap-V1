package fr.univavignon.ceri.deskmap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
 * @author zihao zheng
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

	private String URL_OSM = new String("https://lz4.overpass-api.de/api/interpreter?data=");

	/**
	 * filter for combobox
	 */
	private String filter = "";


    @Override
    /*
     * Initial
     * */
	public void initialize(URL location, ResourceBundle resources) {

    	this.anchorPane1.setMinWidth(345);
    	//content of combobox test
		this.cbbTo.getItems().setAll("Apple", "Orange", "Pear","Abc"
				,"Abd","Abe","Abf pp ff","Abg","Abh","Abi","Bbc",
				"Bbd","Bbe","Bbf","Bbg","Bbh","Cb1","Cb2","Cb3"
				,"Cb4","Cb5","Cb6","D1","D2","D3");
		this.cbbFrom.getItems().setAll("Apple", "Orange", "Pear","Banan");

		// counter-part of combobox
		this.originalItemsTo= FXCollections.observableArrayList(this.cbbTo.getItems());
		this.originalItemsFrom= FXCollections.observableArrayList(this.cbbFrom.getItems());
		// Initial disable
		this.setCity.setDisable(true);
		this.nameStreetFrom.setDisable(true);
		this.nameStreetTo.setDisable(true);
		this.cbbFrom.setDisable(true);
		this.cbbTo.setDisable(true);
	}

    /**
	 * Send the HTTP GET request to the Overpass API
     * @param city name of city
	 * @throws Exception e
	 */
	private void sendGet(String city) throws Exception {
		try {
			String spec = this.URL_OSM + "[out:json][timeout:50];area(3600102478)->.SA;(node['highway'='primary'](area.SA);node['highway'='secondary'](area.SA);node['highway'='tertiary'](area.SA);node['highway'='residential'](area.SA);node['highway'='unclassified'](area.SA); way['highway'](area.SA););out;";

			URL httpUrl = new URL(spec);

			HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
	        httpURLConnection.setReadTimeout(5000);
	        httpURLConnection.setRequestMethod("GET");

	        httpURLConnection.setRequestProperty("test-header","get-header-value");

	        // Get content
	        InputStream inputStream = httpURLConnection.getInputStream();
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	        final StringBuffer stringBuffer = new StringBuffer();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	            stringBuffer.append(line);
	        }

	        inputStream.close();
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
		System.out.println(this.nameCity.getText());
	}

	/**
	 * @param event e
	 */
	@FXML
	private void okClick(MouseEvent event)
    {

		System.out.println("Set city clicked");
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



}