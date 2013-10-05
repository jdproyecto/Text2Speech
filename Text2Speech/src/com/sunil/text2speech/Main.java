package com.sunil.text2speech;

import java.util.Locale;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

/**
 * 
 * @author JUAN FERNANDEZ
 *La primera prueba del malaguita.
 *
 */
public class Main extends Activity implements TextToSpeech.OnInitListener {
	/** Called when the activity is first created. */

	private TextToSpeech tts;
	private Button btnSpeak;
	private EditText txtText;
	
	private ToggleButton language;

	
	public void toggleClicked(View v) {
		Log.i("TAG","toggle");
		String msg = getResources().getString(R.string.msg_english);
		language = (ToggleButton) findViewById(R.id.language);
		if (language.getText().equals(msg)){
			this.setMVEnghish();
		}else{
			this.setMVSpanish();
		}
		this.setLanguageToSpeak();
	}
	
	private void setMVSpanish(){
		Locale.setDefault(new Locale("es", "ES"));
	}
	
	private void setMVEnghish(){
		Locale.setDefault(Locale.US);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tts = new TextToSpeech(this, this);
		btnSpeak = (Button) findViewById(R.id.btnSpeak);
		txtText = (EditText) findViewById(R.id.txtText);

		// button on click event
		btnSpeak.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				speakOut();
			}

		});
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			this.setMVSpanish();
			this.setLanguageToSpeak();
		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}

	private void setLanguageToSpeak() {
		int result = tts.setLanguage(Locale.getDefault());
		if (result == TextToSpeech.LANG_MISSING_DATA
				|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
			Log.e("TTS", "This Language is not supported");
		} else {
			btnSpeak.setEnabled(true);
			speakOut();
		}
	}

	private void speakOut() {
		String text = txtText.getText().toString();
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
}