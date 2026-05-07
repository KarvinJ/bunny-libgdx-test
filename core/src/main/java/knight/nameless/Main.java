package knight.nameless;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

    private final int SCREEN_WIDTH = 1024;
    private final int SCREEN_HEIGHT = 768;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Array<Bunny> bunnies;
    private Texture bunnyTexture;
    private Texture fontTexture;

    @Override
    public void create() {

        batch = new SpriteBatch();

        fontTexture = new Texture("fonts/test.png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("fonts/test.fnt"), new TextureRegion(fontTexture));
        font.getData().scale(1f);

        bunnyTexture = new Texture("wabbit_alpha.png");
        bunnies = new Array<>();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private void addBunnies() {

        for (int i = 0; i <= 100; i++) {

            var touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            var bunny = new Bunny(bunnyTexture, touchPos.x, touchPos.y);
            bunny.setScreenBounds(new Vector2(SCREEN_WIDTH, SCREEN_HEIGHT));

            bunnies.add(bunny);
        }
    }

    private void update(float deltaTime) {

        camera.update();

        if (Gdx.input.isTouched())
            addBunnies();

        for (var bunny : bunnies)
            bunny.update(deltaTime);
    }

    @Override
    public void render() {

        ScreenUtils.clear(Color.BLACK);

        update(Gdx.graphics.getDeltaTime());

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (var bunny : bunnies)
            bunny.draw(batch);

        font.draw(batch, String.format("FPS: " + Gdx.graphics.getFramesPerSecond()), 20, SCREEN_HEIGHT - 20);
        font.draw(batch, String.format("Count: " + bunnies.size), 20, SCREEN_HEIGHT - 80);

        batch.end();
    }

    @Override
    public void dispose() {

        batch.dispose();
        font.dispose();
        fontTexture.dispose();
        bunnies.forEach(Bunny::dispose);
    }
}
