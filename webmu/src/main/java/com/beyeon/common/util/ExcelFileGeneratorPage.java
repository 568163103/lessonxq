/**
 * 
 */
package com.beyeon.common.util;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelFileGeneratorPage {

	private final int SPLIT_COUNT = 5000; //Excel每个工作簿的行数

	private ArrayList<String> fieldName1 = null; //excel标题数据集
	private ArrayList<String> fieldName2 = null; //excel标题数据集

	private ArrayList<ArrayList<String>> fieldData1 = null; //excel数据内容	
	private ArrayList<ArrayList<String>> fieldData2 = null; //excel数据内容	
	
	private HSSFWorkbook workBook = null;

	/**
	 * 构造器
	 * @param fieldName 结果集的字段名
	 * @param data
	 */
	public ExcelFileGeneratorPage(ArrayList<String> fieldName1, ArrayList<String> fieldName2,ArrayList<ArrayList<String>> fieldData1,ArrayList<ArrayList<String>> fieldData2) {
		this.fieldName1 = fieldName1;
		this.fieldName2 = fieldName2;
		this.fieldData1 = fieldData1;
		this.fieldData2 = fieldData2;
	}

	/**
	 * 创建HSSFWorkbook对象
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook createWorkbook1() {

		workBook = new HSSFWorkbook();//创建一个工作薄对象
		int rows1 = fieldData1.size();//总的记录数
		int sheetNum1 = 0;           //指定sheet的页数

		if (rows1 % SPLIT_COUNT == 0) {
			sheetNum1 = rows1 / SPLIT_COUNT;
		} else {
			sheetNum1 = rows1 / SPLIT_COUNT + 1;
		}
		
		int rows2 = fieldData2.size();//总的记录数
		int sheetNum2 = 0;           //指定sheet的页数

		if (rows2 % SPLIT_COUNT == 0) {
			sheetNum2 = rows2 / SPLIT_COUNT;
		} else {
			sheetNum2 = rows2 / SPLIT_COUNT + 1;
		}
		int sheetNum = sheetNum1 + sheetNum2;
		
		for (int i = 1; i <= sheetNum; i++) {//循环2个sheet的值
			if(i<=sheetNum1){
				HSSFSheet sheet = workBook.createSheet("Page " + i);//使用workbook对象创建sheet对象
				HSSFRow headRow = sheet.createRow((short) 0); //创建行，0表示第一行（本例是excel的标题）
				for (int j = 0; j < fieldName1.size(); j++) {//循环excel的标题
					HSSFCell cell = headRow.createCell(j);//使用行对象创建列对象，0表示第1列
					/**************对标题添加样式begin********************/
					//设置列的宽度
					sheet.setColumnWidth((short)j, (short)6000);
					HSSFCellStyle cellStyle = workBook.createCellStyle();//创建列的样式对象
					HSSFFont font = workBook.createFont();//创建字体对象
					//字体加粗
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					//字体颜色变红
					font.setColor(HSSFColor.RED.index);
					//如果font中存在设置后的字体，并放置到cellStyle对象中，此时该单元格中就具有了样式字体
					cellStyle.setFont(font); 
					cellStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
					cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					/**************对标题添加样式end********************/
					//添加样式
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(HSSFCell.ENCODING_UTF_16);
					if(fieldName1.get(j) != null){
						//将创建好的样式放置到对应的单元格中
						cell.setCellStyle(cellStyle);
						cell.setCellValue((String) fieldName1.get(j));//为标题中的单元格设置值
					}else{
						cell.setCellValue("-");
					}
				}
				//分页处理excel的数据，遍历所有的结果
				for (int k = 0; k < (rows1 < SPLIT_COUNT ? rows1 : SPLIT_COUNT); k++) {
					if (((i - 1) * SPLIT_COUNT + k) >= rows1)//如果数据超出总的记录数的时候，就退出循环
						break;
					HSSFRow row = sheet.createRow((short) (k + 1));//创建1行
					//分页处理，获取每页的结果集，并将数据内容放入excel单元格
					ArrayList<String> rowList = (ArrayList<String>) fieldData1.get((i - 1) * SPLIT_COUNT + k);
					for (int n = 0; n < rowList.size(); n++) {//遍历某一行的结果
						HSSFCell cell = row.createCell(n);//使用行创建列对象
						cell.setCellValue(HSSFCell.ENCODING_UTF_16);
						if(rowList.get(n) != null){
							cell.setCellValue((String) rowList.get(n).toString());
						}else{
							cell.setCellValue("");
						}
					}
				}
			}else{
					HSSFSheet sheet = workBook.createSheet("Page " + i);//使用workbook对象创建sheet对象
					HSSFRow headRow = sheet.createRow((short) 0); //创建行，0表示第一行（本例是excel的标题）
					for (int j = 0; j < fieldName2.size(); j++) {//循环excel的标题
						HSSFCell cell = headRow.createCell(j);//使用行对象创建列对象，0表示第1列
						/**************对标题添加样式begin********************/
						//设置列的宽度
						sheet.setColumnWidth((short)j, (short)6000);
						HSSFCellStyle cellStyle = workBook.createCellStyle();//创建列的样式对象
						HSSFFont font = workBook.createFont();//创建字体对象
						//字体加粗
						font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						//字体颜色变红
						font.setColor(HSSFColor.RED.index);
						//如果font中存在设置后的字体，并放置到cellStyle对象中，此时该单元格中就具有了样式字体
						cellStyle.setFont(font); 
						cellStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
						cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						/**************对标题添加样式end********************/
						//添加样式
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(HSSFCell.ENCODING_UTF_16);
						if(fieldName2.get(j) != null){
							//将创建好的样式放置到对应的单元格中
							cell.setCellStyle(cellStyle);
							cell.setCellValue((String) fieldName2.get(j));//为标题中的单元格设置值
						}else{
							cell.setCellValue("-");
						}
					}
					//分页处理excel的数据，遍历所有的结果
					for (int k = 0; k < (rows2 < SPLIT_COUNT ? rows2 : SPLIT_COUNT); k++) {
						if (((i - sheetNum1-1) * SPLIT_COUNT + k) >= rows2)//如果数据超出总的记录数的时候，就退出循环
							break;
						HSSFRow row = sheet.createRow((short) (k + 1));//创建1行
						//分页处理，获取每页的结果集，并将数据内容放入excel单元格
						ArrayList<String> rowList = (ArrayList<String>) fieldData2.get((i - sheetNum1-1) * SPLIT_COUNT + k);
						for (int n = 0; n < rowList.size(); n++) {//遍历某一行的结果
							HSSFCell cell = row.createCell(n);//使用行创建列对象
							cell.setCellValue(HSSFCell.ENCODING_UTF_16);
							if(rowList.get(n) != null){
								cell.setCellValue((String) rowList.get(n).toString());
							}else{
								cell.setCellValue("");
							}
						}
					}
				}	
		}
		
		return workBook;
	}

	public void expordExcel(OutputStream os) throws Exception {
		workBook = createWorkbook1();
		workBook.write(os);//将excel中的数据写到输出流中，用于文件的输出
		os.close();
	}

}