package fr.uniamu.ibdm.gsa_server.requests.JsonData;

public class ProvidersStatsData {

  private String providerName;
  private long providerStat;

  public ProvidersStatsData(String providerName, long providerStat) {
    this.providerName = providerName;
    this.providerStat = providerStat;
  }

  public ProvidersStatsData() {
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
