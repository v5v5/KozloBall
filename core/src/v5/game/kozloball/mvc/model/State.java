package v5.game.kozloball.mvc.model;

import java.util.ArrayList;
import java.util.Random;

import v5.game.kozloball.mvc.model.gameObjects.AnimalState;
import v5.game.kozloball.mvc.model.gameObjects.BallState;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;

public class State {

	// static {
	// GdxNativesLoader.load();
	// }

	public final static float W_FIELD = 300;
	public final static float H_FIELD = 200;
	public final static float R = 5;
	public final static int GAME_TIME = 30;
	public final static int PENALTY_TIME = 5;

	World _world;

	Body _player;
	Body _field;
	Body _ball;
	Body _referee;
	ArrayList<Body> _animals = new ArrayList<Body>();

	JointDef _jointPlayerToBall;

	int _goalToEnemy;
	int _goalToPlayer;

	long _gameTimeStart;
	long _gameTimeCurrent;

	private Logic _logic;

	public int _countLives = 3;

	public State(Logic logic) {
		_logic = logic;
		_world = new World(new Vector2(0, 0), true);

		_world.setContactListener(new GameCollision(_logic));

		_player = createPlayer();
		_field = createField();
		_ball = createBall();
		_animals = createAnimals();
		// _referee = createReferee();
	}

	public Body getPlayer() {
		return _player;
	}

	public Body getField() {
		return _field;
	}

	Body createPlayer() {

		final float x = -50;
		final float y = 0;
		final float r = State.R;

		final float density = 0.0000001f;
		final float friction = 0.0f;
		final float restitution = 1.0f;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.linearDamping = 4.0f;
		Body body = _world.createBody(bodyDef);

		CircleShape shape = new CircleShape();
		shape.setRadius(r);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;

		Fixture fixture = body.createFixture(fixtureDef);

		shape.dispose();

		return body;
	}

	Body createField() {

		final float density = 0.0000001f;

		float w = W_FIELD;
		float h = H_FIELD;
		float widthGate = h / 3;
		float lenghtWall = (h - widthGate) / 2;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		// bodyDef.position.set(0, 0);

		Body body = _world.createBody(bodyDef);

		ChainShape shape;

		Vector2 v[] = new Vector2[4];

		v[0] = new Vector2(-w / 2, -h / 2 + lenghtWall);
		v[1] = new Vector2(-w / 2, -h / 2);
		v[2] = new Vector2(w / 2, -h / 2);
		v[3] = new Vector2(w / 2, -h / 2 + lenghtWall);

		shape = new ChainShape();
		shape.createChain(v);
		shape.setRadius(1);
		body.createFixture(shape, density);

		v[0] = new Vector2(-w / 2, h / 2 - lenghtWall);
		v[1] = new Vector2(-w / 2, h / 2);
		v[2] = new Vector2(w / 2, h / 2);
		v[3] = new Vector2(w / 2, h / 2 - lenghtWall);

		shape = new ChainShape();
		shape.createChain(v);
		shape.setRadius(1);
		body.createFixture(shape, density);

		shape.dispose();

		return body;
	}

	Body createBall() {
		final float x = 50;
		final float y = 0;
		final float r = State.R;

		final float density = 0.0000001f;
		final float friction = 0.0f;
		final float restitution = 0.0f;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		Body body = _world.createBody(bodyDef);

		CircleShape shape = new CircleShape();
		shape.setRadius(r);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;

		body.setUserData(new BallState(_world));

		Fixture fixture = body.createFixture(fixtureDef);

		shape.dispose();

		return body;
	}

	Body createReferee() {
		final float x = 0;
		final float y = 0;
		final float r = State.R;

		final float density = 0.0000001f;
		final float friction = 0.0f;
		final float restitution = 0.0f;

		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.DynamicBody;
		bd.position.set(x, y);
		Body b = _world.createBody(bd);

		CircleShape s = new CircleShape();
		s.setRadius(r);

		FixtureDef fd = new FixtureDef();
		fd.shape = s;
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;

		Fixture f = b.createFixture(fd);

		s.dispose();

		return b;
	}

	public Body getBall() {
		return _ball;
	}

	public String getScore() {
		return _goalToEnemy + ":" + _goalToPlayer;
	}

	public long getTime() {
		return (_gameTimeCurrent - _gameTimeStart);
	}

	private ArrayList<Body> createAnimals() {
		int count = 5;

		final float r = State.R;

		final float density = 0.0000001f;
		final float friction = 0.0f;
		final float restitution = 1.0f;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.linearDamping = 4.0f;

		CircleShape shape = new CircleShape();
		shape.setRadius(r);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;

		ArrayList<Body> bodies = new ArrayList<Body>();

		float x;
		float y;

		int randMinX = 0;// (int) (-W_FIELD / 2);
		int randMaxX = (int) (W_FIELD / 2);
		int randMinY = (int) (-H_FIELD / 2);
		int randMaxY = (int) (H_FIELD / 2);

		Body body;

		for (int i = 0; i < count; i++) {

			body = _world.createBody(bodyDef);
			body.setUserData(new AnimalState());

			bodies.add(body);

			x = _logic.rand.nextInt((int) (randMaxX - randMinX) + 1) + randMinX;
			y = _logic.rand.nextInt((int) (randMaxY - randMinY) + 1) + randMinY;

			bodies.get(i).setTransform(x, y, 0);

			Fixture fixture = bodies.get(i).createFixture(fixtureDef);
		}

		shape.dispose();
		return bodies;
	}

	public ArrayList<Body> getAnimals() {
		return _animals;
	}

	public int getCountLives() {
		return _countLives;
	}

}
