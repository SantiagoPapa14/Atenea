package app.model;

import java.time.LocalDate;

public interface SettlementApplicable {
    LocalDate calculateSettlementDate();
}
