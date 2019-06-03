package utility;

import java.util.Map;

public interface Generator<T> {
	T next(Map<String,String> options);
}
