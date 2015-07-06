package v5.game.kozloball.mvc.model.gameObjects;

import v5.game.kozloball.mvc.model.State;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class AnimalState {

	public enum S {
		PLAY, PENALTY, HOSPITAL
	};

	private long time;

	private AnimalState.S _s = AnimalState.S.PLAY;
	Body _target;
	
	private Vector2 _targetPos = new Vector2();

	private State _state;

	public AnimalState(State state) {
		_state = state;
		_target = createTarget();
	}

	private Body createTarget() {
		final float r = State.R;

		final float density = 0.0000001f;
		final float friction = 0.0f;
		final float restitution = 1.0f;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;

		World world = _state.getWorld();
		Body body = world.createBody(bodyDef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(r);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;

		Fixture fixture = body.createFixture(fixtureDef);

		int randMinX = 0;// (int) (-State.W_FIELD / 2);
		int randMaxX = (int) (State.W_FIELD / 2);
		int randMinY = (int) (-State.H_FIELD / 2);
		int randMaxY = (int) (State.H_FIELD / 2);

		float x = _state.getLogic().getRandom().nextInt(randMaxX - randMinX + 1)
				+ randMinX;
		float y = _state.getLogic().getRandom().nextInt(randMaxY - randMinY + 1)
				+ randMinY;
		
		getTargetPos().set(x, y);

//		body.setTransform(x, y, 0);

		shape.dispose();

		return body;
	}

	public void newTarget() {
		int randMinX = 0;// (int) (-State.W_FIELD / 2);
		int randMaxX = (int) (State.W_FIELD / 2);
		int randMinY = (int) (-State.H_FIELD / 2);
		int randMaxY = (int) (State.H_FIELD / 2);

		float x = _state.getLogic().getRandom().nextInt(randMaxX - randMinX + 1)
				+ randMinX;
		float y = _state.getLogic().getRandom().nextInt(randMaxY - randMinY + 1)
				+ randMinY;
		
		_targetPos.set(x, y);
	}

	public void setState(S state) {
		_s = state;
		time = System.currentTimeMillis();
	}

	public S getState() {
		return _s;
	}

	public long getTime() {
		return time;
	}

	public Body getTarget() {
		return _target;
	}

	public Vector2 getTargetPos() {
		return _targetPos;
	}


}
