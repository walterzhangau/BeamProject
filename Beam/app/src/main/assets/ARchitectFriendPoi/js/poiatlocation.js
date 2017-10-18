// implementation of AR-Experience (aka "World")
var World = {


	// true once data was fetched
	initiallyLoadedData: false,

	// POI-Marker asset
	markerDrawable_idle: null,

	// called to inject new POI data
	loadPoisFromJsonData: function loadPoisFromJsonDataFn(lat, lon) {

		var latitude = parseFloat(lat);
		var longitude = parseFloat(lon);
        AR.logger.debug("latitude: " + latitude);
        AR.logger.debug("longitude: " + longitude);
		/*
			Loads an AR.ImageResource when the World variable was defined. It will be reused for each marker that we will create afterwards.
		*/
		World.markerDrawable_idle = new AR.ImageResource("assets/cloud.png");

		/*
			For creating the marker a new object AR.GeoObject will be created at the specified geolocation. An AR.GeoObject connects one or more AR.GeoLocations with multiple AR.Drawables. The AR.Drawables can be defined for multiple targets. A target can be the camera, the radar or a direction indicator. Both the radar and direction indicators will be covered in more detail in later examples.
		*/
		var markerLocation = new AR.GeoLocation(latitude, longitude, 100);
		var markerImageDrawable_idle = new AR.ImageDrawable(World.markerDrawable_idle, 2.5, {
			zOrder: 0,
			opacity: 1.0
		});

		// create GeoObject
		var markerObject = new AR.GeoObject(markerLocation, {
			drawables: {
				cam: [markerImageDrawable_idle]
			}
		});

		// Updates status message as a user feedback that everything was loaded properly.
		World.updateStatusMessage('1 place loaded');
	},

	// updates status message shown in small "i"-button aligned bottom center
	updateStatusMessage: function updateStatusMessageFn(message, isWarning) {

		var themeToUse = isWarning ? "e" : "c";
		var iconToUse = isWarning ? "alert" : "info";

		$("#status-message").html(message);
		$("#popupInfoButton").buttonMarkup({
			theme: themeToUse
		});
		$("#popupInfoButton").buttonMarkup({
			icon: iconToUse
		});
	},

	// location updates, fired every time you call architectView.setLocation() in native environment
	locationChanged: function locationChangedFn(lat, lon, alt, acc) {

		/*
			The custom function World.onLocationChanged checks with the flag World.initiallyLoadedData if the function was already called. With the first call of World.onLocationChanged an object that contains geo information will be created which will be later used to create a marker using the World.loadPoisFromJsonData function.
		*/
		if (!World.initiallyLoadedData) {
			// creates a poi object with a random location near the user's location
			World.initiallyLoadedData = true;
		} else {

		}

	},
};

/* 
	Set a custom function where location changes are forwarded to. There is also a possibility to set AR.context.onLocationChanged to null. In this case the function will not be called anymore and no further location updates will be received. 
*/
AR.context.onLocationChanged = World.locationChanged;
