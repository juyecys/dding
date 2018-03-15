package cn.com.dingduoduo.utils.common;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExcelUtils {

    private ExcelUtils(){
        super();
    }

    /**
     * 创建行内容
     *
     * @param hssfRow
     * @param contents
     */
    public static void createRowContents(HSSFRow hssfRow, String[] contents) {
        for(int i = 0; i < contents.length; i++) {
            HSSFCell headerCell = hssfRow.createCell(i);
            headerCell.setCellValue(contents[i]);
        }
    }
    
    public static void createRowContents(HSSFRow hssfRow, List<String> contents) {
        for(int i = 0; i < contents.size(); i++) {
            HSSFCell headerCell = hssfRow.createCell(i);
            headerCell.setCellValue(contents.get(i));
        }
    }

    /**
     *
     * 读取Excel的内容
     *
     * @param file
     *            读取数据的源Excel
     *
     * @param ignoreRows
     *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
     *
     * @return 读出的Excel中数据的内容
     */
    public static List<String[]> readXlsFile(File file, int ignoreRows) throws IOException {
        BufferedInputStream in = null;
        FileInputStream fileInputStream = null;
        try{
            List<String[]> result = new ArrayList<String[]>();
            int rowSize = 0;
            fileInputStream = new FileInputStream(file);
            in = new BufferedInputStream(fileInputStream);
            Workbook wb = null;
            try {
                wb = new XSSFWorkbook(in);
            } catch (Exception ex) {
                in = new BufferedInputStream(new FileInputStream(file));
                wb = new HSSFWorkbook(in);
            }
            Cell cell = null;
            for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
                Sheet st = wb.getSheetAt(sheetIndex);
                for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                    Row row = st.getRow(rowIndex);
                    if (row == null) {
                        continue;
                    }
                    int tempRowSize = row.getLastCellNum() + 1;
                    if (tempRowSize > rowSize) {
                        rowSize = tempRowSize;
                    }
                    String[] values = new String[rowSize];
                    Arrays.fill(values, "");
                    boolean hasValue = false;
                    for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                        String value = "";
                        cell = row.getCell(columnIndex);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_STRING:
                                    value = cell.getStringCellValue();
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                        Date date = cell.getDateCellValue();
                                        if (date != null) {
                                            value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                        } else {
                                            value = "";
                                        }
                                    } else {
                                        value = new DecimalFormat("0").format(cell.getNumericCellValue());
                                    }
                                    break;
                                case Cell.CELL_TYPE_FORMULA:
                                    // 导入时如果为公式生成的数据则无值
                                    value = "";
                                    break;
                                case Cell.CELL_TYPE_BLANK:
                                    break;
                                case Cell.CELL_TYPE_ERROR:
                                    value = "";
                                    break;
                                case Cell.CELL_TYPE_BOOLEAN:
                                    value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                                    break;
                                default:
                                    value = "";
                            }
                        }
                        if (columnIndex == 0 && value.trim().equals("")) {
                            break;
                        }
                        values[columnIndex] = value;
                        hasValue = true;
                    }
                    if (hasValue) {
                        result.add(values);
                    }
                }
            }
            return result;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if(fileInputStream != null){
                fileInputStream.close();
            }
            if(in != null){
                in.close();
            }

        }
    }

    /**
	 * 
	 * 设置excel报表的样式
	 * 
	 * @param hssfWorkbook
	 * @param columns
	 * @param rows
	 */
	private static void setWorkbookStyle(HSSFWorkbook hssfWorkbook, int columns, int rows, String sheetName) {

		HSSFSheet hssfSheet = hssfWorkbook.getSheet(sheetName);

		// header
		CellStyle headerStyle = hssfWorkbook.createCellStyle();
		headerStyle.setWrapText(true);
		HSSFFont headerFont = hssfWorkbook.createFont();
		headerFont.setBoldweight((short) 1000);
		headerStyle.setFont(headerFont);

		HSSFRow headerRow = hssfSheet.getRow(0);
		for (int c = 0; c < columns; c++) {
			headerRow.getCell(c).setCellStyle(headerStyle);
		}

		// content
		CellStyle contentStyle = hssfWorkbook.createCellStyle();
		contentStyle.setWrapText(true);
		HSSFFont contentFont = hssfWorkbook.createFont();
		contentFont.setBoldweight((short) 0);
		contentStyle.setFont(contentFont);

		for (int r = 1; r < rows; r++) {
			HSSFRow row = hssfSheet.getRow(r);
			for (int c = 0; c < columns; c++) {
				row.getCell(c).setCellStyle(contentStyle);
			}
		}

		// 自适应宽度
		for (int col = 0; col < columns; col++) {
			hssfSheet.autoSizeColumn(col);
		}
	}
	
	public static void exportReport(List<List<String>> report,String fileName, String sheetName) throws IOException {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFSheet hssfSheet = hssfWorkbook.createSheet(sheetName);

		try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
			int count = 0;
			for (List<String> row : report) {
				HSSFRow hssfRow = hssfSheet.createRow(count);
				count++;
				createRowContents(hssfRow, row);
			}

			setWorkbookStyle(hssfWorkbook, report.get(0).size(), report.size(), sheetName);
			hssfWorkbook.write(outputStream);
		} catch (IOException e) {
			throw e;
		}
	}
}
