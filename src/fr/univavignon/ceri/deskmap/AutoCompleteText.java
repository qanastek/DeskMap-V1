package fr.univavignon.ceri.deskmap;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

/**
 * @author zihao zheng
 *
 */
public class AutoCompleteText extends TextField{

	private ObservableList<String> suggestions;

	private ContextMenu suggestMenu = new ContextMenu();

	public AutoCompleteText(ObservableList<String> suggestions){

		super();

		this.suggestions = suggestions;
	}

	/**
	 *  changed
	 */
	public void listener(TextField t) {
		if(t.getText().length() == 0){
			this.suggestMenu.hide();
		}
		else{
			ObservableList<String> research = FXCollections.observableArrayList();
			research.addAll(this.suggestions);

			if (this.suggestions.size() > 0) {
				showResearch(research,t);
				if (!AutoCompleteText.this.suggestMenu.isShowing()) {
					AutoCompleteText.this.suggestMenu.show(t, Side.BOTTOM, 0, 0);
				}
			}
			else {
				AutoCompleteText.this.suggestMenu.hide();
			}
		}

	}

	public ObservableList<String> getSuggestions() { return this.suggestions; }

	private void showResearch(ObservableList<String> searchResult,TextField text){
		ObservableList<String> filteredList = FXCollections.observableArrayList();
		List<CustomMenuItem> menuItems = new LinkedList<>();

		Stream<String> itens = searchResult.stream();
		String txtUsr = text.getText().toLowerCase();
		itens.filter(el -> el.toString().toLowerCase().contains(txtUsr)).forEach(filteredList::add);

		int count = Math.min(filteredList.size(), 10);
		for(int i = 0; i < count; i++){
			final String result = filteredList.get(i);
			Label label = new Label(result);
			CustomMenuItem item = new CustomMenuItem(label,true);
			item.setOnAction(new EventHandler<ActionEvent>()
		      {
		        @Override
		        public void handle(ActionEvent actionEvent) {
		          text.setText(result);
		          suggestMenu.hide();
		        }
		      });
			menuItems.add(item);
		}

		suggestMenu.getItems().clear();
		suggestMenu.getItems().addAll(menuItems);
	}
}
