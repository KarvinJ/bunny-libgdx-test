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
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Main extends ApplicationAdapter {

    private final int SCREEN_WIDTH = 1360;
    private final int SCREEN_HEIGHT = 768;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private ExtendViewport viewport;
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
        camera.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0);
        viewport = new ExtendViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void addBunnies() {

        for (int i = 0; i <= 100; i++) {

            var touchPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            var bunny = new Bunny(
                bunnyTexture,
                touchPos.x,
                touchPos.y,
                new Vector2(SCREEN_WIDTH, SCREEN_HEIGHT)
            );

            bunnies.add(bunny);
        }
    }

    private void update(float deltaTime) {

        if (Gdx.input.isTouched())
            addBunnies();

        for (var bunny : bunnies)
            bunny.update(deltaTime);
    }

    @Override
    public void render() {

        ScreenUtils.clear(Color.BLACK);

        update(Gdx.graphics.getDeltaTime());

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        for (var bunny : bunnies)
            bunny.draw(batch);

        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, SCREEN_HEIGHT - 20);
        font.draw(batch, "Count: " + bunnies.size, 20, SCREEN_HEIGHT - 80);

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
