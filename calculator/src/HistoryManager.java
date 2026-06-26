import java.util.ArrayList;

public class HistoryManager {
    private ArrayList<String> records;

    public HistoryManager() {
        records = new ArrayList<>();
    }

    public void addRecord(String record) {
        records.add(record);
    }

    public boolean isEmpty() {
        return records.isEmpty();
    }

    public ArrayList<String> getAllRecords() {
        return records;
    }

    public String toPlainString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < records.size(); i++) {
            sb.append(i + 1).append(". ").append(records.get(i)).append("\n");
        }
        return sb.toString();
    }
}
