package knight.nameless;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    SpriteBatch batch;
    BitmapFont font;
    OrthographicCamera camera;
    Array<Bunny> bunnies;
    Texture bunnyTexture;

    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();

        bunnyTexture = new Texture("wabbit_alpha.png");
        bunnies = new Array<>();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 768);
    }

    private void addBunnies() {

        for (int i = 0; i <= 100; i++) {

            var touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            var bunny = new Bunny(bunnyTexture);

            bunny.setPosition(new Vector2(touchPos.x, touchPos.y));
            bunny.setVelocity(new Vector2(MathUtils.random(-200, 200), MathUtils.random(-200, 200)));
            bunny.setBounds(new Vector2(1024, 768));

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

        ScreenUtils.clear(0, 1, 1, 1);

        update(Gdx.graphics.getDeltaTime());

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (var bunny : bunnies)
            bunny.draw(batch);

        font.draw(batch, String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()), 20, 20);
        font.draw(batch, String.format("Count: %d", bunnies.size), 20, 50);

        batch.end();
    }

    @Override
    public void dispose() {

        batch.dispose();
        bunnies.forEach(Bunny::dispose);
    }
}
