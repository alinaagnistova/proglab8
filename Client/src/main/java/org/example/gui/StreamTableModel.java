package org.example.gui;



import org.example.data.SpaceMarine;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.util.*;

public class StreamTableModel extends AbstractTableModel {
    private DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, GuiManager.getLocale());
    private String[] columnNames;
    private ArrayList<SpaceMarine> allData;
    private ArrayList<SpaceMarine> filteredData;
    private Integer sortingColumn = 0;
    private boolean reversed = false;
    private FilterWorker filterWorker;

    public StreamTableModel(String[] columnNames, int rowCount, FilterWorker filterWorker) {
        this.columnNames = columnNames;
        this.filterWorker = filterWorker;
    }

    public void setDataVector(ArrayList<SpaceMarine> data, String[] columnNames){
        this.allData = data;
        this.columnNames = columnNames;
        this.filteredData = actFiltration(data);
    }

    public void performSorting(int column){
        this.reversed = (sortingColumn == column)
                ? !reversed
                : false;
        this.sortingColumn = column;
        this.filteredData = actFiltration(this.allData);
    }

    public void performFiltration(){
        this.filteredData = actFiltration(this.allData);
    }

    @Override
    public int getRowCount() {
        return this.filteredData.size() - 1;
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.getValueAtRow(this.filteredData.get(rowIndex), columnIndex);
    }

    private ArrayList<SpaceMarine> actFiltration(ArrayList<SpaceMarine> allData){
        if(Objects.isNull(this.sortingColumn)) return allData;
        ArrayList<SpaceMarine> sorted = new ArrayList<>(allData.stream()
                .sorted(Comparator.comparing(o -> this.sortingColumn < 0
                        ? (float)o.getId()
                        : this.getSortedFiledFloat(o, this.sortingColumn)))
                .filter(filterWorker.getPredicate())
                .toList());
        if(reversed) Collections.reverse(sorted);
        return sorted;
    }

    public Object getValueAtRow(SpaceMarine o, int row){
        return switch (row){
            case 0 -> o.getId();
            case 1 -> o.getName();
            case 2 -> o.getCoordinates();
            case 3 -> dateFormat.format(o.getCreationDate());
            case 4 -> o.getHealth();
            case 5 -> o.getCategory();
            case 6 -> o.getWeaponType();
            case 7 -> o.getMeleeWeapon();
            case 8 -> o.getChapter().getName();
            case 9 -> o.getChapter().getMarinesCount();
            case 10 -> o.getUserLogin();
            default -> throw new IllegalStateException("Unexpected value: " + row);
        };
    }
//todo
    public float getSortedFiledFloat(SpaceMarine o, int column){
        return switch (column){
            case 0 -> o.getId();
            case 1 -> o.getName().length();
            case 2 -> o.getCoordinates().getRadius();
            case 3 -> o.getCreationDate().getDayOfYear();
            case 4 -> o.getHealth();
            case 5 -> o.getCategory().ordinal();
            case 6 -> o.getWeaponType().ordinal();
            case 7 -> o.getMeleeWeapon().ordinal();
            case 8 -> o.getChapter().getName().length();
            case 9 -> o.getChapter().getMarinesCount();
            case 10 -> o.getUserLogin().length();
            default -> throw new IllegalStateException("Unexpected value: " + column);
        };
    }

    public SpaceMarine getRow(int row) {
        try {
            return this.filteredData.get(row);
        } catch (IndexOutOfBoundsException e) {
            return this.filteredData.get(0);
        }
    }

    public ArrayList<SpaceMarine> getAllData() {
        return allData;
    }
}

