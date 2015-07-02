package v5.game.kozloball.mvc.model;

import v5.game.kozloball.mvc.model.ModelListener.Event;

public interface ModelInterface {

	public void addListener(ModelListener listener);

	public void removeListener(ModelListener listener);

	void fireChangedEvent(Event event);

}
