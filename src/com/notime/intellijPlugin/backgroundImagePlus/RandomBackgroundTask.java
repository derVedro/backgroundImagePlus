package com.notime.intellijPlugin.backgroundImagePlus;

import com.intellij.ide.util.PropertiesComponent;
import com.notime.intellijPlugin.backgroundImagePlus.ui.Settings;

/**
 * Author: Allan de Queiroz
 * Date:   07/05/17
 * Modified by He Lili   Date: 19/10/18
 */
public class RandomBackgroundTask implements Runnable {
    @Override
    public void run() {
        PropertiesComponent prop = PropertiesComponent.getInstance();
        String folder = prop.getValue(Settings.FOLDER);
        String[] radioButton = prop.getValue(Settings.RADIO_BUTTON).split(",");
        boolean keepSameImage = prop.getBoolean(Settings.KEEP_SAME_IMAGE);
        String image = null;
        NotificationCenter.notice("Start update background");
        for (int i = 0; i < radioButton.length; i++) {
            String type = radioButton[i];
            if (i == 0 || !keepSameImage) {
                image = BingImageDownloader.download(folder);
            }
            if (image == null) {
                NotificationCenter.notice("Please check your internet connection");
                return;
            }
            if (image.contains(",")) {
                NotificationCenter.notice("Intellij wont load images with ',' character\n" + image);
            }
            NotificationCenter.notice("Image path" + image);
            prop.setValue(type, image);
        }
    }
    
}
