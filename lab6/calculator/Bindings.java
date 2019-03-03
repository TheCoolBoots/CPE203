
package calculator;

import exceptions.UnboundIdentifierException;

public interface Bindings {
	void addBinding(String id, double value);

	double lookupBinding(String id) throws UnboundIdentifierException;
}
