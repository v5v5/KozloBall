package v5.game.kozloball.mvc.view;

import java.util.LinkedList;

import v5.game.kozloball.mvc.Utils;
import v5.game.kozloball.mvc.model.ModelListener.Event;
import v5.game.kozloball.mvc.model.State;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

public class View {

	Graphics _g;

	public void draw(LinkedList<Event> _events, State state) {
//		drawField(state);
		drawPlayer(state);
	}

	private void drawPlayer(State state) {
		int x = (int) state.getPlayer().getPosition().x;
		int y = (int) state.getPlayer().getPosition().y;
		int r = 10;

		System.out.println("x: " + x + "  y: " + y);
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
