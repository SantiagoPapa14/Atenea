package app.model;

import java.util.*;
import java.time.*;
import java.math.*;
import java.sql.*;

import app.model.*;

public class SQLiteRepository implements TradeRepository {
    private Connection conn = null;

    public SQLiteRepository(String fileName) {
        connect(fileName);
        initializeDatabase();
    }

    private void connect(String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    private void initializeDatabase() {
        try (Statement stmt = conn.createStatement()) {
            // Base table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS trade (
                            id INTEGER PRIMARY KEY,
                            tradeDate DATE NOT NULL,
                            counterparty VARCHAR(255) NOT NULL,
                            tradeType VARCHAR(50) NOT NULL,
                            status VARCHAR(50) NOT NULL
                        )
                    """);

            // Stock-specific table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS stock_trade (
                            id INTEGER PRIMARY KEY,
                            ticker VARCHAR(50) NOT NULL,
                            quantity INTEGER NOT NULL,
                            price REAL NOT NULL,
                            exchange VARCHAR(50) NOT NULL,
                            currency VARCHAR(10) NOT NULL,
                            FOREIGN KEY (id) REFERENCES trade(id) ON DELETE CASCADE
                        )
                    """);

            // FX-specific table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS fx_trade (
                            id INTEGER PRIMARY KEY,
                            fxProductType VARCHAR(50) NOT NULL,
                            buyCurrency VARCHAR(10) NOT NULL,
                            sellCurrency VARCHAR(10) NOT NULL,
                            buyAmount NUMERIC(19,4) NOT NULL,
                            sellAmount NUMERIC(19,4) NOT NULL,
                            rate NUMERIC(19,8) NOT NULL,
                            valueDate DATE NOT NULL,
                            FOREIGN KEY (id) REFERENCES trade(id) ON DELETE CASCADE
                        )
                    """);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    @Override
    public void save(Trade trade) {
        try {
            conn.setAutoCommit(false);

            // Insert base trade data
            String baseSql = """
                        INSERT OR REPLACE INTO trade (id, tradeDate, counterparty, tradeType, status)
                        VALUES (?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement pstmt = conn.prepareStatement(baseSql)) {
                pstmt.setLong(1, trade.getId());
                pstmt.setDate(2, java.sql.Date.valueOf(trade.getTradeDate()));
                pstmt.setString(3, trade.getCounterparty());
                pstmt.setString(4, TradeType.fromTrade(trade).name());
                pstmt.setString(5, trade.getStatus().name());
                pstmt.executeUpdate();
            }

            // Insert type-specific data
            if (trade instanceof StockTrade stockTrade) {
                saveStockTrade(stockTrade);
            } else if (trade instanceof FxTrade fxTrade) {
                saveFxTrade(fxTrade);
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Failed to save trade", e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveStockTrade(StockTrade trade) throws SQLException {
        String sql = """
                    INSERT OR REPLACE INTO stock_trade (id, ticker, quantity, price, exchange, currency)
                    VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, trade.getId());
            pstmt.setString(2, trade.getTicker());
            pstmt.setInt(3, trade.getQuantity());
            pstmt.setDouble(4, trade.getPrice());
            pstmt.setString(5, trade.getExchange());
            pstmt.setString(6, trade.getCurrency());
            pstmt.executeUpdate();
        }
    }

    private void saveFxTrade(FxTrade trade) throws SQLException {
        String sql = """
                    INSERT OR REPLACE INTO fx_trade
                    (id, fxProductType, buyCurrency, sellCurrency, buyAmount, sellAmount, rate, valueDate)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, trade.getId());
            pstmt.setString(2, trade.getFxProductType().name());
            pstmt.setString(3, trade.getBuyCurrency());
            pstmt.setString(4, trade.getSellCurrency());
            pstmt.setBigDecimal(5, trade.getBuyAmount());
            pstmt.setBigDecimal(6, trade.getSellAmount());
            pstmt.setBigDecimal(7, trade.getRate());
            pstmt.setDate(8, java.sql.Date.valueOf(trade.getValueDate()));
            pstmt.executeUpdate();
        }
    }

    @Override
    public Trade findById(long id) {
        String sql = """
                    SELECT * FROM trade WHERE id = ?
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                TradeType tradeType = TradeType.valueOf(rs.getString("tradeType"));

                return switch (tradeType) {
                    case STOCK -> loadStockTrade(rs);
                    case FX_SPOT, FX_FORWARD, FX_SWAP -> loadFxTrade(rs);
                };
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find trade", e);
        }
        return null;
    }

    private StockTrade loadStockTrade(ResultSet baseRs) throws SQLException {
        long id = baseRs.getLong("id");

        String sql = "SELECT * FROM stock_trade WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new StockTrade(
                        id,
                        baseRs.getDate("tradeDate").toLocalDate(),
                        baseRs.getString("counterparty"),
                        rs.getString("ticker"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("exchange"),
                        rs.getString("currency"));
            }
        }
        throw new SQLException("Stock trade details not found for id: " + id);
    }

    private FxTrade loadFxTrade(ResultSet baseRs) throws SQLException {
        long id = baseRs.getLong("id");

        String sql = "SELECT * FROM fx_trade WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new FxTrade(
                        id,
                        baseRs.getDate("tradeDate").toLocalDate(),
                        baseRs.getString("counterparty"),
                        FxProductType.valueOf(rs.getString("fxProductType")),
                        rs.getString("buyCurrency"),
                        rs.getString("sellCurrency"),
                        rs.getBigDecimal("buyAmount"),
                        rs.getBigDecimal("rate"),
                        rs.getDate("valueDate").toLocalDate());
            }
        }
        throw new SQLException("FX trade details not found for id: " + id);
    }

    @Override
    public List<Trade> findAll() {
        List<Trade> trades = new ArrayList<>();
        String sql = "SELECT * FROM trade";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TradeType tradeType = TradeType.valueOf(rs.getString("tradeType"));

                Trade trade = switch (tradeType) {
                    case STOCK -> loadStockTrade(rs);
                    case FX_SPOT, FX_FORWARD, FX_SWAP -> loadFxTrade(rs);
                };

                trades.add(trade);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load all trades", e);
        }

        return trades;
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM trade WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            // CASCADE will delete from child tables
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete trade", e);
        }
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

enum TradeType {
    STOCK,
    FX_SPOT,
    FX_FORWARD,
    FX_SWAP;

    public static TradeType fromTrade(Trade trade) {
        if (trade instanceof StockTrade) {
            return STOCK;
        } else if (trade instanceof FxTrade) {
            FxTrade fx = (FxTrade) trade;
            return switch (fx.getFxProductType()) {
                case SPOT -> FX_SPOT;
                case FORWARD -> FX_FORWARD;
                case SWAP -> FX_SWAP;
            };
        }
        throw new IllegalArgumentException("Unknown trade type: " + trade.getClass());
    }
}
