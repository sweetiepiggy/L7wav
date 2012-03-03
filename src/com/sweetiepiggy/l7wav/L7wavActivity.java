/*
    Copyright (C) 2012 Sweetie Piggy Apps <sweetiepiggyapps@gmail.com>

    This file is part of L7wav.

    L7wav is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 3 of the License, or
    (at your option) any later version.

    L7wav is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with L7wav; if not, see <http://www.gnu.org/licenses/>.
*/

package com.sweetiepiggy.l7wav;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class L7wavActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button a_button = (Button)findViewById(R.id.a_button);
		a_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "a",
						Toast.LENGTH_SHORT).show();
				int minSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
				AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
					AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
					minSize, AudioTrack.MODE_STREAM);
				track.play();
				short[] buf = new short[20480];
				float angle = 0;
				for (int i=0; i < 20480; ++i) {
					buf[i] = (short)(Math.sin(angle)*Short.MAX_VALUE);
					angle += (float)(2*Math.PI) * 440 / 44100;
				}
				track.write(buf, 0 , 20480);
				Toast.makeText(getApplicationContext(), "b",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}

