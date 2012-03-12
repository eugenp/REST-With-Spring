package org.rest.client.template;

public enum ClientOperations {
    EQ, NEG_EQ;

    public boolean isNegated() {
	switch (this) {
	case EQ:
	    return false;
	case NEG_EQ:
	    return true;

	default:
	    return false;
	}
    }

}
