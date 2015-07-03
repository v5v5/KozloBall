package v5.game.kozloball.mvc.model;

import v5.game.kozloball.mvc.model.T.Direction;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Array;

public class Logic {

	State _state;

	public Logic() {
		_state = new State(this);
	}

	public State getState() {
		return _state;
	}

	long millis = 0;

	public void step() {
		float d = (System.currentTimeMillis() - millis) / 1000.0f;
		// System.out.println("d:" + d);

		// _state._world.step(1/60, 6, 2);
		// _state._world.step(0.0001f, 6, 2);
		_state._world.step(d, 6, 2);

		createPlayerGrabBall();
		millis = System.currentTimeMillis();

		// System.out.println("count = " + _state._world.getBodyCount());
		// Array<Body> b = new Array<Body>();
		// _state._world.getBodies(b);
		// System.out.println("x = " + b.get(0).getPosition().x + " , y = " +
		// b.get(0).getPosition().y);

	}

	public void movePlayer(Direction direction) {
		float force = 500;
		Body body = _state._player;

		if (isJointPlayerToBall()) {
			body = _state._ball;
		}

		switch (direction) {
		case LEFT:
			body.applyForceToCenter(-force, 0, true);
			// body.setLinearVelocity(-force, 0f);
			break;
		case RIGHT:
			body.applyForceToCenter(force, 0, true);
			// body.setLinearVelocity(force, 0f);
			break;
		case UP:
			body.applyForceToCenter(0, -force, true);
			// body.setLinearVelocity(0, -force);
			break;
		case DOWN:
			// body.setLinearVelocity(0, force);
			body.applyForceToCenter(0, force, true);
			break;
		default:
			break;
		}
	}

	public void stopPlayer() {
		_state._player.setLinearVelocity(0, 0);
	}

	public void resetWorld() {
		_state._player.setLinearVelocity(0, 0);
		_state._ball.setLinearVelocity(0, 0);

		_state._player.setTransform(0, 0, 0);
		_state._ball.setTransform(0, 0, 0);
	}

	void initPlayerGrabBall() {
		_state._jointPlayerToBall = new DistanceJointDef();
		((DistanceJointDef) _state._jointPlayerToBall).initialize(
				_state._player, _state._ball, _state._player.getPosition(),
				_state._ball.getPosition());
		((DistanceJointDef) _state._jointPlayerToBall).length = 20;
		((DistanceJointDef) _state._jointPlayerToBall).frequencyHz = 0;
	}

	void createPlayerGrabBall() {
		// if (_state.isPlayerGrabBall) {
		if (isJointPlayerToBall()) {
			// _state.jointPlayerToBall = new RopeJointDef();
			// ((RopeJointDef)_state.jointPlayerToBall).maxLength = 20;
			// ((RopeJointDef)_state.jointPlayerToBall).collideConnected =
			// false;
			// _state.jointPlayerToBall.bodyA = _state._player;
			// _state.jointPlayerToBall.bodyB = _state._ball;
			// _state._world.createJoint(_state.jointPlayerToBall);

			// _state.jointPlayerToBall = new WeldJointDef();
			// ((WeldJointDef) _state.jointPlayerToBall).initialize(
			// _state._player, _state._ball, new Vector2(0, 0));
			// _state._world.createJoint(_state.jointPlayerToBall);

			_state._world.createJoint(_state._jointPlayerToBall);

			// _state.jointPlayerToBall = new PulleyJointDef();
			// ((PulleyJointDef) _state.jointPlayerToBall).initialize(
			// _state._player, _state._ball,
			// _state._player.getWorldCenter(),
			// _state._ball.getWorldCenter());
			// _state._world.createJoint(_state.jointPlayerToBall);

			// _state.jointPlayerToBall = new MouseJointDef();
			// _state.jointPlayerToBall.bodyA = _state._player;
			// _state.jointPlayerToBall.bodyB = _state._ball;
			// _state._world.createJoint(_state.jointPlayerToBall);

			// _state.jointPlayerToBall = new FrictionJointDef();
			// ((FrictionJointDef)_state.jointPlayerToBall).initialize(_state._player,
			// _state._ball, _state._ball.getWorldCenter());
			// _state._world.createJoint(_state.jointPlayerToBall);
		}
		// }
	}

	private boolean isJointPlayerToBall() {
		return (_state._jointPlayerToBall != null);
	}

	public void destroyJointFromPlayerToBall() {
		int count = _state._world.getJointCount();
		Array<Joint> joints = new Array<Joint>();
		_state._world.getJoints(joints);

		for (int i = 0; i < count; i++) {
			_state._world.destroyJoint(joints.get(i));
		}
		_state._jointPlayerToBall = null;
	}

}
