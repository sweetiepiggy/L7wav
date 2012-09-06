/*
    Copyright (C) 2012 Sweetie Piggy Apps <sweetiepiggyapps@gmail.com>

    This file is part of L~7.

    L~7 is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 3 of the License, or
    (at your option) any later version.

    L~7 is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with L~7; if not, see <http://www.gnu.org/licenses/>.
*/

package com.sweetiepiggy.l7wav;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class L7wavActivity extends Activity
{
	static final int SAMPLE_SIZE = 4410;
	int minSize;
	AudioTrack track;
	short[] buf = new short[SAMPLE_SIZE];

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		minSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
		track = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
			AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
			minSize, AudioTrack.MODE_STREAM);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

//		AudioManager am = getSystemService(Context.AUDIO_SERVICE);
//		int result;
//		do {
//			result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC,
//					AudioManager.AUDIOFOCUS_GAIN);
//			sleep(1);
//		} while (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED);

		track.play();

		Button a_button = (Button)findViewById(R.id.a_button);
		a_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				play_sound(440);
			}
		});

		Button b_button = (Button)findViewById(R.id.b_button);
		b_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				play_sound(493.883);
			}
		});

		Button c_button = (Button)findViewById(R.id.c_button);
		c_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				play_sound(523.251);
			}
		});

		Button d_button = (Button)findViewById(R.id.d_button);
		d_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				play_sound(587.33);
			}
		});

		Button e_button = (Button)findViewById(R.id.e_button);
		e_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				play_sound(659.255);
			}
		});

		Button f_button = (Button)findViewById(R.id.f_button);
		f_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				play_sound(698.456);
			}
		});

		Button g_button = (Button)findViewById(R.id.g_button);
		g_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				play_sound(783.991);
			}
		});
	}

	void play_sound(double freq)
	{
		float angle = 0;
		for (int i=0; i < SAMPLE_SIZE; ++i) {
			buf[i] = (short)(Math.sin(angle)*Short.MAX_VALUE);
			angle += (float)(2*Math.PI) * freq / 44100;
		}
		track.write(buf, 0 , SAMPLE_SIZE);
	}
}

