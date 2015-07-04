package v5.game.kozloball.mvc.model.gameObjects;

public class AnimalState {

	public enum State {
		PLAY, PENALTY, HOSPITAL
	};
	
	private long time;
	
	private State _state = State.PLAY;
	
	public void setState(State state) {
		_state = state;
		time = System.currentTimeMillis();
	}

	public State getState() {
		return _state;
	}
	
	public long getTime() {
		return time;
	}
	
}
