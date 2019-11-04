# DeskMap

Desktop version of map browers such as Google Maps

This software was developped as an assignment during the 2019/20 university year at the CERI, Avignon University (France), by the following students:
* Mohamed BEN YAMNA
* Quentin Capdepon
* Yanis Labrak
* Zihao Zheng

It can be used to browse maps extracted from OpenStreetMap.


## Organization
The source code is organized as follows:

* At the root of the deskmap package:
     * Models
      * Contain all the class for the future objects
     * Vues
      * Contain all the FXML files which each corresponding to a vue	
     * Controllers
      * Contain all the methods which will handle the events for a specific view
     * Ressources
      * Contain all the ressources like the pictures and CSS stylesheets
     * Services
      * Contain all others classes like the one where the queries was build or another one which draw on the canvas
     * Config
      * Contain all configuration classes like the one which contain all the color variable or another one which store all the limits, the thinkness of the roads, etc...
     * The main class of the project
      * Launcher.java


## Installation
Here is the procedure to install this software :
1. Do this
2. Do that
3. etc.


## Use
1. In order to use the software, you must have an internet connection
2. You need to have at least the version 1.8 of JavaSE
3. Run the software
4. Enter the city in which you want to make the path calculation
5. Enter the start and the end address of your route
6. Press start

The project wiki (put a hyperlink) contains detailed instructions regarding how to use the software.


## Dependencies

The project relies on the following libraries:
* json-simple : This library was used to parse the JSON data received from the OSM API server (available in JavaEE)

And the following services:
* Open Street Map: This service was used for fetching the geographical data needed for this project

## Development environement
* JavaFX
    * Install JavaFX: [Tutorial](https://o7planning.org/fr/10619/installation-de-e-fx-clipse-sur-eclipse)
    * Install SceneBuilder: [Tutorial](https://o7planning.org/fr/10621/installez-javafx-scene-builder-dans-eclipse)

## References
During development, we use the following bibliographic resources:
* [De  la  géo  et  des  maths](https://blogs.msdn.microsoft.com/ogdifrance/2011/07/13/de-la-go-et-des-maths/?fbclid=IwAR3efsf9pp87SdKcxNy71T79GPfu7wcxwE-2JhpUWKYOhxW91f38fa_CynY): Its explains how to convert geographical coordinates to flat coordinates.
* [Les requêtes pour Overpass Turbo](http://www.crige-paca.org/index.php?eID=tx_crigedocuments&hash=a4103035&fid=3031): Its explains how OpenStreetMap API works and how to build queries easily.
* [Working  with  Canvas](https://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm): This Oracle tutorial explain how to use the canvas library of JavaFx.