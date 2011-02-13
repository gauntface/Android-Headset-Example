package co.uk.gauntface.android.headsetexample;

import co.uk.gauntface.android.headsetexample.HardButtonReceiver.HardButtonListener;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Copyright 2011 Matthew Gaunt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 
 * NOTE:
 * This code has been briefly tested on a Nexus One running Android 
 * version 2.2.2 and a G1 running version 1.6
 * 
 * 
 * @author Matthew Gaunt - http://www.gauntface.co.uk/
 *
 */

public class HeadsetExample extends Activity implements HardButtonListener {
    
	private final static String TAG = "gauntface";
	
	private TextView mMessageTextView;
	private HardButtonReceiver mButtonReceiver;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mMessageTextView = (TextView) findViewById(R.id.MainMessageTextView);
        
        mButtonReceiver = new HardButtonReceiver(this);
        
        // Create the intent filter the button receiver will handle
        IntentFilter iF = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);
        /**
         * Assign the intent filter a high priority so this receiver
         * gets called first on a button press which can then determine
         * whether to pass it down to other apps or not (i.e. the Music app)
         * 
         * Initially I found the SYSTEM_HIGH_PRIORITY didn't work, lastfm seemed
         * to get first request of the button press, then the music player and 
         * this example didn't get access to it.
         * 
         * So while +1 works, its extremely easy to break (i.e. another developer uses
         * SYSTEM_HIGH_PRIORITY + 1 then you may not get access to the button event).
         * 
         * Also the Document says to use a value between SYSTEM_LOW_PRIORITY and
         * SYSTEM_HIGH_PRIORITY (i.e. -1000 & 1000)
         */
        iF.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY + 1);
        
        // register the receiver
        registerReceiver(mButtonReceiver, iF);
        Log.v(TAG, "HeadsetExample: The Button Receiver has been registered");
    }
    
    public void onStop() {
    	super.onStop();
    	
    	// In the onStop method you should unregister the receiver
    	unregisterReceiver(mButtonReceiver);
    	Log.v(TAG, "HeadsetExample: The Button Receiver has been unregistered");
    }
    
    /**
     * HardButtonListener methods
     */
	@Override
	public void onPrevButtonPress() {
		Log.v(TAG, "HeadsetExample: Previous button pressed");
		mMessageTextView.setText(getString(R.string.message_prev_button_press));
	}

	@Override
	public void onNextButtonPress() {
		Log.v(TAG, "HeadsetExample: Next button pressed");
		mMessageTextView.setText(getString(R.string.message_next_button_press));
	}

	@Override
	public void onPlayPauseButtonPress() {
		Log.v(TAG, "HeadsetExample: Play / Pause button pressed");
		mMessageTextView.setText(getString(R.string.message_play_pause_button_press));
	}
}