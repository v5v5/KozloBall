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
		_logic.start();
		fireChangedEvent(Event.SHOW_MENU);
	}

	public void hitBall(int speed) {
		_logic.hitBall(speed);
		fireChangedEvent(Event.HIT_BALL);
	}

	public void step() {
		_logic.step();
		fireChangedEvent(Event.STEP);

		int goal = _logic.isGoal();
		if (goal > 0) {
			fireChangedEvent(Event.GOAL_TO_ENEMY);
		} else if (goal < 0) {
			fireChangedEvent(Event.GOAL_TO_PLAYER);
		}

		if (_logic._state.getCountLives() <= 0) {
			fireChangedEvent(Event.GAME_OVER);
		}

		if ((_logic._state.getTime() / 1000) >= State.GAME_TIME) {
			fireChangedEvent(Event.GAME_OVER);
		}
	}

	public void movePlayer(Direction direction) {
		_logic.movePlayer(direction);
		fireChangedEvent(Event.MOVE_PLAYER);
	}

	public void stopPlayer() {
		_logic.stopPlayer();
		fireChangedEvent(Event.STOP_PLAYER);
	}

	public void reset() {
		_logic.reset();
		fireChangedEvent(Event.RESET_WORLD);
	}

}
