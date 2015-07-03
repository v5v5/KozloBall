package v5.game.kozloball.mvc.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameCollision implements ContactListener {

	private Logic _logic;

	public GameCollision(Logic logic) {
		_logic = logic;
	}

	@Override
	public void beginContact(Contact contact) {
		// System.out.println("beginContact:" + contact);
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();

		if (((bodyA == _logic._state._player) && (bodyB == _logic._state._ball))
				|| ((bodyB == _logic._state._player) && (bodyA == _logic._state._ball))) {
			System.out.println("Contact player to ball");

			_logic.initPlayerGrabBall();
			
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
