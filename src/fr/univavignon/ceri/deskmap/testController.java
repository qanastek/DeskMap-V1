package fr.univavignon.ceri.deskmap;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitMenuButton;



public class testController implements Initializable {
	@FXML TextField nStreet;
	@FXML TextField nomVille;
	@FXML ComboBox<String> cbb;
	@FXML ComboBox<String> cbbTmp;
	@FXML SplitMenuButton nomStreet;
	@FXML Button btn;
	@FXML AnchorPane anchorPane1,anchorPane2;
	ObservableList<String> originalItems;
	ObservableList<String> filteredList;
	/**
	 * filter for combobox
	 */
	String filter = "";
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	anchorPane1.setMinWidth(345);
		btn.setStyle("-fx-background-color: #B2BABB;");
		cbb.getItems().setAll("Apple", "Orange", "Pear","Abc"
				,"Abd","Abe","Abf pp ff","Abg","Abh","Abi","Bbc",
				"Bbd","Bbe","Bbf","Bbg","Bbh","Cb1","Cb2","Cb3"
				,"Cb4","Cb5","Cb6","D1","D2","D3");

		this.cbb.setEditable(true);
		this.originalItems= FXCollections.observableArrayList(this.cbb.getItems());
		System.out.println(originalItems);
//		new ComboBoxAutoComplete<String>(cbb);

	}


	@FXML
	private void TextType(KeyEvent event){
		if(!Character.isDigit(event.getCharacter().charAt(0))){
			event.consume();
		}
	}

	@FXML
	private void checkVille(KeyEvent event){
		if (nomVille.getText().equals("")){
			btn.setStyle("-fx-background-color: #d0d0d0;");
		}
		else {
			this.btn.setStyle("-fx-background: #f4f4f4;");
		}
	}
	/**
	 * @param event e
	 */
	@FXML
	private void okClick(MouseEvent event)
    {
		if(!this.nomVille.getText().equals("")){
			this.btn.setStyle("-fx-color: #82E0AA;");
		}
    }
	/**
	 * @param event e
	 */
	@FXML
	private void comboboxToStreet(KeyEvent event){

		this.filteredList = FXCollections.observableArrayList();

		this.filter = this.cbb.getEditor().getText();

		if (event.getCode().equals(KeyCode.BACK_SPACE) && filter.length() > 0) {
			filter = filter.substring(0, filter.length() - 1);
			this.cbb.getItems().setAll(originalItems);
		}
		if (event.getCode().equals(KeyCode.ESCAPE)) {
			filter = "";
		}
		if (event.getCode().equals(KeyCode.SPACE)) {
			filter += ' ';
		}
		System.out.println("filter is :" +filter);
		if (filter.length() == 0) {
			this.filteredList = this.originalItems;
		}
		if(filter.length() >= 0){
			Stream<String> itens = this.cbb.getItems().stream();
			String txtUsr = filter.toString().toLowerCase();
			itens.filter(el -> el.toString().toLowerCase().contains(txtUsr)).forEach(filteredList::add);

			cbb.show();
		}

		cbb.getItems().setAll(filteredList);
	}

	@FXML
	private void Searching(MouseEvent event)
    {
		System.out.println("Searching clicked");

    }

	@FXML
	private void SetCity(MouseEvent event)
    {
		System.out.println(nStreet.getText());

    }



}