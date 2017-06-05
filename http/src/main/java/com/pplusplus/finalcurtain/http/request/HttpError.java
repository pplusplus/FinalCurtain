package com.pplusplus.finalcurtain.http.request;

/**
 * Created by Pat Powell on 02/06/2017.
 */

public class HttpError {

    public static final HttpError MALFORMED_URL = new HttpError("The url was not well formed");
    public static final HttpError CONNECTION_ERROR = new HttpError("There was a problem with the network connection");
    public static final HttpError SERVER_ERROR = new HttpError("The server returned a bad error code");
    public static final HttpError TIMEOUT_ERROR = new HttpError("The server took too long to reply");
    public static final HttpError FAILED = new HttpError("The request failed");

    public static final int NO_STATUS = -1;

    private String message;
    private int status = NO_STATUS;

    private HttpError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
