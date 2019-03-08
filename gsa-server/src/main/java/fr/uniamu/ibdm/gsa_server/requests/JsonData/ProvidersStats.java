package fr.uniamu.ibdm.gsa_server.requests.JsonData;

public class ProvidersStats {

  private String providerName;
  private long providerStat;

  public ProvidersStats(String providerName, long providerStat) {
    this.providerName = providerName;
    this.providerStat = providerStat;
  }

  public ProvidersStats() {
  }

  public String getProviderName() {
    return providerName;
  }

  public void setProviderName(String providerName) {
    this.providerName = providerName;
  }

  public long getProviderStat() {
    return providerStat;
  }

  public void setProviderStat(long providerStat) {
    this.providerStat = providerStat;
  }
}
