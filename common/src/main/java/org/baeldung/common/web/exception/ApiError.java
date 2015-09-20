package org.baeldung.common.web.exception;

public class ApiError {

    private int status;
    private String message;
    private String developerMessage;

    public ApiError(final int status, final String message, final String developerMessage) {
        super();

        this.status = status;
        this.message = message;
        this.developerMessage = developerMessage;
    }

    //

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(final String developerMessage) {
        this.developerMessage = developerMessage;
    }

    //

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((developerMessage == null) ? 0 : developerMessage.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + status;
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApiError other = (ApiError) obj;
        if (developerMessage == null) {
            if (other.developerMessage != null) {
                return false;
            }
        } else if (!developerMessage.equals(other.developerMessage)) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        return status == other.status;
    }

    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ApiError [status=").append(status).append(", message=").append(message).append(", developerMessage=").append(developerMessage).append("]");
        return builder.toString();
    }

}
