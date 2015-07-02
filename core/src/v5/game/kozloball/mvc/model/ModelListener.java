package v5.game.kozloball.mvc.model;

public interface ModelListener {
	
	public enum Event {SHOW_MENU, HIT_BALL, EVENT1, GAME_OVER, STEP};

	void onChange(Event event, State state);

}
