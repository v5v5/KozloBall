package v5.game.kozloball.mvc.model;

import java.util.Random;

import v5.game.kozloball.mvc.model.T.Direction;
import v5.game.kozloball.mvc.model.gameObjects.AnimalState;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Array;

public class Logic {

	State _state;
	private Random rand = new Random();

	public Logic() {
		_state = new State(this);
	}

	public State getState() {
		return _state;
	}

	long gameTimePrev = 0;

	public void step() {
		if (0 == gameTimePrev) {
			gameTimePrev = System.currentTimeMillis() - 1;
		}

		float d = (System.currentTimeMillis() - gameTimePrev) / 1000.0f;
		// System.out.println("d:" + d);

		// _state._world.step(1/60, 6, 2);
		// _state._world.step(0.0001f, 6, 2);
		_state._world.step(d, 6, 2);

		createPlayerGrabBall();
		moveAnimals();

		gameTimePrev = System.currentTimeMillis();
		_state._gameTimeCurrent = gameTimePrev;

		// System.out.println("count = " + _state._world.getBodyCount());
		// Array<Body> b = new Array<Body>();
		// _state._world.getBodies(b);
		// System.out.println("x = " + b.get(0).getPosition().x + " , y = " +
		// b.get(0).getPosition().y);

	}

	private void moveAnimals() {

		float x;
		float y;

		// int randMinX = (int) (-State.W_FIELD / 2);
		// int randMaxX = (int) (State.W_FIELD / 2);
		// int randMinY = (int) (-State.H_FIELD / 2);
		// int randMaxY = (int) (State.H_FIELD / 2);
		//
		// int desireMove;

		Body animal;
		AnimalState aState;

		for (int i = 0; i < _state._animals.size(); i++) {

			animal = _state.getAnimals().get(i);
			aState = (AnimalState) animal.getUserData();

			switch (aState.getState()) {
			case PLAY:
				// desireMove = getRandom().nextInt(100);
				// if (desireMove > 5) {
				// continue;
				// }
				//
				// x = getRandom().nextInt(randMaxX - randMinX + 1) + randMinX;
				// y = getRandom().nextInt(randMaxY - randMinY + 1) + randMinY;
				// _state._animals.get(i).applyForceToCenter(x, y, true);

				aState.getTarget().setTransform(aState.getTargetPos(), 0);
				if (animal.getLinearVelocity().len() < 10) {
					_state._animals.get(i).applyForceToCenter(
							aState.getTarget().getPosition()
									.sub(animal.getPosition()).scl(5), true);
				}
				break;
			case PENALTY:
				if (_state._gameTimeCurrent - aState.getTime() >= State.PENALTY_TIME * 1000) {
					aState.setState(AnimalState.S.PLAY);
				}

				break;
			default:
				break;
			}

		}

		animalMoveTowardToBall();
	}

	private void animalMoveTowardToBall() {

		float dstMin = 0;
		float dst;
		int iAnimal = -1;

		Body animal;
		AnimalState aState;

		for (int i = 0; i < _state._animals.size(); i++) {

			animal = _state.getAnimals().get(i);
			aState = (AnimalState) animal.getUserData();

			switch (aState.getState()) {
			case PLAY:
				dst = Vector2.dst(_state._animals.get(i).getPosition().x,
						_state._animals.get(i).getPosition().y,
						_state._ball.getPosition().x,
						_state._ball.getPosition().y);

				if (0 == i) {
					dstMin = dst;
					continue;
				}

				if (dst < dstMin) {
					dstMin = dst;
					iAnimal = i;
				}
				break;

			default:
				break;
			}

		}

		if (iAnimal < 0) {
			return;
		}

		_state._animals.get(iAnimal).applyForceToCenter(
				_state._ball.getPosition()
						.sub(_state._animals.get(iAnimal).getPosition())
						.scl(0.00001f), true);
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

	public void reset() {
		destroyJointFromPlayerToBall();

		_state._player.setLinearVelocity(0, 0);
		_state._ball.setLinearVelocity(0, 0);

		_state._player.setTransform(-50, 0, 0);
		_state._ball.setTransform(50, 0, 0);

		float x;
		float y;

		int randMinX = 0;// (int) (-W_FIELD / 2);
		int randMaxX = (int) (State.W_FIELD / 2);
		int randMinY = (int) (-State.H_FIELD / 2);
		int randMaxY = (int) (State.H_FIELD / 2);

		Body animal;
		// AnimalState aState;
		for (int i = 0; i < _state._animals.size(); i++) {
			x = getRandom().nextInt(randMaxX - randMinX + 1) + randMinX;
			y = getRandom().nextInt(randMaxY - randMinY + 1) + randMinY;

			animal = _state._animals.get(i);
			// aState = (AnimalState) animal.getUserData();
			// aState.setState(AnimalState.State.PLAY);
			animal.setTransform(x, y, 0);
		}

	}

	void initPlayerGrabBall() {
		_state._jointPlayerToBall = new DistanceJointDef();
		((DistanceJointDef) _state._jointPlayerToBall).initialize(
				_state._player, _state._ball, _state._player.getPosition(),
				_state._ball.getPosition());
		((DistanceJointDef) _state._jointPlayerToBall).length = 2 * State.R;
		((DistanceJointDef) _state._jointPlayerToBall).frequencyHz = 0;

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

	void createPlayerGrabBall() {
		if (isJointPlayerToBall()) {
			_state._world.createJoint(_state._jointPlayerToBall);
		}
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

	public void hitBall(int speed) {
		destroyJointFromPlayerToBall();

		switch (speed) {
		case 1:
			_state._ball.setLinearDamping(2.0f);
			break;
		case 2:
			_state._ball.setLinearDamping(1.0f);
			break;
		case 3:
			_state._ball.setLinearDamping(0.5f);
			break;
		default:
			_state._ball.setLinearDamping(0.0f);
			break;
		}

		Vector2 dir = _state._ball.getPosition().sub(
				_state._player.getPosition());
		// Vector2 dir = _logic._state._player.getLinearVelocity();
		_state._ball.applyForceToCenter(dir, true);
		// _state._player.applyLinearImpulse(0, 0, 1, 0, false);
		System.out.println("hit");
	}

	public void start() {
		_state._gameTimeStart = System.currentTimeMillis();
		_state._countLives = 3;
		_state._goalToEnemy = 0;
		_state._goalToPlayer = 0;

		Body animal;
		AnimalState aState;
		for (int i = 0; i < _state._animals.size(); i++) {
			animal = _state._animals.get(i);

			aState = (AnimalState) animal.getUserData();
			aState.setState(AnimalState.S.PLAY);
		}
	}

	public Random getRandom() {
		return rand;
	}

}
