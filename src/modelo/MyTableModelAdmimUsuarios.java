/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Cathecita
 */
public class MyTableModelAdmimUsuarios extends AbstractTableModel{
    
    
    
    private String[] columns;
    private Object[][] rows;
    
    public MyTableModelAdmimUsuarios(){}
    
    public MyTableModelAdmimUsuarios(Object[][] data, String[] columnsName){
        this.columns = columnsName;
        this.rows = data;
    }
    
    //columnas que se necesitan para llevar a mi tabla de usuarios
    public Class getColumnClass(int col)
    {
        //Se cuenta desde 0 hasta 5 para lograr las 6 columnas
        if(col == 6){ return Icon.class; }
        
        else{
            return getValueAt(0, col).getClass();
        }
    }
    
    @Override
    public int getRowCount() {
    
        return this.rows.length;
        
    }

    @Override
    public int getColumnCount() {
    
        return this.columns.length;
        
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    
        return this.rows[rowIndex][columnIndex];
        
    }
    
    @Override
    public String getColumnName(int col){
        
        return this.columns[col];
        
    }
    
}
