package v5.game.kozloball.mvc.view;

public interface Graphics {

	void clearRect(int x, int y, int width, int height, int colorIndex);
	void fillCircle(int x, int y, int r, int colorIndex);
	void drawLine(int x0, int y0, int x1, int y1, int colorIndex);
	void drawString(String text, int x, int y);

}
