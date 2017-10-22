package com.example.grace.ARchitect;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.media.AudioManager;
import android.opengl.GLES20;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.grace.servercommunication.JSONResponse;
import com.example.grace.servercommunication.ServerConnection;
import com.example.grace.util.MyLocation;
import com.google.android.gms.tasks.Task;
import com.wikitude.architect.ArchitectJavaScriptInterfaceListener;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.ArchitectView.SensorAccuracyChangeListener;
import com.wikitude.architect.services.camera.CameraLifecycleListener;
import com.wikitude.common.camera.CameraSettings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Abstract activity which handles live-cycle events.
 * Feel free to extend from this activity when setting up your own AR-Activity
 *
 */
public abstract class AbstractArchitectCamActivity extends Activity implements ArchitectViewHolderInterface{

	final private int REQUEST_PERMISSIONS = 1;

	/**
	 * holds the Wikitude SDK AR-View, this is where camera, markers, compass, 3D models etc. are rendered
	 */
	protected ArchitectView architectView;

	/**
	 * worldLoadedListener receives calls when the AR world is finished loading or when it failed to laod.
	 */
	protected ArchitectView.ArchitectWorldLoadedListener worldLoadedListener;

	protected ArrayList<String> permissions = new ArrayList<String>();

	protected String[] permissionsToCheck = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

	protected String friendName;

	public FriendLocationTask friendLocationTask;

	private static final String TAG = "AbstractCamActivity";

	private FriendLocationTask mFriendLocationTask = null;

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		/* pressing volume up/down should cause music volume changes */
		this.setVolumeControlStream( AudioManager.STREAM_MUSIC );

		/* set samples content view */
		this.setContentView( this.getContentViewId() );

		/*
		 *	this enables remote debugging of a WebView on Android 4.4+ when debugging = true in AndroidManifest.xml
		 *	If you get a compile time error here, ensure to have SDK 19+ used in your ADT/Eclipse.
		 *	You may even delete this block in case you don't need remote debugging or don't have an Android 4.4+ device in place.
		 *	Details: https://developers.google.com/chrome-developer-tools/docs/remote-debugging
		 */
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		    if ( 0 != ( getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE ) ) {
		        WebView.setWebContentsDebuggingEnabled(true);
		    }
		}
		for (String item : permissionsToCheck) {
			checkPermission(item);
		}
		if (permissions.size() > 0) {
			ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSIONS);
		}

		/* set AR-view for life-cycle notifications etc. */
		this.architectView = (ArchitectView)this.findViewById( this.getArchitectViewId()  );

		// finds out which user has been 'beamed' by extracting their username from the intent
		Bundle extras = getIntent().getExtras();
		friendName = extras.getString("user");

		/* set up the architectView passing in license key etc.
		* */
		final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
		config.setLicenseKey(this.getWikitudeSDKLicenseKey());
		try {
			/* first mandatory life-cycle notification */
			this.architectView.onCreate( config );
		} catch (RuntimeException rex) {
			this.architectView = null;
			Toast.makeText(getApplicationContext(), "can't create Architect View", Toast.LENGTH_SHORT).show();
			Log.e(this.getClass().getName(), "Exception in ArchitectView.onCreate()", rex);
		}

		// set world loaded listener
		this.worldLoadedListener = this.getWorldLoadedListener();

		// registers valid world loaded listener in architectView
		if (this.worldLoadedListener != null && this.architectView != null) {
			this.architectView.registerWorldLoadedListener(worldLoadedListener);
		}

		// create timer and a new timer task that gets the location of friend
		// from server and calls setLocation() with the correct longitude and latitude
		createTimer();

		}

	protected void createTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				//get friends location from server
				getFriendLocation();
			}
		}, 0, 5000);

	}

	/* sends request to server for friends location, response handled in ASyncTask FriendLocationTask
	* */
	private boolean getFriendLocation() {
		Log.e(TAG, "getFriendLocation");
		ServerConnection serverConnection = new ServerConnection();

		ArrayList<String> keyTags = new ArrayList<>();
		keyTags.add("user");
		ArrayList<String> keys = new ArrayList<>();
		keys.add(friendName);
		Log.e(TAG, friendName);
		//query server for friends data

		serverConnection.makeServerRequest("FriendLocation", keyTags, keys, 1, this, false);
		mFriendLocationTask = new FriendLocationTask();
		mFriendLocationTask.execute((Void) null);
		return true;
	}

	/* links the native ARWorld with the java in architectView.load
	* */
	@Override
	protected void onPostCreate( final Bundle savedInstanceState ) {
		super.onPostCreate( savedInstanceState );

		if ( this.architectView != null ) {

			// call mandatory live-cycle method of architectView
			this.architectView.onPostCreate();

			try {
				// load content via url in architectView, ensure '<script src="architect://architect.js"></script>' is part of this HTML file, have a look at wikitude.com's developer section for API references
				this.architectView.load( this.getARchitectWorldPath() );

				if (this.getInitialCullingDistanceMeters() != ArchitectViewHolderInterface.CULLING_DISTANCE_DEFAULT_METERS) {
					// set the culling distance - meaning: the maximum distance to render geo-content
					this.architectView.setCullingDistance( this.getInitialCullingDistanceMeters() );
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		// call mandatory live-cycle method of architectView
		if (this.architectView != null) {
			this.architectView.onResume();

		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		// call mandatory live-cycle method of architectView
		if ( this.architectView != null ) {
			this.architectView.onPause();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// call mandatory live-cycle method of architectView
		if ( this.architectView != null ) {
			this.architectView.clearCache();
			this.architectView.onDestroy();
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if ( this.architectView != null ) {
			this.architectView.onLowMemory();
		}
	}

	/**
	 * path to the architect-file (AR-Experience HTML) to launch
	 */
	@Override
	public abstract String getARchitectWorldPath();

	/**
	 * @return layout id of your layout.xml that holds an ARchitect View, e.g. R.layout.camview
	 */
	@Override
	public abstract int getContentViewId();

	/**
	 * @return Wikitude SDK license key, checkout www.wikitude.com for details
	 */
	@Override
	public abstract String getWikitudeSDKLicenseKey();

	/**
	 * @return layout-id of architectView, e.g. R.id.architectView
	 */
	@Override
	public abstract int getArchitectViewId();

	/**
	 * @return Implementation of ArchitectWorldLoadedListener. That way you know when a AR world is finished loading or when it failed to load.
	 */
	@Override
	public abstract ArchitectView.ArchitectWorldLoadedListener getWorldLoadedListener();

	public boolean checkPermission(String permission) {
		if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
			permissions.add(permission);
			return false;
		} else {
			return true;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e(TAG, "onKeyDown");
		Log.e(TAG, Integer.toString(KeyEvent.KEYCODE_BACK));
		Log.e(TAG, "keyCode = " + Integer.toString(keyCode));
		if (keyCode == KeyEvent.KEYCODE_BACK && architectView != null) {
			finish();
			return true;
		}
		return false;
	}

	public class FriendLocationTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			Log.e(TAG, "doInBackground");
			try {
				while(JSONResponse.response == null) {
					Thread.sleep(20);
					Log.e("Waiting", "waiting");
				}

				return true;



			}catch(Exception e){e.printStackTrace();}

			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			Log.e(TAG, "onPostExecute");
			mFriendLocationTask = null;
			try {
				Log.e(TAG, "setLocation about to be called");
				architectView.setLocation(MyLocation.myLatitude, MyLocation.myLongitude, 100);
				//	System.out.print("=============World.loadPoisFromJsonData(" + JSONResponse.JSON + ");");
				String latitude = JSONResponse.response.getString("latitude");
				String longitude = JSONResponse.response.getString("longitude");
				//System.out.println("=================== LATITUDE AS STRING: " + latitude);
				architectView.callJavascript("World.loadPoisFromJsonData(" + latitude + ", " + longitude + ");");
			} catch (Exception e) {
				Log.e(TAG, "Exception caught.");
				e.printStackTrace();
			}

			friendLocationTask = null;
			JSONResponse.response = null;
		}

		@Override
		protected void onCancelled() {
			Log.e(TAG, "onCancelled");
			friendLocationTask = null;

		}
	}

}
