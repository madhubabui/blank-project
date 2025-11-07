package com.college.ppp;

import java.util.List;

public record Project(int id,
                      String title,
                      String description,
                      List&lt;Integer&gt; partnerIds,
                      List&lt;Contract&gt; contracts,
                      List&lt;Payment&gt; payments,
                      List&lt;KPI&gt; kpis) {
    @Override
    public String toString() {
        return "Project{id=" + id + ", title='" + title + "', partners=" + partnerIds.size() + ", contracts=" + contracts.size() + "}";
    }
}