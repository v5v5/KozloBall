package v5.game.kozloball.mvc.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class State {

	// static {
	// GdxNativesLoader.load();
	// }

	World _world;

	Body _player;
	Body _field;
	Body _ball;
	Body _referee;
	Body[] _animal = new Body[0];

//	Box2DDebugRenderer debugRenederer;
//	
//	public Box2DDebugRenderer getDedugRenderer() {
//		return debugRenederer;
//	}
	
	public State() {
		_world = new World(new Vector2(0, -98), true);
	
//		debugRenederer = new Box2DDebugRenderer();

		_player = createPlayer();
		// _field = createField();
		// _ball = createBall();
		// _referee = createReferee();
	}

	public Body getPlayer() {
		return _player;
	}

	public Body getField() {
		return _field;
	}

	Body createPlayer() {

		final float x = 100;
		final float y = 0;
		final float r = 0.1f;

		final float density = 1.0f;
		final float friction = 0.75f;
		final float restitution = 0.1f;

		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.DynamicBody;
		bd.position.set(x, y);
		Body b = _world.createBody(bd);

		CircleShape s = new CircleShape();
		s.setRadius(r);

		FixtureDef fd = new FixtureDef();
		fd.shape = s;
		fd.density = density;
//		fd.friction = friction;
//		fd.restitution = restitution;

		Fixture f = b.createFixture(fd);

		s.dispose();

		return b;
	}

	Body createField() {

		final float density = 0.5f;

		float w = 300;
		float h = 200;
		float widthGate = h / 3;
		float lenghtWall = (h - widthGate) / 2;

		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.StaticBody;
		// bd.position.set(0, 0);

		Body body = _world.createBody(bd);

		ChainShape s;
		
		Vector2 v[] = new Vector2[4];

		v[0] = new Vector2(-w / 2, -h / 2 + lenghtWall);
		v[1] = new Vector2(-w / 2, -h / 2);
		v[2] = new Vector2(w / 2, -h / 2);
		v[3] = new Vector2(w / 2, -h / 2 + lenghtWall);

		s = new ChainShape();
		s.createChain(v);
		body.createFixture(s, density);

		v[0] = new Vector2(-w / 2, h / 2 - lenghtWall);
		v[1] = new Vector2(-w / 2, h / 2);
		v[2] = new Vector2(w / 2, h / 2);
		v[3] = new Vector2(w / 2, h / 2 - lenghtWall);

		s = new ChainShape();
		s.createChain(v);
		body.createFixture(s, density);

		s.dispose();

		return body;
	}

	Body createBall() {
		final float x = 10;
		final float y = 0;
		final float r = 10;

		final float density = 1f;
		final float friction = 0.5f;
		final float restitution = 0.3f;

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

		final float density = 0.5f;
		final float friction = 0.4f;
		final float restitution = 0.6f;

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

}
