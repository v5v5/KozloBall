package v5.game.kozloball.mvc.model.gameObjects;

import v5.game.kozloball.mvc.model.State;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.World;

public class BallState {

	World _world;
	Body wallOfEnemy;
	Body wallOfPlayer;

	public BallState(World world) {
		_world = world;

		wallOfEnemy = createWallOfEnemy();
		wallOfPlayer = createWallOfPlayer();
	}

	private Body createWallOfPlayer() {
		final float density = 0.0000000f;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;

		Body body = _world.createBody(bodyDef);

		EdgeShape shape = new EdgeShape();

		float x0 = -State.W_FIELD / 2 - 2 * State.R - 1;
		float y0 = -State.H_FIELD / 2;
		float x1 = -State.W_FIELD / 2 - 2 * State.R - 1;
		float y1 = State.H_FIELD / 2;

		shape.set(x0, y0, x1, y1);

		// shape.setRadius(1);
		body.createFixture(shape, density);

		shape.dispose();

		return body;
	}

	private Body createWallOfEnemy() {
		final float density = 0.0000000f;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;

		Body body = _world.createBody(bodyDef);

		EdgeShape shape = new EdgeShape();

		float x0 = State.W_FIELD / 2 + 2 * State.R + 1;
		float y0 = -State.H_FIELD / 2;
		float x1 = State.W_FIELD / 2 + 2 * State.R + 1;
		float y1 = State.H_FIELD / 2;

		shape.set(x0, y0, x1, y1);

		// shape.setRadius(1);
		body.createFixture(shape, density);

		shape.dispose();

		return body;
	}

	public Body getWallOfEnemy() {
		return wallOfEnemy;
	}

	public Body getWallOfPlayer() {
		return wallOfPlayer;
	}
}
