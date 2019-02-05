package fr.uniamu.ibdm.gsa_server.requests;

import java.io.Serializable;

public class JSONResponse<T> implements Serializable {

    protected RequestStatus status;
    protected String error;
    protected T data;

    public JSONResponse(RequestStatus status, T data) {
        this.status = status;
        this.data = data;
        this.error = null;
    }

    public JSONResponse(RequestStatus status) {
        this.status = status;
        this.error = null;
        this.data = null;
    }

    public JSONResponse(RequestStatus status, String error, T data) {
        this.status = status;
        this.error = error;
        this.data = data;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
