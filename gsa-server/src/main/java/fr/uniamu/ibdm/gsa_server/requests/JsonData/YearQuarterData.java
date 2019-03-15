package fr.uniamu.ibdm.gsa_server.requests.JsonData;

public class YearQuarterData {
  private String quarter;
  private Integer year;

  public YearQuarterData() {
  }

  public YearQuarterData(String quarter, Integer year) {
    this.quarter = quarter;
    this.year = year;
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((quarter == null) ? 0 : quarter.hashCode());
    result = prime * result + ((year == null) ? 0 : year.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    YearQuarterData other = (YearQuarterData) obj;
    if (quarter == null) {
      if (other.quarter != null) {
        return false;
      }
    } else if (!quarter.equals(other.quarter)) {
      return false;
    }
    if (year == null) {
      if (other.year != null) {
        return false;
      }
    } else if (!year.equals(other.year)) {
      return false;
    }
    return true;
  }
}
