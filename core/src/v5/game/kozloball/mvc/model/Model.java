package v5.game.kozloball.mvc.model;

import java.util.ArrayList;
import java.util.List;

import v5.game.kozloball.mvc.model.ModelListener.Event;
import v5.game.kozloball.mvc.model.T.Direction;

import com.badlogic.gdx.math.Vector2;

public class Model implements ModelInterface {

	List<ModelListener> _listeners = new ArrayList<ModelListener>();
	private Logic _logic = new Logic();
	
	public Logic getLogic() {
		return _logic;
	}	

	@Override
	public void addListener(ModelListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void removeListener(ModelListener listener) {
		_listeners.remove(listener);
	}

	@Override
	public void fireChangedEvent(Event event) {
		for (ModelListener modelListener : _listeners) {
			modelListener.onChange(event, _logic.getState());
		}
	}

	public void start() {
		fireChangedEvent(Event.SHOW_MENU);
	}

	public void hitBall() {
		_logic.destroyJointFromPlayerToBall();
		
		Vector2 v =  _logic._state._player.getLinearVelocity();
		_logic._state._ball.applyForceToCenter(v, true);
//		_logic._state._player.applyLinearImpulse(0, 0, 1, 0, false);
//		_logic._state._player.setTransform(1, 10, 3);
		System.out.println("hit");
		fireChangedEvent(Event.HIT_BALL);
	}

	public void step() {
		_logic.step();
		fireChangedEvent(Event.STEP);
	}

	public void movePlayer(Direction direction) {
		_logic.movePlayer(direction);
		fireChangedEvent(Event.MOVE_PLAYER);
	}

	public void stopPlayer() {
		_logic.stopPlayer();
		fireChangedEvent(Event.STOP_PLAYER);
	}

	public void resetWorld() {
		_logic.resetWorld();
		fireChangedEvent(Event.RESET_WORLD);
	}
	
	

}
