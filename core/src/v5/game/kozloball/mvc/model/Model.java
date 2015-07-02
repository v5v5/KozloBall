package v5.game.kozloball.mvc.model;

import java.util.ArrayList;
import java.util.List;

import v5.game.kozloball.mvc.model.ModelListener.Event;

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
		_logic._state._player.applyForceToCenter(1, 1, true);
//		_logic._state._player.applyLinearImpulse(0, 0, 1, 0, false);
//		_logic._state._player.setTransform(1, 10, 3);
		System.out.println("hit");
		fireChangedEvent(Event.HIT_BALL);
	}

	public void step() {
		_logic.step();
		fireChangedEvent(Event.STEP);
	}
	
	

}
