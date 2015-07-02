package v5.game.kozloball.mvc.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class Logic {

	State _state;

	public Logic() {
		_state = new State();
	}

	public State getState() {
		return _state;
	}

	public void step() {
//		_state._player.applyForceToCenter(10, 10, true);
		_state._world.step(1/60, 6, 2);

//		System.out.println("count = " + _state._world.getBodyCount());
//		Array<Body> b = new Array<Body>(); 
//		_state._world.getBodies(b);
//		System.out.println("x = " + b.get(0).getPosition().x + " , y = " + b.get(0).getPosition().y);
		
	}

}
