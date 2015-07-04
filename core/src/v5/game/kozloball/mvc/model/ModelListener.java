package v5.game.kozloball.mvc.model;

public interface ModelListener {

	public enum Event {
		SHOW_MENU, HIT_BALL, EVENT1, GAME_OVER, STEP, MOVE_PLAYER, STOP_PLAYER, RESET_WORLD, GOAL_TO_ENEMY, GOAL_TO_PLAYER
	};

	void onChange(Event event, State state);

}
