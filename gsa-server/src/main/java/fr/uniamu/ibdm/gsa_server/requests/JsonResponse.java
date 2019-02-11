package fr.uniamu.ibdm.gsa_server.requests;

import java.io.Serializable;

public class JsonResponse<T> implements Serializable {

  protected RequestStatus status;
  protected String error;
  protected T data;

  /**
   *  Create JsonResponse object and set up status and data.
   * @param status status of the response.
   * @param data data to be sent in the response.
   */
  public JsonResponse(RequestStatus status, T data) {
    this.status = status;
    this.data = data;
    this.error = null;
  }

  /**
   * Create JsonResponse and set up status,
   * data sent with the response will be empty javascript object.
   * @param status status of the response.
   */
  public JsonResponse(RequestStatus status) {
    this.status = status;
    this.error = null;
    this.data = null;
  }

  /**
   * Create JsonResponse object and initialize it with status and error,
   * the response will be empty javascript object.
   * @param status status of the response.
   * @param error error message.
   */
  public JsonResponse(RequestStatus status, String error) {
    this.status = status;
    this.error = error;
    this.data = null;
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
