package CatanLib;

public class GlobalSettings {
    public static int SCREEN_WIDTH = 1920;
    public static int SCREEN_HEIGHT = 1080;

    public static int DEFAULT_SCREEN_WIDTH = 1920;
    public static int DEFAULT_SCREEN_HEIGHT = 1080;
    public static float TILE_WIDTH = 222;
    public static float TILE_HEIGHT = 256;
    public static float DEFAULT_TILE_WIDTH = 222;
    public static float DEFAULT_TILE_HEIGHT = 256;

    public static float MENU_ARROW_WIDTH = 70;

    public static float HAND_WIDTH = 1000;

    public static float STRUCTURE_WIDTH = 75;
    public static float STRUCTURE_HEIGHT = 75;

    public static float ROAD_WIDTH = TILE_HEIGHT / 2;
    public static float ROAD_HEIGHT = 20;


    public static float CARD_WIDTH = 170;
    public static float CARD_HEIGHT = 255;

    public static float BUTTON_WIDTH = 400;
    public static float BUTTON_HEIGHT = 100;

    public GlobalSettings() {
        SCREEN_HEIGHT = 1080;
        SCREEN_WIDTH = 1920;
    }

    public void RevertToStandardSettings(){
        SCREEN_HEIGHT = 1080;
        SCREEN_WIDTH = 1920;
    }
    public static float screenToImageHeight(float y){
        return SCREEN_HEIGHT - y;
    }



}
