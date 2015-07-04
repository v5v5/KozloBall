package v5.game.kozloball.mvc.view;

import java.util.LinkedList;

import v5.game.kozloball.mvc.Utils;
import v5.game.kozloball.mvc.model.ModelListener.Event;
import v5.game.kozloball.mvc.model.State;
import v5.game.kozloball.mvc.model.gameObjects.AnimalState;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

public class View {

	Graphics _g;

	final static int R = (int) State.R;

	public void draw(LinkedList<Event> _events, State state) {
		clearView();
		drawField(state);
		drawPlayer(state);
		drawBall(state);
		drawScore(state);
		drawTime(state);
		drawCountLives(state);
		drawAnimals(state);
	}

	private void drawCountLives(State state) {
		int count = state.getCountLives();
		String text = "Lives: " + count;
		_g.drawString(text, (int) State.W_FIELD / 2 - 50,
				(int) -State.H_FIELD / 2 - 30);
	}

	private void drawAnimals(State state) {
		int x = 0;
		int y = 0;
		int r = R;

		Body animal;
		AnimalState aState;

		for (int i = 0; i < state.getAnimals().size(); i++) {

			animal = state.getAnimals().get(i);
			aState = (AnimalState) animal.getUserData();

			switch (aState.getState()) {
			case PLAY:
				x = (int) state.getAnimals().get(i).getPosition().x;
				y = (int) (state.getAnimals().get(i).getPosition().y);
				break;

			case PENALTY:
				x = (int) (State.W_FIELD / 2 - 2 * r * i);
				y = (int) (State.H_FIELD / 2 + 30);
				break;
			default:
				break;
			}

			_g.fillCircle(x, y, r, 5);
		}
	}

	private void drawTime(State state) {
		String time = Float.toString( state.getTime() / 1000);
		_g.drawString(time, (int) (-State.W_FIELD / 2),
				(int) (+State.H_FIELD / 2 + 30));
	}

	private void drawScore(State state) {
		_g.drawString(state.getScore(), (int) (-State.W_FIELD / 2),
				(int) (-State.H_FIELD / 2 - 30));
	}

	private void drawBall(State state) {
		int x = (int) state.getBall().getPosition().x;
		int y = (int) (state.getBall().getPosition().y);
		int r = R;

		_g.fillCircle(x, y, r, 2);
	}

	private void clearView() {
		_g.clearRect(-500, -500, 1000, 1000, 4);
	}

	private void drawPlayer(State state) {
		int x = (int) state.getPlayer().getPosition().x;
		int y = (int) (state.getPlayer().getPosition().y);
		int r = R;

		// System.out.println("x: " + x + "  y: " + y);
		_g.fillCircle(x, y, r, 0);
	}

	private void drawField(State state) {

		Body b = state.getField();
		if (null == b) {
			return;
		}

		Array<Fixture> a = b.getFixtureList();

		for (int i = 0; i < a.size; i++) {

			ChainShape s = Utils.as(ChainShape.class, a.get(i).getShape());
			if (null == s) {
				continue;
			}

			Vector2 v0 = new Vector2();
			Vector2 v1 = new Vector2();
			for (int j = 0; j < s.getVertexCount(); j++) {

				if (j > 0) {
					s.getVertex(j - 1, v0);
					s.getVertex(j, v1);

					// System.out.println("" + i + ", " + j + ": " + v0.x + ", "
					// + v0.y + ": " + v1.x + ", " + v1.y);

					_g.drawLine((int) v0.x, (int) v0.y, (int) v1.x, (int) v1.y,
							2);
				}
			}
		}
	}

	public void setGraphics(Graphics graphics) {
		_g = graphics;
	}

}
