package v5.game.kozloball.mvc.model;

import v5.game.kozloball.mvc.model.gameObjects.AnimalState;
import v5.game.kozloball.mvc.model.gameObjects.BallState;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameCollision implements ContactListener {

	private Logic _logic;

	// private State _state;

	public GameCollision(Logic logic) {
		_logic = logic;
		// _state = _logic._state;
	}

	@Override
	public void beginContact(Contact contact) {
		// System.out.println("beginContact:" + contact);
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();

		// try {
		detectContactAnimalToBall(bodyA, bodyB);
		detectContactAnimalToPlayer(bodyA, bodyB);
		detectContactBallToWall(bodyA, bodyB);
		detectContactAnimalToTarget(bodyA, bodyB);

		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	private void detectContactAnimalToTarget(Body bodyA, Body bodyB) {
		
		Body animal = null;
		
		for (int i = 0; i < _logic._state._animals.size(); i++) {
			Body a = _logic._state._animals.get(i);

			if ((bodyA == a) || (bodyB == a)) {
				animal = a;
				break;
			}
		}
		
		if (animal == null) {
			return;
		}
		
		AnimalState as = (AnimalState)animal.getUserData();
		if (as.getState() != AnimalState.S.PLAY) {
			return;
		}
		
		Body target = as.getTarget();
		
		if ((bodyA == target) || (bodyB == target)) {
			as.newTarget();
		}
	}

	private void detectContactAnimalToBall(Body bodyA, Body bodyB) {
		if ((bodyA == _logic._state._ball) || (bodyB == _logic._state._ball)) {

			if ((bodyA == _logic._state._player)
					|| (bodyB == _logic._state._player)) {
				System.out.println("Contact player to ball");
				_logic.initPlayerGrabBall();
				return;
			}

			for (int i = 0; i < _logic._state._animals.size(); i++) {
				Body animal = _logic._state._animals.get(i);

				if ((bodyA == animal) || (bodyB == animal)) {
					System.out.println("Contact animal to ball");
//					_logic._state._ball.applyForceToCenter(-100, 0, true);
					_logic._state.animalHitBall(-100, 0);
				}
			}
		}
	}

	private void detectContactAnimalToPlayer(Body bodyA, Body bodyB) {
		if ((bodyA == _logic._state._player)
				|| (bodyB == _logic._state._player)) {

			Body animal = null;

			for (int i = 0; i < _logic._state._animals.size(); i++) {
				Body a = _logic._state._animals.get(i);

				if ((bodyA == a) || (bodyB == a)) {
					System.out.println("Contact player to animal");
					animal = a;
					break;
				}
			}

			if (animal != null) {
				AnimalState state = (AnimalState) animal.getUserData();

				if (AnimalState.S.PLAY == state.getState()) {
					state.setState(AnimalState.S.PENALTY);
					_logic._state._countLives--;

				}
			}
		}
	}

	private void detectContactBallToWall(Body bodyA, Body bodyB) {

		Body ball = _logic.getState().getBall();
		Body wallP = ((BallState) ball.getUserData()).getWallOfPlayer();
		Body wallE = ((BallState) ball.getUserData()).getWallOfEnemy();

		if (((ball == bodyA) && (wallP == bodyB))
				|| ((ball == bodyB) && (wallP == bodyA))) {
			_logic._state._goalToPlayer++;
			_logic._state.setGoal();
		}
		if (((ball == bodyA) && (wallE == bodyB))
				|| ((ball == bodyB) && (wallE == bodyA))) {
			
			_logic._state._goalToEnemy++;
			_logic._state.setGoal();

		}
	}

	@Override
	public void endContact(Contact contact) {
		// System.out.println("endContact:" + contact);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// System.out.println("preContact:" + contact);
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// System.out.println("postContact:" + contact);
	}

}
