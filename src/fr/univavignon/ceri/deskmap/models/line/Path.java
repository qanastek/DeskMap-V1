/**
 * 
 */
package fr.univavignon.ceri.deskmap.models.line;

import fr.univavignon.ceri.deskmap.config.Color;
import fr.univavignon.ceri.deskmap.config.Settings;

/**
 * @author Yanis Labrak
 *
 */
public class Path extends Road {
	
	/**
	 * Constructor
	 */
	public Path() {
		super(0L);
		this.setColor(Color.PATH);
		this.setThickness(Settings.PATH);
	}

}
