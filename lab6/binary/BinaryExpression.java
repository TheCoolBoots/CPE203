package binary;

import calculator.Bindings;
import calculator.Expression;

public class BinaryExpression implements Expression{

	private final Expression l;
	private final Expression r;
	private final String op;

	public BinaryExpression(final Expression l, final Expression r, String operator) {
		this.l = l;
		this.r = r;
		op = operator;
	}

	public String toString() {
		return "(" + l + " "+ op + " " + r + ")";
	}

	public double evaluate(final Bindings bindings) {
		
		switch(op){
		case("+"):
			return l.evaluate(bindings) + r.evaluate(bindings);
		case("-"):
			return l.evaluate(bindings) - r.evaluate(bindings);
		case("*"):
			return l.evaluate(bindings) * r.evaluate(bindings);
		case("/"):
			return l.evaluate(bindings) / r.evaluate(bindings);
		
		}
		return 0.0;
		
	}
}
