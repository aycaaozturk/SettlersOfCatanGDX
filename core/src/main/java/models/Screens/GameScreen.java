package models.Screens;

import CatanLib.GlobalSettings;
import Enums.ResourceType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.catan.CatanGame;
import models.Board;
import models.BoardPieces.City;
import models.BoardPieces.Road;
import models.BoardPieces.Settlement;
import models.BoardPieces.Structure;
import models.Edge;
import models.GUIElements.Button;
import models.GUIElements.HitBoxes.CircleHitBox;
import models.GUIElements.MenuArrow;
import models.GUIElements.Popups.BuildPopup;
import models.GUIElements.Popups.CardPopUp;
import models.GUIElements.Popups.DiceRollPopup;
import models.GUIElements.Popups.PopUp;
import models.GUIElements.HitBoxes.RectangleHitBox;
import models.Player;
import models.Vertex;
import models.cards.ResourceCard;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen, InputProcessor {

    private CatanGame game;

    private Vector3 cameraTarget;
    private float targetZoom;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private Vector3 mousePos = Vector3.Zero;

    private ShapeRenderer shapes;
    private SpriteBatch batch;

    private List<CircleHitBox> CornerHitBoxes = new ArrayList<>();
    private List<RectangleHitBox> EdgeHitBoxes = new ArrayList<>();

    private RectangleHitBox handEntryHitBox;
    private RectangleHitBox handExitHitBox;
    private boolean inHand = false;
    private Texture handBackground;
    private Vector3 handPosition = new Vector3();
    private Vector3 handTarget = new Vector3();
    private int handPage = 0;

    private Button endTurnButton;

    private List<RectangleHitBox> cardHitBoxes = new ArrayList<>();

    private MenuArrow handLeftArrow;
    private MenuArrow handRightArrow;

    private boolean buildPopUpOpened = false;

    List<PopUp> popUps = new ArrayList<>();

    boolean playerTurn = true;

    boolean dicePopUpOpened = false;

    private Player player;
    private Board board;


    public GameScreen(CatanGame game, Player player, Board board) {
        this.game = game;
        this.player = player;
        this.board = board;

        Gdx.input.setCursorCatched(true);
        Gdx.input.setInputProcessor(this);

       initCamera();
       initHand();

        shapes = new ShapeRenderer();
        batch = new SpriteBatch();

        createEdgeHitBoxes();
        createCornerHitBoxes();
        createHandHitBoxes();
        createCardHitBoxes();
        createEndTurnButton();
   }


    @Override
    public void render(float delta) {
        draw();

        if(!Gdx.input.isCursorCatched())
            return;
        updateCamera();

        //always render mouse last
        renderMouse();
    }

    private void draw(){
        Gdx.gl.glClearColor(0.4f, 0.6f, 0.8f, 1f);  // a bit darker light blue
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        batch.begin();
        renderBoard();
        renderStructures();

        batch.setProjectionMatrix(camera.combined);
        shapes.setProjectionMatrix(camera.combined);
        batch.end();
        shapes.end();

        if(playerTurn)
            renderButtons();

        renderHand(handPage);

        for(PopUp popUp : popUps)
            renderPopUp(popUp);

        if(Gdx.input.isCursorCatched() && !inHand && !buildPopUpOpened)
            drawHitBoxes();
        else if (Gdx.input.isCursorCatched()){
            if(buildPopUpOpened)
                drawPopUpHitBoxes();
        }
    }

    private void initCamera(){
       Gdx.graphics.setWindowedMode(1920, 1080);
       camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
       viewport = new ExtendViewport(GlobalSettings.SCREEN_WIDTH, GlobalSettings.SCREEN_HEIGHT, camera);
       camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
       cameraTarget = new Vector3();
       cameraTarget.set(camera.position);
       targetZoom = camera.zoom;

   }

    private void initHand(){
        handBackground = new Texture("HandBackground.png");
        handPosition.x = camera.viewportWidth / 2f;
        handPosition.y = 0f;
        handTarget.set(handPosition);

        handLeftArrow = new MenuArrow(
            new Vertex(
                handPosition.x - 0.5f * GlobalSettings.HAND_WIDTH - 0.5f * GlobalSettings.MENU_ARROW_WIDTH,
                handPosition.y + 100f - 0.5f * GlobalSettings.MENU_ARROW_WIDTH
            ),
            "left"
        );

        handRightArrow = new MenuArrow(
            new Vertex(
                handPosition.x + 0.5f * GlobalSettings.HAND_WIDTH - 0.5f * GlobalSettings.MENU_ARROW_WIDTH,
                handPosition.y + 100f - 0.5f * GlobalSettings.MENU_ARROW_WIDTH
            ),
            "right"
        );
    }

    private void updateCamera() {
        camera.position.lerp(cameraTarget, 0.1f);
        camera.zoom = MathUtils.lerp(camera.zoom, targetZoom, 0.1f); // 0.1f is smoothing factor (tweak this)
        camera.update();
    }

    private void createHandHitBoxes() {
        handEntryHitBox = new RectangleHitBox(new Vertex(handPosition.x, handPosition.y), 1000f, 400f, 0f, 1);
        handExitHitBox  = new RectangleHitBox(new Vertex(handPosition.x, handPosition.y), 1000f, 800f, 0f, 1);
    }

    private void createEdgeHitBoxes(){
        for (Edge edge : board.getEdges()) {
            EdgeHitBoxes.add(new RectangleHitBox(edge.getCenter(), GlobalSettings.TILE_HEIGHT/2 - 40f, 50, edge.getAngle(), edge, 0));
        }
    }

    private void createCornerHitBoxes(){
        for(Vertex vertex : board.getVertices()){
            CornerHitBoxes.add(new CircleHitBox(20, vertex, 0));
        }
    }

    private void createCardHitBoxes(){
        RectangleHitBox hb0 = new RectangleHitBox(
            new Vertex(
                handPosition.x - 0.4f * GlobalSettings.HAND_WIDTH - 40f + 0.5f * GlobalSettings.CARD_WIDTH,
                handPosition.y + 0.5f * GlobalSettings.CARD_HEIGHT + 40
            ),
            GlobalSettings.CARD_WIDTH - 20f,
            GlobalSettings.CARD_HEIGHT,
            0,
            new ResourceCard(ResourceType.WOOD)
        );

        RectangleHitBox hb1 = new RectangleHitBox(
            new Vertex(
                handPosition.x - 0.4f * GlobalSettings.HAND_WIDTH - 30f + 0.5f * GlobalSettings.CARD_WIDTH + GlobalSettings.CARD_WIDTH,
                handPosition.y + 0.5f * GlobalSettings.CARD_HEIGHT + 40
            ),
            GlobalSettings.CARD_WIDTH - 20f,
            GlobalSettings.CARD_HEIGHT,
            0,
            new ResourceCard(ResourceType.CLAY)
        );

        RectangleHitBox hb2 = new RectangleHitBox(
            new Vertex(
                handPosition.x - 0.4f * GlobalSettings.HAND_WIDTH - 20f + 0.5f * GlobalSettings.CARD_WIDTH + 2f * GlobalSettings.CARD_WIDTH,
                handPosition.y + 0.5f * GlobalSettings.CARD_HEIGHT + 40
            ),
            GlobalSettings.CARD_WIDTH - 20f,
            GlobalSettings.CARD_HEIGHT,
            0,
            new ResourceCard(ResourceType.WOOL)
        );

        RectangleHitBox hb3 = new RectangleHitBox(
            new Vertex(
                handPosition.x - 0.4f * GlobalSettings.HAND_WIDTH - 10f + 0.5f * GlobalSettings.CARD_WIDTH + 3f * GlobalSettings.CARD_WIDTH,
                handPosition.y + 0.5f * GlobalSettings.CARD_HEIGHT + 40
            ),
            GlobalSettings.CARD_WIDTH - 20f,
            GlobalSettings.CARD_HEIGHT,
            0,
            new ResourceCard(ResourceType.GRAIN)
        );

        RectangleHitBox hb4 = new RectangleHitBox(
            new Vertex(
                handPosition.x - 0.4f * GlobalSettings.HAND_WIDTH + 0.5f * GlobalSettings.CARD_WIDTH + 4f * GlobalSettings.CARD_WIDTH,
                handPosition.y + 0.5f * GlobalSettings.CARD_HEIGHT + 40
            ),
            GlobalSettings.CARD_WIDTH - 20f,
            GlobalSettings.CARD_HEIGHT,
            0,
            new ResourceCard(ResourceType.ORE)
        );

        cardHitBoxes.add(hb0);
        cardHitBoxes.add(hb1);
        cardHitBoxes.add(hb2);
        cardHitBoxes.add(hb3);
        cardHitBoxes.add(hb4);
    }

    private void createEndTurnButton(){
        Vertex pivot = new Vertex(GlobalSettings.SCREEN_WIDTH - 0.5f* GlobalSettings.BUTTON_WIDTH - 10
            ,GlobalSettings.SCREEN_HEIGHT - 0.5f* GlobalSettings.BUTTON_HEIGHT - 10);
        this.endTurnButton = new Button(
            pivot,
            GlobalSettings.BUTTON_WIDTH,
            GlobalSettings.BUTTON_HEIGHT,
            new Texture("GUIElements/EndTurnButton.png"),
            new Texture("GUIElements/EndTurnButtonHover.png"));

    }

    private void renderButtons(){
        SpriteBatch buttonBatch = new SpriteBatch();
        buttonBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        buttonBatch.begin();

        buttonBatch.draw(
            endTurnButton.getTexture(),
            endTurnButton.getTextureOrigin().getX(),
            endTurnButton.getTextureOrigin().getY(),
            endTurnButton.getWidth(),
            endTurnButton.getHeight()
            );


        buttonBatch.end();
    }

    private void renderBoard(){

        batch.draw(board.getTiles().get(1).getImage(), board.getTiles().get(1).getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(1).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(2).getImage(), board.getTiles().get(2). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(2).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(3).getImage(), board.getTiles().get(3). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(3).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(4).getImage(), board.getTiles().get(4). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(4).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(5).getImage(), board.getTiles().get(5). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(5).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(6).getImage(), board.getTiles().get(6). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(6).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(7).getImage(), board.getTiles().get(7). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(7).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(8).getImage(), board.getTiles().get(8). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(8).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(9).getImage(), board.getTiles().get(9). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(9).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(10).getImage(), board.getTiles().get(10). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(10).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(11).getImage(), board.getTiles().get(11). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(11).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(12).getImage(), board.getTiles().get(12). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(12).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(13).getImage(), board.getTiles().get(13). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(13).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(14).getImage(), board.getTiles().get(14). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(14).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(15).getImage(), board.getTiles().get(15). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(15).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(16).getImage(), board.getTiles().get(16). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(16).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(17).getImage(), board.getTiles().get(17). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(17).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(18).getImage(), board.getTiles().get(18). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(18).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);
        batch.draw(board.getTiles().get(19).getImage(), board.getTiles().get(19). getX() - (0.5f * GlobalSettings.TILE_WIDTH), screenToImageHeight(board.getTiles().get(19).getY()) - (0.5f * GlobalSettings.TILE_HEIGHT), GlobalSettings.TILE_WIDTH, GlobalSettings.TILE_HEIGHT);

    }

    private void renderMouse(){
        mousePos.set(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        mousePos = camera.unproject(mousePos);
        ShapeRenderer mouseRender = new ShapeRenderer();
        mouseRender.setProjectionMatrix(camera.combined);
        mouseRender.begin(ShapeRenderer.ShapeType.Filled);
        mouseRender.setColor(Color.GREEN);
        mouseRender.circle(mousePos.x, mousePos.y, 5);
        mouseRender.end();
    }

    private void renderHand(int page){

        handPosition.lerp(handTarget, 0.1f);
        SpriteBatch handBatch = new SpriteBatch();
        handBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        handBatch.begin();
        handBatch.draw(handBackground, handPosition.x - 500 ,handPosition.y - 200);

        handBatch.draw(
            handLeftArrow.getTexture(),
            handLeftArrow.getOrigin().getX(),
            handLeftArrow.getOrigin().getY(),
            handLeftArrow.getWidth(),
            handLeftArrow.getHeight()
        );

        handBatch.draw(
            handRightArrow.getTexture(),
            handRightArrow.getOrigin().getX(),
            handRightArrow.getOrigin().getY(),
            handRightArrow.getWidth(),
            handRightArrow.getHeight()
        );

        if(page == 0){
            int woodAmount = player.countResource(ResourceType.WOOD);
            int clayAmount = player.countResource(ResourceType.CLAY);
            int woolAmount = player.countResource(ResourceType.WOOL);
            int oreAmount = player.countResource(ResourceType.ORE);
            int grainAmount = player.countResource(ResourceType.GRAIN);

            for (int i = 0; i < woodAmount; i++) {
                handBatch.draw(
                    ResourceCard.WoodTexture,
                    handPosition.x - 0.4f * GlobalSettings.HAND_WIDTH - 40f,
                    handPosition.y - 100 - i * 8,
                    GlobalSettings.CARD_WIDTH,
                    GlobalSettings.CARD_HEIGHT
                    );
            }

            for (int i = 0; i < clayAmount; i++) {
                handBatch.draw(
                    ResourceCard.ClayTexture,
                    handPosition.x - 0.4f * GlobalSettings.HAND_WIDTH + GlobalSettings.CARD_WIDTH - 30f,
                    handPosition.y - 100 - i * 8,
                    GlobalSettings.CARD_WIDTH,
                    GlobalSettings.CARD_HEIGHT
                );
            }

            for (int i = 0; i < woolAmount; i++) {
                handBatch.draw(
                    ResourceCard.WoolTexture,
                    handPosition.x - 0.4f * GlobalSettings.HAND_WIDTH + 2f * GlobalSettings.CARD_WIDTH - 20f,
                    handPosition.y - 100 - i * 8,
                    GlobalSettings.CARD_WIDTH,
                    GlobalSettings.CARD_HEIGHT
                );
            }

            for (int i = 0; i < grainAmount; i++) {
                handBatch.draw(
                    ResourceCard.GrainTexture,
                    handPosition.x - 0.4f * GlobalSettings.HAND_WIDTH + 3f * GlobalSettings.CARD_WIDTH - 10f,
                    handPosition.y - 100 - i * 8,
                    GlobalSettings.CARD_WIDTH,
                    GlobalSettings.CARD_HEIGHT
                );
            }

            for (int i = 0; i < oreAmount; i++) {
                handBatch.draw(
                    ResourceCard.OreTexture,
                    handPosition.x - 0.4f * GlobalSettings.HAND_WIDTH + 4f * GlobalSettings.CARD_WIDTH,
                    handPosition.y - 100 - i * 8,
                    GlobalSettings.CARD_WIDTH,
                    GlobalSettings.CARD_HEIGHT
                );
            }

        }
        handBatch.end();
    }

    private void renderPopUp(PopUp popUp){

        if(popUp instanceof CardPopUp && inHand){
            renderCardPopUp((CardPopUp) popUp);
        }
        else if (popUp instanceof BuildPopup && !inHand){
            renderBuildPopup((BuildPopup) popUp);
        } else if (popUp instanceof DiceRollPopup){
            renderDicePopUp((DiceRollPopup) popUp);
        }

    }

    private void renderDicePopUp(DiceRollPopup popUp){
        SpriteBatch popUpBatch = new SpriteBatch();
        popUpBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        popUpBatch.begin();

        popUp.Draw(popUpBatch);

        popUpBatch.end();

    }

    private void renderCardPopUp(CardPopUp cardPopUp){
        SpriteBatch cardBatch = new SpriteBatch();
        cardBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        cardBatch.begin();

        cardBatch.draw(
            cardPopUp.getTexture(),
            cardPopUp.getOrigin().getX(),
            cardPopUp.getOrigin().getY(),
            CardPopUp.CardPopUpWidth,
            CardPopUp.CardPopUpHeight
        );

        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.draw(
            cardBatch,
            cardPopUp.getTitle(),
            cardPopUp.getOrigin().getX() + 15,
            cardPopUp.getOrigin().getY() + CardPopUp.CardPopUpHeight - 5
        );

        font.draw(
            cardBatch,
            cardPopUp.getDescription(),
            cardPopUp.getOrigin().getX() + 15,
            cardPopUp.getOrigin().getY() + 0.5f * CardPopUp.CardPopUpHeight - 5
        );

        cardBatch.end();



    }

    private void renderBuildPopup(BuildPopup buildPopup){
        SpriteBatch popUpBatch = new SpriteBatch();
        popUpBatch.setProjectionMatrix(camera.combined);
        popUpBatch.begin();

        popUpBatch.draw(
            buildPopup.getBackgroundTexture(),
            buildPopup.getBackgroundOrigin().getX(),
            screenToImageHeight(buildPopup.getBackgroundOrigin().getY()),
            buildPopup.getWidth(),
            buildPopup.getHeight()
        );

        popUpBatch.draw(
            buildPopup.getCancelButtonTexture(),
            buildPopup.getCancelButtonOrigin().getX(),
            screenToImageHeight(buildPopup.getCancelButtonOrigin().getY()),
            buildPopup.getButtonWidth(),
            buildPopup.getButtonHeight()
        );

        popUpBatch.draw(
            buildPopup.getConfirmButtonTexture(),
            buildPopup.getConfirmButtonOrigin().getX(),
            screenToImageHeight(buildPopup.getConfirmButtonOrigin().getY()),
            buildPopup.getButtonWidth(),
            buildPopup.getButtonHeight()
        );

        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.draw(
            popUpBatch,
            buildPopup.getTitle(),
            buildPopup.getBackgroundOrigin().getX() + 15,
            screenToImageHeight(buildPopup.getBackgroundOrigin().getY() - buildPopup.getHeight() + 10)
        );


        popUpBatch.end();
    }

    private void renderStructures(){
        SpriteBatch structuresBatch = new SpriteBatch();
        structuresBatch.setProjectionMatrix(camera.combined);
        structuresBatch.begin();

        for (Structure structure : board.getStructures()) {
            if(structure instanceof Road){
                batch.draw(
                    new TextureRegion(structure.getTexture()),
                    structure.getSourceEdge().getCenter().getX() - 0.5f * GlobalSettings.ROAD_WIDTH,
                    screenToImageHeight(structure.getSourceEdge().getCenter().getY() + 0.5f * GlobalSettings.ROAD_HEIGHT),
                    0.5f * GlobalSettings.ROAD_WIDTH,
                    0.5f * GlobalSettings.ROAD_HEIGHT,
                    GlobalSettings.ROAD_WIDTH,
                    GlobalSettings.ROAD_HEIGHT,
                    1,
                    1,
                    -structure.getSourceEdge().getAngle()
                );
            }
        }
        for (Structure structure : board.getStructures()){
            if(structure instanceof City || structure instanceof Settlement){
                batch.draw(
                    structure.getTexture(),
                    structure.getSourceVertex().getX() - 0.5f * GlobalSettings.STRUCTURE_WIDTH,
                    screenToImageHeight(structure.getSourceVertex().getY() + 0.5f * GlobalSettings.STRUCTURE_WIDTH),
                    GlobalSettings.STRUCTURE_WIDTH,
                    GlobalSettings.STRUCTURE_HEIGHT
                );
            }

        }
        structuresBatch.end();
    }

    //TODO change to changing to hover like arrows
    private void drawPopUpHitBoxes(){
        BuildPopup buildPopup = getBuildPopup();
        if(buildPopup == null)
            return;


        ShapeRenderer hitBoxRenderer = new ShapeRenderer();
        hitBoxRenderer.setProjectionMatrix(camera.combined);
        hitBoxRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if(buildPopup.getHitBoxStatus("confirm")){
            hitBoxRenderer.rect(
                buildPopup.getConfirmButtonOrigin().getX(),
                screenToImageHeight(buildPopup.getConfirmButtonOrigin().getY()),
                0,
                0,
                buildPopup.getButtonWidth(),
                buildPopup.getButtonHeight(),
                1f,
                1f,
                0,
                Color.RED,
                Color.RED,
                Color.RED,
                Color.RED);
        }
        if(buildPopup.getHitBoxStatus("cancel")){
            hitBoxRenderer.rect(
                buildPopup.getCancelButtonOrigin().getX(),
                screenToImageHeight(buildPopup.getCancelButtonOrigin().getY()),
                0,
                0,
                buildPopup.getButtonWidth(),
                buildPopup.getButtonHeight(),
                1f,
                1f,
                0,
                Color.RED,
                Color.RED,
                Color.RED,
                Color.RED);
        }
        hitBoxRenderer.end();

    }

    private void drawHitBoxes(){
        ShapeRenderer hitBoxRenderer = new ShapeRenderer();
        hitBoxRenderer.setProjectionMatrix(camera.combined);
        hitBoxRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (RectangleHitBox hit : EdgeHitBoxes){
            if(!hit.active)
                continue;
            hitBoxRenderer.rect(
                hit.getX() - 0.5f * hit.getWidth(),
                screenToImageHeight(hit.getY() + 0.5f * hit.getHeight()),
                0.5f * hit.getWidth(),
                0.5f * hit.getHeight(),
                hit.getWidth(),
                hit.getHeight(),
                1f,
                1f,
                -hit.getRotation(),
                Color.RED,
                Color.RED,
                Color.RED,
                Color.RED);
        }

        for (CircleHitBox hit : CornerHitBoxes){
            if(!hit.active)
                continue;
            hitBoxRenderer.circle(hit.getX(), screenToImageHeight(hit.getY()), hit.getRadius(), 16);
        }
        hitBoxRenderer.end();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY){

        if(dicePopUpOpened){

            DiceRollPopup popup = null;

            for (PopUp popUp : popUps){
                if(popUp instanceof DiceRollPopup){
                    popup = (DiceRollPopup) popUp;
                }
            }
            if(popup == null){
                dicePopUpOpened = false;
                return false;
            }

            popup.hit(screenX, screenToImageHeight(screenY));

            return false;
        }

        checkHandHitBoxes(screenX, screenY);

        if(inHand){
            checkCardHitBoxes(screenX, screenY);
        }

        if(!buildPopUpOpened)
            checkBoardHitBoxes(screenX, screenY);
        if(buildPopUpOpened)
            checkBuildHitBoxes(screenX, screenY);

        return false;
    }

    private void checkBoardHitBoxes(int x, int y){
        Vector3 coords = getGlobalCoordinates(x, y);

        for (RectangleHitBox hit : EdgeHitBoxes){
            hit.active = hit.hit(coords.x, screenToImageHeight(coords.y));
        }
        for (CircleHitBox hit : CornerHitBoxes) {
            hit.active = hit.hit(coords.x, screenToImageHeight(coords.y));
        }
    }

    private BuildPopup getBuildPopup(){
        BuildPopup buildPopup = null;
        for (PopUp popUp : popUps){
            if(popUp instanceof BuildPopup){
                buildPopup = (BuildPopup) popUp;
            }
        }
        return buildPopup;
    }

    private void checkBuildHitBoxes(int x, int y){
        if(!buildPopUpOpened)
            return;

        Vector3 coords = getGlobalCoordinates(x, y);
        BuildPopup buildPopup = getBuildPopup();

        if(buildPopup == null)
            return;

        String popupReturn = buildPopup.BuildOrCancelHit(coords.x, screenToImageHeight(coords.y));

        switch (popupReturn) {
            case "confirm" -> {
                buildPopup.changeHitBoxStatus(true, "confirm");
                buildPopup.changeHitBoxStatus(false, "cancel");
            }
            case "cancel" -> {
                buildPopup.changeHitBoxStatus(true, "cancel");
                buildPopup.changeHitBoxStatus(false, "confirm");
            }
            case "missed" -> {
                buildPopup.changeHitBoxStatus(false, "cancel");
                buildPopup.changeHitBoxStatus(false, "confirm");
            }
            case null, default -> {
            }
        }
    }

    private void checkHandHitBoxes(int x, int y){

        endTurnButton.setHover(endTurnButton.hit(x, screenToImageHeight(y)));

        if(handLeftArrow.hit(x, screenToImageHeight(y))){
            handLeftArrow.setHover(true);
            return;
        }
        else{
            handLeftArrow.setHover(false);
        }

        if(handRightArrow.hit(x, screenToImageHeight(y))){
            handRightArrow.setHover(true);
            return;
        }
        else
            handRightArrow.setHover(false);

        if(inHand){
            if(!handExitHitBox.hit(x,  screenToImageHeight(y))){
                inHand = false;
                handTarget.set(new Vector3(GlobalSettings.SCREEN_WIDTH * 0.5f, 0, 0));
            }
        } else {
            if(handEntryHitBox.hit(x, screenToImageHeight(y))){
                inHand = true;
                handTarget.set(new Vector3(GlobalSettings.SCREEN_WIDTH * 0.5f, 140, 0));
            }
        }
    }

    private void checkCardHitBoxes(int x, int y){
        if(handPage != 0)
            return;
        popUps.removeIf(popUp -> popUp instanceof CardPopUp);
        for(RectangleHitBox hit : cardHitBoxes){
            if(hit.hit(x,screenToImageHeight(y))){
                ResourceCard source = (ResourceCard) hit.getSource();
                popUps.add(CardPopUp.getResourcePopUp(
                    new Vertex(hit.getX(), hit.getY()),
                    source.getType(),
                    player.countResource(source.getType())
                ));
                return;
            }
        }
    }

    public Vector3 getGlobalCoordinates(int screenX, int screenY) {
        Vector3 touchChoords = new Vector3(screenX, screenY, 0);
        Vector3 coords = camera.unproject(
            touchChoords,
            0,
            0,
            GlobalSettings.SCREEN_WIDTH,
            GlobalSettings.SCREEN_HEIGHT);

        return coords;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.D){
            cameraTarget.x += 100f;
        }
        else if(keycode == Input.Keys.A){
            cameraTarget.x -= 100f;
        }
        else if(keycode == Input.Keys.W){
            cameraTarget.y += 100f;
        }
        else if(keycode == Input.Keys.S){
            cameraTarget.y -= 100f;
        }
        else if(keycode == Input.Keys.ESCAPE){
            Gdx.input.setCursorCatched(false);
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!Gdx.input.isCursorCatched())
            Gdx.input.setCursorCatched(true);


        if(dicePopUpOpened && button == Input.Buttons.LEFT){

            DiceRollPopup popup = null;

            for (PopUp popUp : popUps){
                if(popUp instanceof DiceRollPopup){
                    popup = (DiceRollPopup) popUp;
                }
            }
            if(popup == null){
                dicePopUpOpened = false;
                return false;
            }

            if(popup.hit(screenX, screenToImageHeight(screenY))){
                dicePopUpOpened = false;
                popUps.remove(popup);
            }

            return false;
        }

        if(button == Input.Buttons.LEFT){
            if(endTurnButton.hit(screenX, screenToImageHeight(screenY))){
                game.ChangeTurn();
            }
        }

        if(button == Input.Buttons.LEFT){
            if(handLeftArrow.hit(screenX, screenToImageHeight(screenY))){
                handPage--;
                if(handPage < 0)
                    handPage = 0;
            }
            else if(handRightArrow.hit(screenX, screenToImageHeight(screenY))){
                handPage++;
                //Todo set max pages here
            }
        }

        if(inHand)
            return false;

        if (button == Input.Buttons.LEFT){
            BuildPopup buildPopup = getBuildPopup();
            if(buildPopUpOpened && buildPopup != null){
                boolean cancelStatus = buildPopup.getHitBoxStatus("cancel");
                boolean confirmStatus = buildPopup.getHitBoxStatus("confirm");

                if(cancelStatus && confirmStatus){
                    return false;
                }
                if(confirmStatus){
                    boolean buildSuccess = false;
                    if(buildPopup.getSourceStructure() instanceof City){
                        if (player.canBuildCity()){
                            Structure toRemove = null;
                            for (Structure structure : board.getStructures()) {
                                if (structure.getSourceVertex() == buildPopup.getSourceStructure().getSourceVertex())
                                    toRemove = structure;
                            }
                            board.addStructure(buildPopup.getSourceStructure());
                            if(toRemove != null)
                                board.removeStructure(toRemove);

                            player.takeResources(buildPopup.getSourceStructure());
                            buildSuccess = true;
                        }
                    } else if (buildPopup.getSourceStructure() instanceof Settlement){
                        if (player.canBuildSettlement()){
                            board.addStructure(buildPopup.getSourceStructure());
                            player.takeResources(buildPopup.getSourceStructure());
                            buildSuccess = true;
                        }
                    } else if (buildPopup.getSourceStructure() instanceof Road){
                        if (player.canBuildRoad()){
                            board.addStructure(buildPopup.getSourceStructure());
                            player.takeResources(buildPopup.getSourceStructure());
                            buildSuccess = true;
                        }
                    }
                    if(buildSuccess){
                        popUps.removeIf(popUp -> popUp instanceof BuildPopup);
                        buildPopUpOpened = false;
                    }
                    return false;
                }
                if(cancelStatus){
                    popUps.removeIf(popUp -> popUp instanceof BuildPopup);
                    buildPopUpOpened = false;
                    return false;
                }
            } else {
                for (CircleHitBox hit : CornerHitBoxes){
                    Vertex source = hit.getVertex();
                    Structure structure = getStructureForVertex(source);
                    if(!hit.active)
                        continue;
                    if(structure instanceof Settlement){
                        buildPopUpOpened = true;
                        BuildPopup bp = new BuildPopup(new City(hit.getVertex(), player));
                        popUps.add(bp);
                    } else if (structure == null){
                        buildPopUpOpened = true;
                        BuildPopup bp = new BuildPopup(new Settlement(hit.getVertex(), player));
                        popUps.add(bp);
                    }
                }
                if(buildPopUpOpened)
                    return false;
                for (RectangleHitBox hit : EdgeHitBoxes){
                    if(!hit.active)
                        continue;
                    Edge source = hit.getEdge();
                    Structure structure = getStructureForEdge(source);

                    if(structure == null){
                        buildPopUpOpened = true;
                        BuildPopup bp = new BuildPopup(new Road((Edge)hit.getSource(), player));
                        popUps.add(bp);
                    }
                }
            }
        }


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if(amountY > 0)
            targetZoom += 0.1f;
        if(amountY < 0)
            targetZoom -= 0.1f;

        targetZoom = MathUtils.clamp(targetZoom, 0.5f, 6f);
        return false;
    }

    @Override
    public void resize (int width, int height) {
        // viewport must be updated for it to work properly
        viewport.update(width, height, true);
        GlobalSettings.SCREEN_WIDTH = width;
        GlobalSettings.SCREEN_HEIGHT = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }


    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
    }

    private float screenToImageHeight(float y){
        return GlobalSettings.SCREEN_HEIGHT - y;
    }

    private Structure getStructureForVertex(Vertex vertex){
        for (Structure structure : board.getStructures()){
            if(structure.getSourceVertex() != null && structure.getSourceVertex().equals(vertex)){
                return structure;
            }
        }
        return null;
    }

    private Structure getStructureForEdge(Edge edge){
        for (Structure structure : board.getStructures()){
            if(structure.getSourceEdge() != null && structure.getSourceEdge().equals(edge)){
                return structure;
            }
        }
        return null;
    }
}
