package v5.game.kozloball.mvc.model;

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

//	static {
//		GdxNativesLoader.load();
//	}

	World _world;

	Body _player;
	Body _field;
	Body _ball;
	Body _referee;
	Body[] _animal = new Body[0];
	
	JointDef _jointPlayerToBall;

	private Logic _logic;

	public State(Logic logic) {
		_logic = logic;
		_world = new World(new Vector2(0, 0), true);
		
		_world.setContactListener(new GameCollision(_logic));
	
		_player = createPlayer();
		 _field = createField();
		 _ball = createBall();
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
		final float r = 10f;

		final float density = 0.0000001f;
		final float friction = 0.0f;
		final float restitution = 1.0f;

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

		Fixture fixture = body.createFixture(fixtureDef);
		
		shape.dispose();

		return body;
	}
	
	Body createField() {

		final float density = 0.0000001f;

		float w = 300;
		float h = 200;
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
		final float r = 10;

		final float density = 0.0000001f;
		final float friction = 0.0f;
		final float restitution = 1.0f;

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

	Body createReferee() {
		final float x = 0;
		final float y = 0;
		final float r = 10;

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

}
