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
import android.content.res.Configuration;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class L7wavActivity extends Activity
{
	static final int SAMPLE_RATE = 44100;
	static final int SAMPLE_SIZE = SAMPLE_RATE / 10;

	static final double FREQ_A =  440;
	static final double FREQ_B =  493.883;
	static final double FREQ_C =  523.251;
	static final double FREQ_D =  587.33;
	static final double FREQ_E =  659.255;
	static final double FREQ_F =  698.456;
	static final double FREQ_G =  783.991;

	AudioTrack mTrack;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		int screenSize = getResources().getConfiguration().screenLayout &
			Configuration.SCREENLAYOUT_SIZE_MASK;

		if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE ||
				screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
			create_row(7);
			create_row(6);
			create_row(5);
		}

		create_row(4);
		create_row(3);
		create_row(2);

		if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
			create_row(1);
			create_row(0);
		}

		init_audio_track();
	}

	void create_row(int octave)
	{
		int screenSize = getResources().getConfiguration().screenLayout &
			Configuration.SCREENLAYOUT_SIZE_MASK;

		LinearLayout top_layout = (LinearLayout) findViewById(R.id.top_layout);

		LinearLayout row_layout = new LinearLayout(this);
		row_layout.setOrientation(LinearLayout.HORIZONTAL);
		row_layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
		row_layout.setGravity(Gravity.CENTER);

		if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
			row_layout.setPadding(100, 10, 100, 10);
		} else {
			row_layout.setPadding(10, 10, 10, 10);
		}

		double octave_multiplier = Math.pow(2, octave);

		Button a_button = new Button(this);
		init_button(a_button, FREQ_A * octave_multiplier, 1f);
		row_layout.addView(a_button);
		Button b_button = new Button(this);
		init_button(b_button, FREQ_B * octave_multiplier, 2f);
		row_layout.addView(b_button);
		Button c_button = new Button(this);
		init_button(c_button, FREQ_C * octave_multiplier, 3f);
		row_layout.addView(c_button);
		Button d_button = new Button(this);
		init_button(d_button, FREQ_D * octave_multiplier, 1f);
		row_layout.addView(d_button);
		Button e_button = new Button(this);
		init_button(e_button, FREQ_E * octave_multiplier, 2f);
		row_layout.addView(e_button);
		Button f_button = new Button(this);
		init_button(f_button, FREQ_F * octave_multiplier, 1f);
		row_layout.addView(f_button);
		Button g_button = new Button(this);
		init_button(g_button, FREQ_G * octave_multiplier, 2f);
		row_layout.addView(g_button);

		top_layout.addView(row_layout);
	}

	void init_button(Button button, final double freq, float weight)
	{
		button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					100, weight));

		final short[] snd = new short[SAMPLE_SIZE];
		init_l7wav(snd, freq);

		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				play_sound(snd);
			}
		});
	}

	void init_button(int button_id, final short[] buf)
	{
		Button button = (Button) findViewById(button_id);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				play_sound(buf);
			}
		});
	}

	void init_audio_track()
	{
		int minSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
		mTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLE_RATE,
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

		mTrack.play();
	}

	void init_l7wav(short[] buf, double freq) {
		boolean pos = true;
		short x = Short.MAX_VALUE;
		int j = 0;
		for (int i=0; i < SAMPLE_SIZE; ++i, ++j) {
			buf[i] = x;
			if (j == (int) (SAMPLE_RATE / (freq / 8.))) {
				j = 0;
				pos = !pos;
				x = pos ? Short.MAX_VALUE : Short.MIN_VALUE;
			}
		}
	}

	void init_sin_wav(short[] buf, double freq) {
		float angle = 0;
		for (int i=0; i < SAMPLE_SIZE; ++i) {
			buf[i] = (short)(Math.sin(angle)*Short.MAX_VALUE);
			angle += (float)(2*Math.PI) * (freq / 4.) / SAMPLE_RATE;
		}
	}

	void play_sound(short[] buf)
	{
		mTrack.write(buf, 0 , SAMPLE_SIZE);
	}
}

