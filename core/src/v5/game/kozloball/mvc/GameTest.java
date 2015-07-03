package v5.game.kozloball.mvc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import v5.game.kozloball.mvc.controller.Controller;
import v5.game.kozloball.mvc.model.T.Direction;
import v5.game.kozloball.mvc.view.Graphics;
import v5.game.kozloball.mvc.view.View;

public class GameTest {

	protected static final Color[] COLORS = { Color.gray, Color.black,
			Color.magenta, Color.green, Color.blue, Color.cyan };

	public static void main(String[] args) {

		// create controller
		final Controller controller = new Controller();

		// create ui
		// JFrame frame = new JFrame("KozloBall");
		JFrame frame = new JFrame("KozloBall") {
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(java.awt.Graphics g) {
				controller.repaintView();
			}
		};
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// create input data stream
		frame.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(final KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					controller.movePlayer(Direction.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					controller.movePlayer(Direction.RIGHT);
					break;
				case KeyEvent.VK_DOWN:
					controller.movePlayer(Direction.DOWN);
					break;
				case KeyEvent.VK_UP:
					controller.movePlayer(Direction.UP);
					break;
				case KeyEvent.VK_SPACE:
					controller.hitBall();
					break;
				case KeyEvent.VK_F12:
					controller.resetWorld();
					break;
				default:
					break;
				}
			}

		});

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					controller.step();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();

		// create output data stream
		final Graphics2D graphics = (Graphics2D) frame.getGraphics();

		final int ORIGIN_X = frame.getWidth() / 2;
		final int ORIGIN_Y = frame.getHeight() / 2;

		View view = new View();
		view.setGraphics(new Graphics() {

			@Override
			public void fillCircle(int x, int y, int r, int colorIndex) {
				

				graphics.setColor(COLORS[colorIndex]);
				graphics.fillOval(ORIGIN_X + x - r, ORIGIN_Y + y - r,
						2 * r, 2 * r);
			}

			@Override
			public void drawLine(int x0, int y0, int x1, int y1, int colorIndex) {
				graphics.setColor(COLORS[colorIndex]);
				graphics.drawLine(ORIGIN_X + x0, ORIGIN_Y + y0, ORIGIN_X + x1,
						ORIGIN_Y + y1);
			}

			@Override
			public void clearRect(int x, int y, int width, int height,
					int colorIndex) {
				graphics.clearRect(ORIGIN_X + x, ORIGIN_X + y, width, height);
			}

		});
		controller.setView(view);

		// start controller
		controller.start();
	}

}
