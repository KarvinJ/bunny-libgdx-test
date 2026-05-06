package knight.nameless;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bunny {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 bounds;
    private final Texture texture;

    Bunny(Texture texture) {
        this.texture = texture;
        position = Vector2.Zero;
        velocity = Vector2.Zero;
        bounds = Vector2.Zero;
    }


    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setBounds(Vector2 bounds) {
        this.bounds = bounds;
    }

    public void update(float deltaTime) {

        position.mulAdd(velocity, deltaTime);

        if (position.x <= 0 || position.x >= bounds.x)
            velocity.x = -velocity.x;

        if (position.y <= 0 || position.y >= bounds.y)
            velocity.y = -velocity.y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }
}
