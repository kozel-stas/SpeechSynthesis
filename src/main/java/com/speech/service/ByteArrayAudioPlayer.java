package com.speech.service;

import com.sun.speech.freetts.audio.AudioPlayer;

import java.io.*;
import java.util.Vector;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFileFormat.Type;

public class ByteArrayAudioPlayer implements AudioPlayer, AutoCloseable {
    private AudioFormat currentFormat;
    private byte[] outputData;
    private int curIndex;
    private int totBytes;
    private Type outputType;
    private Vector outputList;
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    public ByteArrayAudioPlayer() {
        this.currentFormat = null;
        this.curIndex = 0;
        this.totBytes = 0;
        this.outputType = Type.WAVE;
        this.outputList = new Vector();
    }

    public ByteArrayOutputStream getByteArrayOutputStream() {
        return byteArrayOutputStream;
    }

    public synchronized void setAudioFormat(AudioFormat format) {
        this.currentFormat = format;
    }

    public AudioFormat getAudioFormat() {
        return this.currentFormat;
    }

    public void pause() {
    }

    public synchronized void resume() {
    }

    public synchronized void cancel() {
    }

    public synchronized void reset() {
    }

    public void startFirstSampleTimer() {
    }

    public synchronized void close() {
        try {
            InputStream is = new SequenceInputStream(this.outputList.elements());
            AudioInputStream ais = new AudioInputStream(is, this.currentFormat, (long) (this.totBytes / this.currentFormat.getFrameSize()));
            AudioSystem.write(ais, outputType, byteArrayOutputStream);
        } catch (IOException ignored) {
        } catch (IllegalArgumentException var5) {
            System.err.println("Can't write audio type " + this.outputType);
        }

    }

    public float getVolume() {
        return 1.0F;
    }

    public void setVolume(float volume) {
    }

    public void begin(int size) {
        this.outputData = new byte[size];
        this.curIndex = 0;
    }

    public boolean end() {
        this.outputList.add(new ByteArrayInputStream(this.outputData));
        this.totBytes += this.outputData.length;
        return true;
    }

    public boolean drain() {
        return true;
    }

    public synchronized long getTime() {
        return -1L;
    }

    public synchronized void resetTime() {
    }

    public boolean write(byte[] audioData) {
        return this.write(audioData, 0, audioData.length);
    }

    public boolean write(byte[] bytes, int offset, int size) {
        System.arraycopy(bytes, offset, this.outputData, this.curIndex, size);
        this.curIndex += size;
        return true;
    }

    public String toString() {
        return "FileAudioPlayer";
    }

    public void showMetrics() {
    }
}

