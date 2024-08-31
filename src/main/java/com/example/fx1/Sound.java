package com.example.fx1;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private Clip clip;
    private URL[] soundURL = new URL[30];

    Sound() {

        soundURL[0] = getClass().getResource("/com/example/fx1/Music.wav");
        soundURL[1] = getClass().getResource("/com/example/fx1/bullet.wav");
        soundURL[2] = getClass().getResource("GlassBreak.wav");
        soundURL[3] = getClass().getResource("bullet_shoot.wav");
        soundURL[4] = getClass().getResource("robot-tank-34600.wav");
        soundURL[5] = getClass().getResource("impact-152508.wav");
        soundURL[6] = getClass().getResource("pickup.wav");
        soundURL[7] = getClass().getResource("coin-upaif-14631.wav");

    }

    public void setFile(int i) {
        try {
            if (soundURL[i] == null) {
                throw new RuntimeException("Sound file not found");
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException | IOException | javax.sound.sampled.LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Start from beginning
            clip.start();
        } else {
            System.out.println("Clip is not initialized");
        }
    }

    public void loop() {
        if (clip != null) {
            clip.setFramePosition(0); // Restart from beginning
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("Clip is not initialized");
        }
    }

    public void loop1() {
        if (clip != null) {
            clip.setFramePosition(6); // Restart from beginning
            clip.loop(1); // Play the clip 3 times in total (initial play + 2 repeats)
        } else {
            System.out.println("Clip is not initialized");
        }
    }


    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

}




