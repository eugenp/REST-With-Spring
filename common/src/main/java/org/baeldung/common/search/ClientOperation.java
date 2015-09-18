package org.baeldung.common.search;

public enum ClientOperation {
    EQ, NEG_EQ, CONTAINS, NEG_CONTAINS, STARTS_WITH, NEG_STARTS_WITH, ENDS_WITH, NEG_ENDS_WITH;

    public boolean isNegated() {
        switch (this) {
        case EQ:
            return false;
        case NEG_EQ:
            return true;

        case CONTAINS:
            return false;
        case STARTS_WITH:
            return false;
        case ENDS_WITH:
            return false;

        case NEG_CONTAINS:
            return true;
        case NEG_STARTS_WITH:
            return true;
        case NEG_ENDS_WITH:
            return true;

        default:
            return false;
        }
    }

}
