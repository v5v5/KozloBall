package v5.game.kozloball.mvc.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import v5.game.kozloball.mvc.model.T.Direction;

import com.badlogic.gdx.physics.box2d.Body;


public class TestLogic {
	
	private Logic _logic;
	private Body _player;

	@Before
	public void setup() {
		_logic = new Logic();
		_player = _logic.getState().getPlayer();
	}

	@Test
	public void createState() {
		Logic l = new Logic();
		State s = new State(l);
		System.out.println(s);
	}

	@Test
	public void createLogic() {
		Logic l = new Logic();
		System.out.println(l);
	}
	
	@Test
	public void createModel() {
		Model m = new Model();
		System.out.println(m);
	}
	
	@Test
	public void movePlayerLeft() {		
		float x0 = _player.getPosition().x;
		float y0 = _player.getPosition().y;
		
		_logic.movePlayer(Direction.LEFT);
		_logic.step();		

		float x1 = _player.getPosition().x;
		float y1 = _player.getPosition().y;

		assertTrue(y1 == y0);
		assertTrue(x1 < x0);		
	}

	@Test
	public void movePlayerRight() {		
		float x0 = _player.getPosition().x;
		float y0 = _player.getPosition().y;
		
		_logic.movePlayer(Direction.RIGHT);
		_logic.step();		

		float x1 = _player.getPosition().x;
		float y1 = _player.getPosition().y;

		assertTrue(x1 > x0);
		assertTrue(y1 == y0);		
	}

	@Test
	public void movePlayerUp() {
		float x0 = _player.getPosition().x;
		float y0 = _player.getPosition().y;
		
		_logic.movePlayer(Direction.UP);
		_logic.step();		

		float x1 = _player.getPosition().x;
		float y1 = _player.getPosition().y;

		assertTrue(y1 < y0);
		assertTrue(x1 == x0);		
	}

	@Test
	public void movePlayerDown() {
		float x0 = _player.getPosition().x;
		float y0 = _player.getPosition().y;
		
		_logic.movePlayer(Direction.DOWN);
		_logic.step();		

		float x1 = _player.getPosition().x;
		float y1 = _player.getPosition().y;

		assertTrue(y1 > y0);
		assertTrue(x1 == x0);		
	}

}
