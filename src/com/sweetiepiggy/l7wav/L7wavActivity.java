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
	short[] buf = new short[SAMPLE_SIZE];
	short[] mSnd_a = new short[SAMPLE_SIZE];
	short[] mSnd_b = new short[SAMPLE_SIZE];
	short[] mSnd_c = new short[SAMPLE_SIZE];
	short[] mSnd_d = new short[SAMPLE_SIZE];
	short[] mSnd_e = new short[SAMPLE_SIZE];
	short[] mSnd_f = new short[SAMPLE_SIZE];
	short[] mSnd_g = new short[SAMPLE_SIZE];

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		init_snd_arrays();
		init_buttons();
		init_audio_track();
	}

	void init_snd_arrays()
	{
		init_sin_wav(mSnd_a, FREQ_A);
		init_sin_wav(mSnd_b, FREQ_B);
		init_sin_wav(mSnd_c, FREQ_C);
		init_sin_wav(mSnd_d, FREQ_D);
		init_sin_wav(mSnd_e, FREQ_E);
		init_sin_wav(mSnd_f, FREQ_F);
		init_sin_wav(mSnd_g, FREQ_G);
	}

	void init_buttons()
	{
		init_button(R.id.a_button, mSnd_a);
		init_button(R.id.b_button, mSnd_b);
		init_button(R.id.c_button, mSnd_c);
		init_button(R.id.d_button, mSnd_d);
		init_button(R.id.e_button, mSnd_e);
		init_button(R.id.f_button, mSnd_f);
		init_button(R.id.g_button, mSnd_g);
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

	void init_sin_wav(short[] buf, double freq) {
		float angle = 0;
		for (int i=0; i < SAMPLE_SIZE; ++i) {
			buf[i] = (short)(Math.sin(angle)*Short.MAX_VALUE);
			angle += (float)(2*Math.PI) * freq / SAMPLE_RATE;
		}
	}

	void play_sound(short[] buf)
	{
		mTrack.write(buf, 0 , SAMPLE_SIZE);
	}
}

