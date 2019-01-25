package cl.coordinador.peajes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vtoro
 */
import java.io.*;
import java.util.StringTokenizer;
import org.apache.poi.xssf.usermodel.*;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.hssf.record.formula.Ptg;
//import org.apache.poi.hssf.record.formula.eval.*;
import org.apache.poi.xssf.usermodel.*;




public class Reliquidacion {
static String[] nomMes = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul",
            "Ago", "Sep", "Oct", "Nov", "Dic"};
    private static String slash = File.separator;
        private static final int numMeses = 12;
//craer un archivo que se llame reliquidacion con el resumen de los IT de facturaccion luego a este archivo se le ir?n agregadon hojas
//leer el archivo liquidacion mes del directorio de la liquidaci�n y copiar tablas y pegarlas en un nuevo archivo
//leer el archivo liquidacion mes del directorio de la RELIQUIDACION y copiar tablas y pegarlas en EL MISMO ARCHIVO
// pegar funciones suma y resta para creear cuadro de liquidaci�n
//copiar el ultimo cuadro en una hoja de pagos

public static void Reliquidacion(File DirectorioLiquidacion, File DirectorioReliquidacion, String mes, int Ano,String Fpago){

    String libroLiquidacion= DirectorioLiquidacion + slash +"Liquidaci�n" + mes + ".xlsx";
    String libroReliquidacion = DirectorioReliquidacion + slash +"Liquidaci�n" + mes + ".xlsx";
    File ArchivoReli= new File(DirectorioReliquidacion + slash +"Liquidaci�n" + mes + ".xlsx");

    int nummes=0;

    for(int i=0;i<numMeses;i++){
              if(mes.equals(nomMes[i])){
                  nummes=i;
                  i=i+1;
                  if(i<10)
                  mes="0"+i;
              }
          }

    String libroRit = DirectorioReliquidacion + slash +"rit" + mes + ".xlsx";
    mes=nomMes[nummes];

        Cell cLiq = null;
        Cell cReliq = null;
        Cell cRit = null;
        Cell cRitRel = null;
        Cell cRitIT = null;
        CellReference c4 = null;
        CellReference RefI;
        CellReference RefF;
        XSSFSheet  hojarit=null;
        XSSFSheet  hojacuadro=null;
        Row rowliq=null;
        Row rowReliq=null;
        Row rowRit=null;
        Row rowcuadro=null;
        Row rowRitRel=null;
        Row rowRitIT=null;

        try {
            //POIFSFileSystem fs = new //POIFSFileSystem(new FileInputStream(libroLiquidacion));
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(libroLiquidacion));
            //POIFSFileSystem fsRel = new //POIFSFileSystem(new FileInputStream(libroReliquidacion));
            XSSFWorkbook wbRel = new XSSFWorkbook(new FileInputStream(libroReliquidacion));

            //POIFSFileSystem fsrit = new //POIFSFileSystem(new FileInputStream(libroRit));
            XSSFWorkbook wbrit = new XSSFWorkbook(new FileInputStream(libroRit));
            hojarit = wbrit.getSheet("Detalle");
            hojacuadro=wbrit.getSheet("Cuadro de Pago");
            if(hojacuadro==null)
                hojacuadro = wbrit.createSheet("Cuadro de Pago");
            else{
                wbrit.removeSheetAt(wbrit.getSheetIndex(hojacuadro));
                hojacuadro = wbrit.createSheet("Cuadro de Pago");
            }
            if(hojarit==null)
                hojarit = wbrit.createSheet("Detalle");
            else{
                wbrit.removeSheetAt(wbrit.getSheetIndex(hojarit));
                hojarit = wbrit.createSheet("Detalle");
            }

            hojarit.setPrintGridlines(false);
            hojarit.setDisplayGridlines(false);
            hojacuadro.setPrintGridlines(false);
            hojacuadro.setDisplayGridlines(false);
            
            Font font = wbrit.createFont();
            font.setFontHeightInPoints((short)8);
            font.setFontName("Century Gothic");
            DataFormat formato1 = wbrit.createDataFormat();
            CellStyle estiloDatos1 = wbrit.createCellStyle();
            StringTokenizer formatoCompleto1 = new StringTokenizer("#,###,##0;[Red]-#,###,##0;\"-\"", ";");
            String formatoPos1 = formatoCompleto1.nextToken();
            estiloDatos1.setDataFormat(formato1.getFormat(formatoPos1));
            estiloDatos1.setFont(font);
            estiloDatos1.setBorderRight(CellStyle.BORDER_THIN);
            estiloDatos1.setRightBorderColor(IndexedColors.PALE_BLUE.getIndex());

            CellStyle estiloDatosA = wbrit.createCellStyle();
            estiloDatosA.setDataFormat(formato1.getFormat(formatoPos1));
            estiloDatosA.setFont(font);
            estiloDatosA.setBorderRight(CellStyle.BORDER_THIN);
            estiloDatosA.setRightBorderColor(IndexedColors.PALE_BLUE.getIndex());
            
            
            CellStyle estiloTexto = wbrit.createCellStyle();
            estiloTexto.setFont(font);


            Font fontTituloFila = wbrit.createFont();
            fontTituloFila.setFontHeightInPoints((short)8);
            fontTituloFila.setFontName("Century Gothic");
            fontTituloFila.setBoldweight(Font.BOLDWEIGHT_BOLD);
            CellStyle estiloTituloFila = wbrit.createCellStyle();
            estiloTituloFila.setFont(fontTituloFila);
            estiloTituloFila.setBorderRight(CellStyle.BORDER_THIN);
            estiloTituloFila.setRightBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloTituloFila.setBorderLeft(CellStyle.BORDER_THIN);
            estiloTituloFila.setLeftBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloTituloFila.setBorderBottom(CellStyle.BORDER_THIN);
            estiloTituloFila.setBottomBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloTituloFila.setBorderTop(CellStyle.BORDER_THIN);
            estiloTituloFila.setTopBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloTituloFila.setAlignment(CellStyle.ALIGN_CENTER);

            CellStyle estiloTituloFilaA = wbrit.createCellStyle();
            estiloTituloFilaA.setFont(fontTituloFila);
            estiloTituloFilaA.setBorderRight(CellStyle.BORDER_THIN);
            estiloTituloFilaA.setRightBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloTituloFilaA.setBorderLeft(CellStyle.BORDER_THIN);
            estiloTituloFilaA.setLeftBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloTituloFilaA.setBorderBottom(CellStyle.BORDER_THIN);
            estiloTituloFilaA.setBottomBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloTituloFilaA.setBorderTop(CellStyle.BORDER_THIN);
            estiloTituloFilaA.setTopBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloTituloFilaA.setAlignment(CellStyle.ALIGN_CENTER);
            estiloTituloFilaA.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
            estiloTituloFilaA.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

            Font fontTitulo = wbrit.createFont();
            fontTitulo.setFontHeightInPoints((short)9);
            fontTitulo.setFontName("Century Gothic");
            fontTitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            CellStyle estiloTitulo = wbrit.createCellStyle();
            estiloTitulo.setFont(fontTitulo);

            DataFormat formato4 = wbrit.createDataFormat();
            CellStyle estiloDatos4 = wbrit.createCellStyle();
            StringTokenizer formatoCompleto4 = new StringTokenizer("#,###,##0;[Red]-#,###,##0;\"-\"", ";");
            String formatoPos4 = formatoCompleto4.nextToken();
            estiloDatos4.setDataFormat(formato4.getFormat(formatoPos4));
            estiloDatos4.setFont(font);
            estiloDatos4.setBorderRight(CellStyle.BORDER_THIN);
            estiloDatos4.setRightBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloDatos4.setBorderBottom(CellStyle.BORDER_THIN);
            estiloDatos4.setBottomBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloDatos4.setBorderTop(CellStyle.BORDER_THIN);
            estiloDatos4.setTopBorderColor(IndexedColors.PALE_BLUE.getIndex());

            CellStyle estiloDatos4A = wbrit.createCellStyle();
            estiloDatos4A.setDataFormat(formato4.getFormat(formatoPos4));
            estiloDatos4A.setFont(font);
            estiloDatos4A.setBorderRight(CellStyle.BORDER_THIN);
            estiloDatos4A.setRightBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloDatos4A.setBorderBottom(CellStyle.BORDER_THIN);
            estiloDatos4A.setBottomBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloDatos4A.setBorderTop(CellStyle.BORDER_THIN);
            estiloDatos4A.setTopBorderColor(IndexedColors.PALE_BLUE.getIndex());
            estiloDatos4A.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
            estiloDatos4A.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

            AreaReference arefliq;
            CellReference[] crefsliq;
            AreaReference arefReliq;
            CellReference[] crefsReliq;

            int nomRangoInd = wb.getNameIndex("Total");
            Name nomRango = wb.getNameAt(nomRangoInd);
            arefliq = new AreaReference(nomRango.getRefersToFormula());
            crefsliq = arefliq.getAllReferencedCells();
            Sheet s = wb.getSheet(crefsliq[0].getSheetName());
            c4=arefliq.getLastCell();
           int filas=c4.getRow()-4;
           short columnas=c4.getCol();
           
            int nomRangoIndRe = wbRel.getNameIndex("Total");
            Name nomRangoRe = wbRel.getNameAt(nomRangoIndRe);
            arefReliq = new AreaReference(nomRangoRe.getRefersToFormula());
            crefsReliq = arefReliq.getAllReferencedCells();
            Sheet sRel = wbRel.getSheet(crefsReliq[0].getSheetName());
            
           int k=0;
           int tipo=0;
           int aux=0;
           int a=0;
           int indcolTot=300;
           double valor=0;
           XSSFFormulaEvaluator formula=new XSSFFormulaEvaluator(wbrit);


            for (int i=0; i<filas; i++) {
                     rowRit = hojarit.createRow(i+5);//hoja detalle, celdas para pagos con IT estimado
                     rowcuadro = hojacuadro.createRow(i+5);
                     rowRitRel = hojarit.createRow(i+10+filas);//hoja detalle, celdas para pagos con IT Real
                     rowRitIT = hojarit.createRow(i+14+filas*2);//hoja detalle, celdas para diferencia entre IT estiamdo - IT Real
                     //Lee datos en archivo de liquidaci�n con IT estimado
                     for (int j=0; j<columnas+1; j++) {
                         rowliq = s.getRow(crefsliq[i*columnas].getRow());
                         cLiq = rowliq.getCell(j);
                         cRit = rowRit.createCell(j);
                         if(cLiq==null){
                             cRit.setCellValue("");
                             if(i>1){
                                 aux=aux+1;
                                 if(aux==2){
                                      indcolTot=j;
                                      System.out.println(indcolTot);
                                 }
                             }
                         }
                         //copia datos en archivo de reliquidaci�n rit.xls
                         else{
                             tipo=cLiq.getCellType();
                             if(tipo==0){
                             cRit.setCellValue(cLiq.getNumericCellValue());
                             cRit.setCellStyle(estiloDatos1);
                             }
                             else if(tipo==1){
                             cRit.setCellValue(cLiq.toString());
                             cRit.setCellStyle(estiloTituloFila);
                             }
                             else if(tipo==2){
                             cRit.setCellFormula(cLiq.getCellFormula());
                             cRit.setCellStyle(estiloDatos4);
                             }
                             hojarit.autoSizeColumn(j);
                         }


                         rowReliq = sRel.getRow(crefsReliq[i*columnas].getRow());
                         cReliq = rowReliq.getCell(j);//Lee datos de archivo liquidacion con IT Real
                         cRitRel = rowRitRel.createCell(j);
                         cRitIT = rowRitIT.createCell(j);
                         if(cReliq==null){
                         cRitRel.setCellValue("");
                         }
                         else{
                             tipo=cReliq.getCellType();
                             if(tipo==0){
                             cRitRel.setCellValue(cReliq.getNumericCellValue());
                             cRitRel.setCellStyle(estiloDatos1);
                             RefI = new CellReference(cRit.getRowIndex(), cRit.getColumnIndex());
                             RefF = new CellReference(cRitRel.getRowIndex(), cRitRel.getColumnIndex());
                             cRitIT.setCellFormula(RefF.formatAsString()+"-"+RefI.formatAsString());
                             if(j<indcolTot){
                                 a=j;
                                 estiloDatosA.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
                                 estiloDatosA.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
                                 cRitIT.setCellStyle(estiloDatosA);
                                 formula.evaluateAllFormulaCells(wbrit);
                                 rowcuadro.createCell(a).setCellValue(cRitIT.getNumericCellValue());
                                 rowcuadro.getCell(a).setCellStyle(estiloDatosA);
                             }
                             else{
                             cRitIT.setCellStyle(estiloDatos1);
                             }
                             }
                             else if(tipo==1){
                             cRitRel.setCellValue(cReliq.toString());
                             cRitRel.setCellStyle(estiloTituloFila);
                             cRitIT.setCellValue(cReliq.toString());
                             if(j<indcolTot){
                                 a=j;
                                  cRitIT.setCellStyle(estiloTituloFilaA);
                                  rowcuadro.createCell(a).setCellValue(cRitIT.toString());
                                  rowcuadro.getCell(a).setCellStyle(estiloTituloFilaA);
                             }
                             else{                          
                               cRitIT.setCellStyle(estiloTituloFila);
                             }
                             }
                             else if(tipo==2){                           
                             RefI = new CellReference(14+filas, cRitRel.getColumnIndex());
                             RefF = new CellReference(cRitRel.getRowIndex()-1, cRitRel.getColumnIndex());
                             cRitRel.setCellFormula("sum("+RefI.formatAsString()+":"+RefF.formatAsString()+")");
                             cRitRel.setCellStyle(estiloDatos4);

                             RefI = new CellReference(cRit.getRowIndex(), cRit.getColumnIndex());
                             RefF = new CellReference(cRitRel.getRowIndex(), cRitRel.getColumnIndex());
                             cRitIT.setCellFormula(RefF.formatAsString()+"-"+RefI.formatAsString());
                             if(j<indcolTot){
                                 a=j;
                                  cRitIT.setCellStyle(estiloDatos4A);
                                   formula.evaluateAllFormulaCells(wbrit);
                                  rowcuadro.createCell(a).setCellValue(cRitIT.getNumericCellValue());
                                  rowcuadro.getCell(a).setCellStyle(estiloDatos4A);
                             }
                             else{
                               cRitIT.setCellStyle(estiloDatos4);
                             }
                             }
                             hojarit.autoSizeColumn(j);
                             hojacuadro.autoSizeColumn(a);
                         }
                     }
            }

            hojarit.createRow(3).createCell(1).setCellValue("1.- Liquidaci�n de Peajes correspondiente a "+ mes+" de "+Ano+
                    " (Valores en $ indexados a "+ mes+" de "+Ano+ " ) con IT Estimados");
            hojarit.getRow(3).getCell(1).setCellStyle(estiloTitulo);
            hojarit.createRow(filas+8).createCell(1).setCellValue("2.- C�lculo de Peajes correspondiente a "+ mes+" de "+Ano+
                    "  (Valores en $ indexados a "+ mes+" de "+Ano+ ") con IT Reales");
            hojarit.getRow(filas+8).getCell(1).setCellStyle(estiloTitulo);
            hojarit.createRow(12+filas*2).createCell(1).setCellValue("3.-Reliquidaci�n de Ingresos Tarifarios correspondiente a "+ mes+" de "+Ano+
                    " (Valores en $ indexados a "+ mes+" de "+Ano+ ")");
            hojarit.getRow(12+filas*2).getCell(1).setCellStyle(estiloTitulo);
            hojarit.createRow(16+filas*3).createCell(1).setCellValue("(1) Valores Positivos: Usuarios Pagan, Valores Negativos: Usuarios Reciben");
            hojarit.getRow(12+filas*2).getCell(1).setCellStyle(estiloTexto);
            hojarit.createRow(17+filas*3).createCell(1).setCellValue("(2) Suma de Cuadros N� 2 y N� 3");
            hojarit.getRow(12+filas*2).getCell(1).setCellStyle(estiloTexto);
            
            hojacuadro.createRow(2).createCell(1).setCellValue("Reliquidaci�n de Ingresos Tarifarios");
            hojacuadro.getRow(2).getCell(1).setCellStyle(estiloTitulo);
            hojacuadro.createRow(3).createCell(1).setCellValue( mes+" de "+Ano);
            hojacuadro.getRow(3).getCell(1).setCellStyle(estiloTitulo);
            hojacuadro.createRow(4).createCell(1).setCellValue("(Valores en $ - fecha de pago hasta: "+Fpago+")");
            hojacuadro.getRow(4).getCell(1).setCellStyle(estiloTitulo);

            FileOutputStream archivoSalida = new FileOutputStream( libroRit );
            wbrit.write(archivoSalida);
            archivoSalida.close();
            ArchivoReli.delete();

        }
        catch (java.io.FileNotFoundException e) {
                System.out.println( "No se se puede acceder al archivo " + e.getMessage());
        }
        catch (Exception e) {
                e.printStackTrace();
        }

    }
}
