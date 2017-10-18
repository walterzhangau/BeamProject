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
	/**
	 * last time the calibration toast was shown, this avoids too many toast shown when compass needs calibration
	 */
	private long lastCalibrationToastShownTimeMillis = System.currentTimeMillis();

    protected Bitmap screenCapture = null;

    private static final int WIKITUDE_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 3;

	@Override
	public String getARchitectWorldPath() {
		//return "08_Point$Of$Interest_1_Poi$At$Location/index.html";
		//return "ARchitectWorld/index.html";
		return "ARchitectFriendPoi/index.html";
	}

	@Override
	public ArchitectJavaScriptInterfaceListener getArchitectJavaScriptInterfaceListener() {
		return null;
	}

	@Override
	public int getContentViewId() {
		return R.layout.sample_cam;
	}

	@Override
	public int getArchitectViewId() {
		return R.id.architectView;
	}
	
	@Override
	public String getWikitudeSDKLicenseKey() {
		return "xzyLUb8xLJhjCcnvsMW3Sx2mfYJqxewOB3xJ+X4cWd14ZmvOou61Ih95TERKNwHfqVTJ4zWRg+zMGWjQJg1QYeMb7MvcBijzxqhd/OZl18hGjN8bOrOiUbxi64CMYrdpM1soimzMLhV+GqzdRt+wZWenatwTKfYoDJ9d+sxsO+NTYWx0ZWRfX9d4qgY+oB6bOZK0Z0DX1AFp1kZWhQeSBTZjrSHbomM7lmi4gaWjDlAaA/7GQLU2pCQQqKTiEEvV0SFosUYLPxTcad6Ot9SEgLGHJrjerAjvAfQvP0LkvQVguBXKZl4opzTyf8LeTmrZDt1R3WtffqsCjaEkwVH06pw4O+y5JwCXclLz2FH2eDddt7tKqAp/TxAOkA398+OhfR8yNkutXn2Yr0D/vMabt27OZTrRFfhWPbi3JeobXqg5ih+CvZf/hxLcYeKJ7i4K9nRYFUwopAbKX/Gm+mvtmPEyOOp8v/SVlErCIWhVwLoXujvUcmi96gGuDQS8GY1zf8pMYY8LmIruldBc4jR7qiIpQWBHE77uSstaYOzUmsr7II+oGQCdjiRAUtsjqhInFal/jtfwgG+OFdIVPlrfH/LEpH/WvJMN5q3JXtUw0C7N64ql3wozP3bTy96XmYuiEH/PXFETSzEdi7VnIiV9/LfOm2e/KzJree9RIG6KLEw=";
	}


	//QUESTIONABLE
	@Override
	public SensorAccuracyChangeListener getSensorAccuracyListener() {
		return new SensorAccuracyChangeListener() {
			@Override
			public void onCompassAccuracyChanged( int accuracy ) {
				/* UNRELIABLE = 0, LOW = 1, MEDIUM = 2, HIGH = 3 */
				if ( accuracy < SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM && SampleCamActivity.this != null && !SampleCamActivity.this.isFinishing() && System.currentTimeMillis() - SampleCamActivity.this.lastCalibrationToastShownTimeMillis > 5 * 1000) {
					Toast.makeText( SampleCamActivity.this, R.string.compass_accuracy_low, Toast.LENGTH_LONG ).show();
					SampleCamActivity.this.lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
				}
			}
		};
	}





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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WIKITUDE_PERMISSIONS_REQUEST_EXTERNAL_STORAGE: {
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    this.saveScreenCaptureToExternalStorage(SampleCamActivity.this.screenCapture);
                } else {
                    Toast.makeText(this, "Please allow access to external storage, otherwise the screen capture can not be saved.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

	@Override
	public ILocationProvider getLocationProvider(final LocationListener locationListener) {
		return new LocationProvider(this, locationListener);
	}
	
	@Override
	public float getInitialCullingDistanceMeters() {
		// you need to adjust this in case your POIs are more than 50km away from user here while loading or in JS code (compare 'AR.context.scene.cullingDistance')
		return ArchitectViewHolderInterface.CULLING_DISTANCE_DEFAULT_METERS;
	}



	@Override
	protected boolean hasIR() {
		return false;
	}

	@Override
	protected boolean hasInstant() {
		return false;
	}

	@Override
	public String getActivityTitle() {
		return null;
	}

	@Override
	protected CameraSettings.CameraPosition getCameraPosition() {
		return CameraSettings.CameraPosition.DEFAULT;
	}

	@Override
	protected boolean hasGeo() {
		return true;
	}

	protected void saveScreenCaptureToExternalStorage(Bitmap screenCapture) {
        if ( screenCapture != null ) {
            // store screenCapture into external cache directory
            final File screenCaptureFile = new File(Environment.getExternalStorageDirectory().toString(), "screenCapture_" + System.currentTimeMillis() + ".jpg");

            // 1. Save bitmap to file & compress to jpeg. You may use PNG too
            try {

                final FileOutputStream out = new FileOutputStream(screenCaptureFile);
                screenCapture.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

                // 2. create send intent
                final Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpg");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(screenCaptureFile));

                // 3. launch intent-chooser
                final String chooserTitle = "Share Snaphot";
                SampleCamActivity.this.startActivity(Intent.createChooser(share, chooserTitle));

            } catch (final Exception e) {
                // should not occur when all permissions are set
                SampleCamActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // show toast message in case something went wrong
                        Toast.makeText(SampleCamActivity.this, "Unexpected error, " + e, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }


}
