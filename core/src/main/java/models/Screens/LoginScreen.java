package models.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.catan.CatanGame;
import models.Users.User;
import models.Users.UserManager;

public class LoginScreen implements Screen {
    // if login -> to mainmenu screen
    //if reigster -> to register screen



    private Stage stage;   //where everything is showed, its a base
    private Skin skin;     // all the ui elements

    private TextField usernameField;
    private TextField passwordField;
    private TextButton registerButton;
    private TextButton loginButton;
    private Label messageLabel;

    private final UserManager userManager;  //like a database
    private final CatanGame game;

    public LoginScreen(CatanGame game, UserManager userManager) {
        this.game = game;
        this.userManager = userManager;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

//        skin = new Skin(Gdx.files.internal("uiskin.json"));
//        //TODO json files should be uploaded

        Table table = new Table();
        table.setFillParent(true);
        table.center();


        Label title = new Label("CATAN", skin);
        title.setFontScale(4);
        title.setColor(Color.ORANGE);


        usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");

        passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');


        messageLabel = new Label("", skin);


        registerButton = new TextButton("Register", skin);
        loginButton = new TextButton("Login", skin);


        addButtonListeners();


        table.add(title).padBottom(40).row();
        table.add(usernameField).width(300).padBottom(20).row();
        table.add(passwordField).width(300).padBottom(20).row();
        table.add(messageLabel).padBottom(20).row();

        Table buttonTable = new Table();
        buttonTable.add(registerButton).width(140).padRight(20);
        buttonTable.add(loginButton).width(140);
        table.add(buttonTable).row();

        stage.addActor(table);
    }

    private void addButtonListeners() {
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText();

                if (userManager.login(username, password)) {
                    User user = userManager.getUser(username);
                    messageLabel.setText("Login successful! Welcome to the game " + user.getFullName()+"...");
                    //game.setCurrentUser(user);


                    game.setScreen(new MainMenuScreen(game));
                    //if login succesful, it takes the user to the main menu

                } else {
                    messageLabel.setText("Invalid username or password.");
                    passwordField.setText("");  //clears the password field for new entry
                }
            }
        });

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new RegisterScreen(game, userManager));
                //if the user is not registered before, it takes the user to the register screen
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.85f, 0.92f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void dispose() { stage.dispose(); skin.dispose(); }




    @Override
    public void show() {

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


}
