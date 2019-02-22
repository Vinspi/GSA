package fr.uniamu.ibdm.gsa_server.requests.forms;

public class AddProductForm {

  private String targetName;
  private String sourceName;

  public AddProductForm() {
  }

  public String getTargetName() {
    return targetName;
  }

  public void setTargetName(String targetName) {
    this.targetName = targetName;
  }

  public String getSourceName() {
    return sourceName;
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }
}