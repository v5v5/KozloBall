package v5.game.kozloball.mvc.controller;

import java.util.LinkedList;

import v5.game.kozloball.mvc.model.Model;
import v5.game.kozloball.mvc.model.ModelListener;
import v5.game.kozloball.mvc.model.State;
import v5.game.kozloball.mvc.model.T.Direction;
import v5.game.kozloball.mvc.view.View;

public class Controller implements ModelListener  {

	private Model _model;
	private View _view;
		
	private LinkedList<Event> _events = new LinkedList<Event>();
	
	public Controller() {
		_model = new Model();
		_model.addListener(this);
	}

	public void setView(View view) {
		_view = view;
	}

	@Override
	public void onChange(Event event, State state) {
		_events.	add(event);
		
		switch (event) {
		case SHOW_MENU:			
			break;
		case HIT_BALL:			
			break;
		case STEP:			
			break;
		case MOVE_PLAYER:			
			break;
		case EVENT1:			
			break;
		case GAME_OVER:			
			break;

		default:
			break;
		}
		
		if (null != _view) {
			_view.draw(_events, state);
		}
	}

	public void movePlayer(Direction direction) {
		_model.movePlayer(direction);
	}

	public void hitBall() {
		_model.hitBall();
		_model.stopPlayer();
	}

	public void start() {
		_model.start();
	}

	public void repaintView() {
		_model.start();
	}

	public void step() {
		_model.step();
	}

	public Model getModel() {
		return _model;
	}

	public void resetWorld() {
		_model.resetWorld();
	}

}
