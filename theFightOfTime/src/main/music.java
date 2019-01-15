/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author 
 */
public class music {
    
    public void snowMusic() {
String soundName = "SnowMusic.wav";
 try {
            //  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(soundName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
} 
    
      public void jazzMusic() {
String soundName = "JazzMusic.wav";
 try {
            //  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(soundName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
} 
      
         public void fleshyPunch() {
String soundName = "fleshyPunch.wav";
 try {
            //  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(soundName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
} 
         
               public void icePunch() {
String soundName = "icePunch.wav";
 try {
            //  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(soundName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
} 
    
}



