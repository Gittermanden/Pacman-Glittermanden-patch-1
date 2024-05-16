package com.example;
import javafx.scene.image.Image;

public class AnimatedImage
{
    // assumes animation loops,
    //  each image displays for equal time
    public Image[] frames;
    public int currentFrame = 0;
    
    public AnimatedImage(String spriteName, int spriteCount, int framesPerSprite, String fileExt)
    {
        this.frames = new Image[spriteCount*framesPerSprite];
        int j = 0;
        for (int i = 0; i < spriteCount*framesPerSprite; i++){
            if (i % framesPerSprite == 0 && i > 0){
                j++;
                if (j > spriteCount){
                    j = 0;
                }
            }
            this.frames[i] = new Image(getResource(spriteName+j+fileExt));
        }   

    }

    private static String getResource(String filename)
    {
        return AnimatedImage.class.getResource(filename).toString();
    }

    public Image getFrame()
    {
        int framegrab = this.currentFrame;
        this.currentFrame++;
        if (this.currentFrame >= frames.length){
            this.currentFrame = 0;
        }
        return frames[framegrab];
    }
}