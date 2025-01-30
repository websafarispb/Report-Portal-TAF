package com.reportportal.ta.exception;

public class ReportPortalRuntimeException extends RuntimeException {

    public ReportPortalRuntimeException() {
        super();
    }

    public ReportPortalRuntimeException(String message) {
        super(message);
    }

    public ReportPortalRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportPortalRuntimeException(Throwable cause) {
        super(cause);
    }

    public void throwIfNull(Object object) {
        if (object == null) {
            throw this;
        }
    }

    public void throwIfTrue(boolean condition) {
        if (condition) {
            throw this;
        }
    }

    public void throwIfFalse(boolean condition) {
        if (!condition) {
            throw this;
        }
    }
}
