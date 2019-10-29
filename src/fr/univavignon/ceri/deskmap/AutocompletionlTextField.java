package fr.univavignon.ceri.deskmap;

import javafx.collections.ObservableList;

public class AutocompletionlTextField{
	private ObservableList<String> contextMenu;
	private String nameCity;

	/**
	 * @param contextMenu
	 * @param nameCity
	 */
	public AutocompletionlTextField(ObservableList<String> contextMenu, String nameCity) {
		this.contextMenu = contextMenu;
		this.nameCity = nameCity;
	}
	
	
}
