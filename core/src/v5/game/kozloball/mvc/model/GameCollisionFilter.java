package v5.game.kozloball.mvc.model;

import v5.game.kozloball.mvc.model.gameObjects.AnimalState;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class GameCollisionFilter implements ContactFilter {

	Logic _logic;

	public GameCollisionFilter(Logic logic) {
		_logic = logic;
	}

	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {

		Body a = fixtureA.getBody();
		Body b = fixtureB.getBody();

		Body ball = _logic.getState().getBall();
		Body player = _logic.getState().getPlayer();

		Body target;
		AnimalState aState;

		for (Body animal : _logic._state.getAnimals()) {

			aState = (AnimalState) animal.getUserData();
			target = aState.getTarget();

			if (((a == ball) && (b == target))
					|| ((b == ball) && (a == target))) {
				return false;
			}
			if (((a == player) && (b == target))
					|| ((b == player) && (a == target))) {
				return false;
			}
		}

		return true;
	}

}
