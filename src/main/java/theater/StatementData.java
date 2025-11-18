package theater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Container for the data needed to render a statement.
 */
public class StatementData {
    private final Invoice invoice;
    private final Map<String, Play> plays;
    private final List<PerformanceData> performances = new ArrayList<>();

    public StatementData(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
        for (Performance performance : invoice.getPerformances()) {
            performances.add(createPerformanceData(performance));
        }
    }

    public String getCustomer() {
        return invoice.getCustomer();
    }

    public List<PerformanceData> getPerformances() {
        return performances;
    }

    public int totalAmount() {
        int result = 0;
        for (PerformanceData performanceData : performances) {
            result += performanceData.getAmount();
        }
        return result;
    }

    public int volumeCredits() {
        int result = 0;
        for (PerformanceData performanceData : performances) {
            result += performanceData.getVolumeCredits();
        }
        return result;
    }

    private Play getPlay(Performance performance) {
        return plays.get(performance.getPlayID());
    }

    private PerformanceData createPerformanceData(Performance performance) {
        AbstractPerformanceCalculator calculator = AbstractPerformanceCalculator
                .createPerformanceCalculator(performance, getPlay(performance));
        return new PerformanceData(performance, getPlay(performance), calculator);
    }
}
