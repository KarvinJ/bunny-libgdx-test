package knight.nameless;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bunny {

    private final Vector2 position;
    private final Vector2 velocity;
    private final Texture texture;
    private final Vector2 screenBounds;

    Bunny(Texture texture, float positionX, float positionY, Vector2 screenBounds) {
        this.texture = texture;
        position = new Vector2(positionX, positionY);
        velocity = new Vector2(MathUtils.random(-200, 200), MathUtils.random(-200, 200));
        this.screenBounds = screenBounds;
    }

    public void update(float deltaTime) {

        if (position.x <= 0 || position.x >= screenBounds.x)
            velocity.x = -velocity.x;

        if (position.y <= 0 || position.y >= screenBounds.y)
            velocity.y = -velocity.y;

        position.mulAdd(velocity, deltaTime);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }
}
