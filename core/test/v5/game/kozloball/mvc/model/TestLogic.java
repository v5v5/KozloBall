package v5.game.kozloball.mvc.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import v5.game.kozloball.mvc.model.T.Direction;

import com.badlogic.gdx.physics.box2d.Body;

public class TestLogic {

	private Logic _logic;
	private State _state;
	private Body _player;

	@Before
	public void setup() {
		_logic = new Logic();
		_state = _logic._state;
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

	@Test
	public void isGoal() {
		Body ball = _state.getBall();
		ball.setTransform(State.W_FIELD / 2 + State.R + 1, State.H_FIELD, 0);
		assertTrue(0 < _logic.isGoal());

		ball.setTransform(-State.W_FIELD / 2 - State.R - 1, State.H_FIELD, 0);
		assertTrue(0 > _logic.isGoal());

		ball.setTransform(0, 0, 0);
		assertTrue(0 == _logic.isGoal());
	}

}
