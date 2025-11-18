package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {
    private final Invoice invoice;
    private final Map<String, Play> plays;
    private final StatementData statementData;

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
        this.statementData = new StatementData(invoice, plays);
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        return renderPlainText(statementData);
    }

    protected String renderPlainText(StatementData statementData) {
        final StringBuilder result =
                new StringBuilder("Statement for " + statementData.getCustomer() + System.lineSeparator());

        for (PerformanceData performanceData : statementData.getPerformances()) {
            result.append(String.format("  %s: %s (%s seats)%n",
                    performanceData.getName(), usd(performanceData.getAmount()),
                    performanceData.getAudience()));
        }

        result.append(String.format("Amount owed is %s%n",
                usd(statementData.totalAmount())));
        result.append(String.format("You earned %s credits%n", statementData.volumeCredits()));
        return result.toString();
    }

    protected String usd(int amount) {
        final NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(amount / (double) Constants.PERCENT_FACTOR);
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Map<String, Play> getPlays() {
        return plays;
    }

    protected StatementData getStatementData() {
        return statementData;
    }
}
