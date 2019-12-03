package com.speech.service;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import org.springframework.stereotype.Service;

@Service
public class WaveSpeechSynthesis implements SpeechSynthesis {

    static {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    }

    @Override
    public byte[] synthesis(String text) {
        ByteArrayAudioPlayer audioPlayer = new ByteArrayAudioPlayer();
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        voice.allocate();
        voice.setAudioPlayer(audioPlayer);
        voice.speak(text);
        voice.deallocate();
        audioPlayer.close();
        return audioPlayer.getByteArrayOutputStream().toByteArray();
    }

}
