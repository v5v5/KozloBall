package v5.game.kozloball.mvc.model;

public interface ModelListener {

	public enum Event {
		SHOW_MENU, HIT_BALL, EVENT1, GAME_OVER, STEP, MOVE_PLAYER, STOP_PLAYER, RESET_WORLD, GOAL
	};

	void onChange(Event event, State state);

}
