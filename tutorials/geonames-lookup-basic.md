---
layout: page
title: Geonames Lookup
description: Setup An Eclipse Project with Geonames Library
group: tutorials-advanced
author: Markus Kerschkewicz
thumbnail:  http://tillnagel.github.com/unfolding/assets/images/tutorials/geonames_lookup_titel_thumb.jpg
finalimage: http://tillnagel.github.com/unfolding/assets/images/tutorials/geonames_lookup_titel.jpg
finalsrc: #someurlatgithub
---

{% include JB/setup %}

## Instruction: Things You Need To Prepare
This tutorial explains how to setup a eclipse project with the geonames library in combination with unfolding. The geonames library gives you the possibility to lookup the coordinates (latitude and longitude) of a certain geo-location just by the name.
First of all you need to prepare some things to follow this tutorial

*  Create an ecplise project with working unfolding library
*  download the geonames.org java library http://www.geonames.org/source-code/ 
*  geonames library also requires jdom to parse the xml web service result
*  create a free account on geonames.org to use the java library features http://www.geonames.org/login

## Step 1: Import Geonames Library


In the first step we need to import the geonames library and setup the WebService with our username.



###### in **geodata/GeoNamesBasicLookup.java**
	
	import org.geonames.*;                                                                                                                                  

    void setup() {
	  size(800,600);

	  WebService.setUserName("username"); // add your username here                                                                                  
    }

## Step 2: Make A Static Lookup
So now we are really starting to use the library. First thing we want to do is looking up all results for a basic string. In our example we will use "berlin" as the searchCriteria. For this lookup we already need a try/catch - statement do handle exceptions (more infos http://processing.org/reference/try.html).

###### in **geodata/GeoNamesBasicLookup.java**
	
	import org.geonames.*;    
	
	String searchName = "berlin"; // the string we want to lookup in geonames database
	ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria(); // the object we need for our search
	                                                                                                                           

	void setup() {
		size(800,600);

		WebService.setUserName("username"); // add your username here    
	     
		searchCriteria.setQ(searchName); // setup the main search term, in our case "berlin"
	
				try {
					ToponymSearchResult searchResult = WebService.search(searchCriteria); // a toponym search result as returned by the geonames webservice.

					for (Toponym toponym : searchResult.getToponyms()) {
						println(toponym.getName() + " " + toponym.getCountryName()
								+ " " + toponym.getLongitude() + " "
								+ toponym.getLatitude()); // prints the search results. We have access on certain get-Functions. In our Case the Name, Country, Longitude and Latitude
							}

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
					}                                                                         
    	}

The code now prints out all the search results we get by our searchCriteria. 

### Specify The Max Rows Of Search Results
The Geonames Java Library already brings some interesting functions with it. One important function you will need for sure is the specification of the maximum amount of rows you want to get as a search result.

	…
	searchCriteria.setQ(searchName); // setup the main search term, in our case "berlin"
	searchCriteria.setMaxRows(1); // setup the maximum amount of rows for your search results
	…

## Step 3: Make A Simple Switch For Our Search Criteria
Now lets get a little bit further. Of course we don't want to lookup just one static String. In the next step we are going to build a basic switch on keyPressed(); to switch between two different SearchCriteria.
So first we need a boolean to preserve the programm of constant looking up. Of course we could handle this with a threading method but first let's stay basic.

	…
	String searchName = "berlin"; // the string we want to lookup in geonames database
	ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria(); // the object we need for our search
	
	boolean searchEvent = true; // we need this for our searchEvent
	…
	
In the next step lets bring our SearchEvent to the draw() method.

###### in **geodata/GeoNamesBasicLookup.java**
	
	import org.geonames.*;    
	
	String searchName = "berlin"; // the string we want to lookup in geonames database
	ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria(); // the object we need for our search
	                                                                                                                           

	void setup() {
		size(800,600);

		WebService.setUserName("username"); // add your username here    
	                                                                       
    	}

	void draw(){
		
		searchCriteria.setQ(searchName); // setup the main search term, in our case "berlin"
			if(searchEvent == true){
				try {
					ToponymSearchResult searchResult = WebService.search(searchCriteria); // a toponym search result as returned by the geonames webservice.

					for (Toponym toponym : searchResult.getToponyms()) {
						println(toponym.getName() + " " + toponym.getCountryName()
								+ " " + toponym.getLongitude() + " "
								+ toponym.getLatitude()); // prints the search results. We have access on certain get-Functions. In our Case the Name, Country, Longitude and Latitude
							}

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
					}
					searchEvent = false;
					}
		
	}
	
	
The last thing we need to do is defining the keyPressed(); function for our lookup.

	public void keyPressed() {

		switch (key) {
		case 'a':
			searchName = "berlin";
			searchEvent = true;
			break;

		case 's':
			searchName = "monaco";
			searchEvent = true;
			break;

		}
	}
	
	![Here comes the alt text](https://github.com/tillnagel/unfolding/blob/gh-pages/assets/images/tutorials/basic_geonames_lookup_berlin.jpg "Monaco")
	*'s' pressed switches to monaco*
	
So now we are able to switch the searchCriteria by pressing the certain key. If you are running the code now it will print the certain searchresults for either "berlin" (key: a) or "monaco" (key: s).

## Advanced: Use ControlP5 to do a dynamic lookup via text-input

In the last step i just shortly want to show you how you can combine the geonames data-lookup in combination with ControlP5 and a text-input field. You get the advantage to dynamically navigate over the whole map by searching certain places.

first import the controlP5 library, create some variables we need and initialize our ControlP5 textfield.
	
	// CONTROLP5 Import
		import controlP5.ControlEvent;
		import controlP5.ControlP5;
		import controlP5.Textfield;
		
		…
	
	// CONTROLP5 Variables
		ControlP5 cp5;
		String textValue = "";
		Textfield myTextfield;
		controlP5.Label label;
		
		public void setup() {

				size(650, 440, GLConstants.GLGRAPHICS);

				// INIT UNFOLDING
				…
				
				// INIT GEONAMES
				…

				// INIT CONTROLP5
				cp5 = new ControlP5(this);

				println(cp5);
				myTextfield = cp5.addTextfield("Search Criteria").setPosition(20, 400).setSize(200, 20).setFocus(true);
				myTextfield.setAutoClear(true).keepFocus(true);
				label = myTextfield.captionLabel();
				label.setColor(color(0));

			}
			
now we just need to change the variable of our variable of the  'searchCriteria.setQ(searchName);' to 'searchCriteria.setQ(myTextfield.getStringValue());' and setup our "ControlP5-Events". 

	public void input(String theText) {
		// automatically receives results from controller input
		println("a textfield event for controller 'input' : " + theText);
	}

	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isAssignableFrom(Textfield.class)) {
			println("controlEvent: accessing a string from controller '" + theEvent.getName() + "': "
					+ theEvent.getStringValue());
			searchEvent = true;
		}
	}

hope you found this tutorial helpfull. 