package com.example.grace.ARchitect;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;



import com.example.grace.myapplication.FriendsActivity;
import com.example.grace.myapplication.R;
import com.wikitude.architect.ArchitectJavaScriptInterfaceListener;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.ArchitectView.SensorAccuracyChangeListener;
import com.wikitude.common.camera.CameraSettings;

import java.io.File;
import java.io.FileOutputStream;

public class SampleCamActivity extends AbstractArchitectCamActivity {

	private static final String TAG = "SampleCamActivity";

    private static final int WIKITUDE_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 3;

	// returns relevant html file for AR World
	@Override
	public String getARchitectWorldPath() {
		return "ARchitectFriendPoi/index.html";
	}

	/* returns the camera activity for AR
	* */
	@Override
	public int getContentViewId() {
		return R.layout.sample_cam;
	}

	/* returns architectView layout
	* */
	@Override
	public int getArchitectViewId() {
		return R.id.architectView;
	}

	//wikitude license key getter
	@Override
	public String getWikitudeSDKLicenseKey() {
		return "xzyLUb8xLJhjCcnvsMW3Sx2mfYJqxewOB3xJ+X4cWd14ZmvOou61Ih95TERKNwHfqVTJ4zWRg+zMGWjQJg1QYeMb7MvcBijzxqhd/OZl18hGjN8bOrOiUbxi64CMYrdpM1soimzMLhV+GqzdRt+wZWenatwTKfYoDJ9d+sxsO+NTYWx0ZWRfX9d4qgY+oB6bOZK0Z0DX1AFp1kZWhQeSBTZjrSHbomM7lmi4gaWjDlAaA/7GQLU2pCQQqKTiEEvV0SFosUYLPxTcad6Ot9SEgLGHJrjerAjvAfQvP0LkvQVguBXKZl4opzTyf8LeTmrZDt1R3WtffqsCjaEkwVH06pw4O+y5JwCXclLz2FH2eDddt7tKqAp/TxAOkA398+OhfR8yNkutXn2Yr0D/vMabt27OZTrRFfhWPbi3JeobXqg5ih+CvZf/hxLcYeKJ7i4K9nRYFUwopAbKX/Gm+mvtmPEyOOp8v/SVlErCIWhVwLoXujvUcmi96gGuDQS8GY1zf8pMYY8LmIruldBc4jR7qiIpQWBHE77uSstaYOzUmsr7II+oGQCdjiRAUtsjqhInFal/jtfwgG+OFdIVPlrfH/LEpH/WvJMN5q3JXtUw0C7N64ql3wozP3bTy96XmYuiEH/PXFETSzEdi7VnIiV9/LfOm2e/KzJree9RIG6KLEw=";
	}

	//used to confirm that the ARWorld (native JavaScript) has loaded
	@Override
	public ArchitectView.ArchitectWorldLoadedListener getWorldLoadedListener() {
		return new ArchitectView.ArchitectWorldLoadedListener() {
			@Override
			public void worldWasLoaded(String url) {
				Log.i(TAG, "worldWasLoaded: url: " + url);
			}

			@Override
			public void worldLoadFailed(int errorCode, String description, String failingUrl) {
				Log.e(TAG, "worldLoadFailed: url: " + failingUrl + " " + description);
			}
		};
	}

	/* returns the distance at which the POI are cut off in the AR view
	* */
	@Override
	public float getInitialCullingDistanceMeters() {
		// you need to adjust this in case your POIs are more than 50km away from user here while loading or in JS code (compare 'AR.context.scene.cullingDistance')
		return ArchitectViewHolderInterface.CULLING_DISTANCE_DEFAULT_METERS;
	}





}
