package UI.Components;

import UI.Theme.UITheme;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DataTable<T> extends JScrollPane {
    private final JTable table;
    private final Model<T> model;

    public DataTable(String[] columns, Function<T, Object[]> rowMapper) {
        this.model = new Model<>(columns, rowMapper);
        this.table = new JTable(model);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(UITheme.TABLE_ROW_HEIGHT);
        table.setFillsViewportHeight(true);
        table.setBackground(UITheme.BG_BASE);
        table.setForeground(UITheme.TEXT_LIGHT);
        table.setFont(UITheme.FONT_BODY);
        table.setSelectionBackground(UITheme.PRIMARY);
        table.setSelectionForeground(UITheme.TEXT_DARK);
        table.setGridColor(UITheme.BORDER);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 38));
        header.setBackground(UITheme.BG_DARK);
        header.setForeground(UITheme.TEXT_LIGHT);
        header.setFont(UITheme.FONT_BODY.deriveFont(Font.BOLD));

        getViewport().setBackground(UITheme.BG_BASE);
        setBorder(BorderFactory.createLineBorder(UITheme.BORDER));
        getVerticalScrollBar().setUnitIncrement(16);

        setViewportView(table);
    }

    public void setItems(List<T> items) {
        model.setItems(items);
    }

    public T getSelectedItem() {
        return model.getItemAt(table.getSelectedRow());
    }

    public JTable getTable() {
        return table;
    }

    private static class Model<T> extends AbstractTableModel {
        private final String[] columns;
        private final Function<T, Object[]> rowMapper;
        private final List<T> items = new ArrayList<>();
        private final List<Object[]> rows = new ArrayList<>();

        Model(String[] columns, Function<T, Object[]> rowMapper) {
            this.columns = columns;
            this.rowMapper = rowMapper;
        }

        void setItems(List<T> newItems) {
            items.clear();
            rows.clear();
            if (newItems != null) {
                for (T item : newItems) {
                    items.add(item);
                    rows.add(rowMapper.apply(item));
                }
            }
            fireTableDataChanged();
        }

        T getItemAt(int row) {
            return (row >= 0 && row < items.size()) ? items.get(row) : null;
        }

        @Override
        public int getRowCount() {
            return rows.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int col) {
            return columns[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return rows.get(row)[col];
        }
    }
}