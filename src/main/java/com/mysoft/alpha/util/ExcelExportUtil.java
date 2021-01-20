package com.mysoft.alpha.util;

import com.google.common.base.Strings;

import net.bytebuddy.asm.Advice.This;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcelExportUtil {
	// 标题
	private String title;
	// 各个列的表头
	private String[] headList;
	// 各个列的元素key值
	private String[] headKey;
	// 需要填充的数据信息
	private List<Map<String, Object>> data;
	// 标题字体大小
	private int titleFontSize = 16;
	// 字体大小
	private int fontSize = 14;
	// 行高
	private int rowHeight = 30;
	// 列宽
	private int columWidth = 200;
	// 工作表
	private String sheetName = "sheet1";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getHeadList() {
		return headList;
	}

	public void setHeadList(String[] headList) {
		this.headList = headList;
	}

	public String[] getHeadKey() {
		return headKey;
	}

	public void setHeadKey(String[] headKey) {
		this.headKey = headKey;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public int getTitleFontSize() {
		return titleFontSize;
	}

	public void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}

	public int getColumWidth() {
		return columWidth;
	}

	public void setColumWidth(int columWidth) {
		this.columWidth = columWidth;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public byte[] exportData() throws IOException {
		// 检查参数配置信息
		checkConfig();
		// 创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建工作表
		HSSFSheet wbSheet = wb.createSheet(this.sheetName);
		// 设置默认行宽
		wbSheet.setDefaultColumnWidth(20);

		// 标题样式（加粗，垂直居中）
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		HSSFFont fontStyle = wb.createFont();
		fontStyle.setBold(true); // 加粗
		fontStyle.setFontHeightInPoints((short) this.titleFontSize); // 设置标题字体大小
		cellStyle.setFont(fontStyle);

		// 在第0行创建rows (表标题)
		HSSFRow title = wbSheet.createRow((int) 0);
		title.setHeightInPoints(30);// 行高
		HSSFCell cellValue = title.createCell(0);
		cellValue.setCellValue(this.title);
		cellValue.setCellStyle(cellStyle);
		wbSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (this.headList.length - 1)));
		// 设置表头样式，表头居中
		HSSFCellStyle style = wb.createCellStyle();
		// 设置单元格样式
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置字体
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) this.fontSize);
		style.setFont(font);
		// 在第1行创建rows
		HSSFRow row = wbSheet.createRow((int) 1);
		// 设置列头元素
		HSSFCell cellHead = null;
		for (int i = 0; i < headList.length; i++) {
			cellHead = row.createCell(i);
			cellHead.setCellValue(headList[i]);
			cellHead.setCellStyle(style);
		}

		// 设置每格数据的样式 （字体红色）
		HSSFCellStyle cellParamStyle = wb.createCellStyle();
		HSSFFont ParamFontStyle = wb.createFont();
		cellParamStyle.setAlignment(HorizontalAlignment.CENTER);
		cellParamStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		ParamFontStyle.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex()); // 设置字体颜色 (红色)
		ParamFontStyle.setFontHeightInPoints((short) this.fontSize);
		cellParamStyle.setFont(ParamFontStyle);
		// 设置每格数据的样式2（字体蓝色）
		HSSFCellStyle cellParamStyle2 = wb.createCellStyle();
		cellParamStyle2.setAlignment(HorizontalAlignment.CENTER);
		cellParamStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont ParamFontStyle2 = wb.createFont();
		ParamFontStyle2.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex()); // 设置字体颜色 (蓝色)
		ParamFontStyle2.setFontHeightInPoints((short) this.fontSize);
		cellParamStyle2.setFont(ParamFontStyle2);
		// 开始写入实体数据信息
		int a = 2;
		for (int i = 0; i < data.size(); i++) {
			HSSFRow roww = wbSheet.createRow((int) a);
			Map<String, Object> map = data.get(i);
			HSSFCell cell = null;
			for (int j = 0; j < headKey.length; j++) {
				cell = roww.createCell(j);
				cell.setCellStyle(style);
				Object valueObject = map.get(headKey[j]);
				String value = null;
				if (valueObject == null) {
					valueObject = "";
				}
				if (valueObject instanceof String) {
					// 取出的数据是字符串直接赋值
					value = (String) map.get(headKey[j]);
				} else if (valueObject instanceof Integer) {
					// 取出的数据是Integer
					value = String.valueOf(((Integer) (valueObject)).floatValue());
				} else if (valueObject instanceof BigDecimal) {
					// 取出的数据是BigDecimal
					value = String.valueOf(((BigDecimal) (valueObject)).floatValue());
				} else {
					value = valueObject.toString();
				}
				// 设置单个单元格的字体颜色
							 
				cell.setCellValue(Strings.isNullOrEmpty(value) ? "" : value);
				cell.setCellStyle(cellParamStyle2);
			}
			a++;
		}
		byte result[] = null;
		ByteArrayOutputStream out = null;
		// 导出数据
		try {
			out = new ByteArrayOutputStream();
	        wb.write(out);
	        result =  out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            try {
                if(null != out){
                    out.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ExcelExportUtil.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                try {
                    wb.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExcelExportUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
		}

        return result;
	}
	
	/**
	 * 开始导出数据信息
	 *
	 */
	public byte[] exportExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 检查参数配置信息
		checkConfig();
		// 创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建工作表
		HSSFSheet wbSheet = wb.createSheet(this.sheetName);
		// 设置默认行宽
		wbSheet.setDefaultColumnWidth(20);

		// 标题样式（加粗，垂直居中）
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		HSSFFont fontStyle = wb.createFont();
		fontStyle.setBold(true); // 加粗
		fontStyle.setFontHeightInPoints((short) this.titleFontSize); // 设置标题字体大小
		cellStyle.setFont(fontStyle);

		// 在第0行创建rows (表标题)
		HSSFRow title = wbSheet.createRow((int) 0);
		title.setHeightInPoints(30);// 行高
		HSSFCell cellValue = title.createCell(0);
		cellValue.setCellValue(this.title);
		cellValue.setCellStyle(cellStyle);
		wbSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (this.headList.length - 1)));
		// 设置表头样式，表头居中
		HSSFCellStyle style = wb.createCellStyle();
		// 设置单元格样式
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置字体
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) this.fontSize);
		style.setFont(font);
		// 在第1行创建rows
		HSSFRow row = wbSheet.createRow((int) 1);
		// 设置列头元素
		HSSFCell cellHead = null;
		for (int i = 0; i < headList.length; i++) {
			cellHead = row.createCell(i);
			cellHead.setCellValue(headList[i]);
			cellHead.setCellStyle(style);
		}

		// 设置每格数据的样式 （字体红色）
		HSSFCellStyle cellParamStyle = wb.createCellStyle();
		HSSFFont ParamFontStyle = wb.createFont();
		cellParamStyle.setAlignment(HorizontalAlignment.CENTER);
		cellParamStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		ParamFontStyle.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex()); // 设置字体颜色 (红色)
		ParamFontStyle.setFontHeightInPoints((short) this.fontSize);
		cellParamStyle.setFont(ParamFontStyle);
		// 设置每格数据的样式2（字体蓝色）
		HSSFCellStyle cellParamStyle2 = wb.createCellStyle();
		cellParamStyle2.setAlignment(HorizontalAlignment.CENTER);
		cellParamStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont ParamFontStyle2 = wb.createFont();
		ParamFontStyle2.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex()); // 设置字体颜色 (蓝色)
		ParamFontStyle2.setFontHeightInPoints((short) this.fontSize);
		cellParamStyle2.setFont(ParamFontStyle2);
		// 开始写入实体数据信息
		int a = 2;
		for (int i = 0; i < data.size(); i++) {
			HSSFRow roww = wbSheet.createRow((int) a);
			Map<String, Object> map = data.get(i);
			HSSFCell cell = null;
			for (int j = 0; j < headKey.length; j++) {
				cell = roww.createCell(j);
				cell.setCellStyle(style);
				Object valueObject = map.get(headKey[j]);
				String value = null;
				if (valueObject == null) {
					valueObject = "";
				}
				if (valueObject instanceof String) {
					// 取出的数据是字符串直接赋值
					value = (String) map.get(headKey[j]);
				} else if (valueObject instanceof Integer) {
					// 取出的数据是Integer
					value = String.valueOf(((Integer) (valueObject)).floatValue());
				} else if (valueObject instanceof BigDecimal) {
					// 取出的数据是BigDecimal
					value = String.valueOf(((BigDecimal) (valueObject)).floatValue());
				} else {
					value = valueObject.toString();
				}
				// 设置单个单元格的字体颜色
							 
				cell.setCellValue(Strings.isNullOrEmpty(value) ? "" : value);
				cell.setCellStyle(cellParamStyle2);
			}
			a++;
		}

		// 导出数据
		try {
			// 设置Http响应头告诉浏览器下载这个附件
			response.setHeader("Content-Disposition", "attachment;Filename=" + System.currentTimeMillis() + ".xls");
			OutputStream outputStream = response.getOutputStream();
			wb.write(outputStream);
			outputStream.close();
			return wb.getBytes();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IOException("导出Excel出现严重异常，异常信息：" + ex.getMessage());
		} finally {
			wb.close();
		}

	}

	/**
	 * 检查数据配置问题
	 *
	 * @throws IOException 抛出数据异常类
	 */
	protected void checkConfig() throws IOException {
		if (headKey == null || headList.length == 0) {
			throw new IOException("列名数组不能为空或者为NULL");
		}

		if (fontSize < 0 || rowHeight < 0 || columWidth < 0) {
			throw new IOException("字体、宽度或者高度不能为负值");
		}

		if (Strings.isNullOrEmpty(sheetName)) {
			throw new IOException("工作表表名不能为NULL");
		}
	}
	
    public static byte[] export(String sheetTitle, String[] title, List<Object> list) {

        HSSFWorkbook wb = new HSSFWorkbook();//创建excel表
        HSSFSheet sheet = wb.createSheet(sheetTitle);
        sheet.setDefaultColumnWidth(20);//设置默认行宽

        //表头样式（加粗，水平居中，垂直居中）
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //设置边框样式
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框

        HSSFFont fontStyle = wb.createFont();
        fontStyle.setBold(true);
        cellStyle.setFont(fontStyle);

        //标题样式（加粗，垂直居中）
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle2.setFont(fontStyle);

        //设置边框样式
        cellStyle2.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle2.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle2.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle2.setBorderRight(BorderStyle.THIN);//右边框

        //字段样式（垂直居中）
        HSSFCellStyle cellStyle3 = wb.createCellStyle();
        cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        //设置边框样式
        cellStyle3.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle3.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle3.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle3.setBorderRight(BorderStyle.THIN);//右边框

        //创建表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(20);//行高
        
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(sheetTitle);
        cell.setCellStyle(cellStyle);

        sheet.addMergedRegion(new CellRangeAddress(0,0,0,(title.length-1)));
        
        //创建标题
        HSSFRow rowTitle = sheet.createRow(1);
        rowTitle.setHeightInPoints(20);

        HSSFCell hc;
        for (int i = 0; i < title.length; i++) {
            hc = rowTitle.createCell(i);
            hc.setCellValue(title[i]);
            hc.setCellStyle(cellStyle2);
        }

        byte result[] = null;

        ByteArrayOutputStream out = null;
        
        try {
            //创建表格数据
            Field[] fields;
            int i = 2;

            for (Object obj : list) {
                fields = obj.getClass().getDeclaredFields();

                HSSFRow rowBody = sheet.createRow(i);
                rowBody.setHeightInPoints(20);

                int j = 0;
                for (Field f : fields) {

                    f.setAccessible(true);

                    Object va = f.get(obj);
                    if (null == va) {
                        va = "";
                    }

                    hc = rowBody.createCell(j);
                    hc.setCellValue(va.toString());
                    hc.setCellStyle(cellStyle3);
                    
                    j++;
                }

                i++;
            }

            out = new ByteArrayOutputStream();
            wb.write(out);
            result =  out.toByteArray();
        } catch (Exception ex) {
            Logger.getLogger(This.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                if(null != out){
                    out.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(This.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                try {
                    wb.close();
                } catch (IOException ex) {
                    Logger.getLogger(This.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }
}
