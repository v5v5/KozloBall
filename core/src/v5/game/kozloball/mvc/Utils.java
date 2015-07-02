package v5.game.kozloball.mvc;

public class Utils {

	public static <T> T as(Class<T> clazz, Object o){
		if(clazz.isInstance(o)){
			return clazz.cast(o);
		}
		return null;
	}

}
