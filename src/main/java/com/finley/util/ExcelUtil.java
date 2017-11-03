package com.finley.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zyf
 * @date 2017/3/8
 */
public class ExcelUtil {

    /**
     * 将excel表格数据转换成对象集合
     * @param file
     * @param fields
     * @param type
     * @return
     * @throws Exception
     */
    public static <T> List<T> parseData(MultipartFile file, String[] fields, Class<T> type) throws Exception {

        //文件扩展名
        String subfix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();

        String contextPath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletPath();

        String fileName = contextPath + "/" + UUID.randomUUID().toString() + "." + subfix;
        File localFile = new File(fileName);

        if (!localFile.getParentFile().exists()) {
            localFile.getParentFile().mkdirs();
        }

        //上传文件
        file.transferTo(localFile);

        Workbook wb = null;

        // 操作Excel2007的版本，扩展名是.xlsx
        if (subfix.endsWith("xlsx")) {
            wb = new XSSFWorkbook(new FileInputStream(localFile));
        }
        // 操作Excel2003以前（包括2003）的版本，扩展名是.xls
        else if (subfix.endsWith("xls")) {
            wb = new HSSFWorkbook(new FileInputStream(localFile));
        }

        //先保存到临时表里面，再处理到正式表，最后清空临时表
        List<T> datas = new ArrayList<T>();

        Sheet sheet = wb.getSheetAt(0);
        int count = sheet.getLastRowNum() + 1;
        try {
            for (int i = 1; i < count; i++) {
                Row row = sheet.getRow(i);
                int lastCellNum = row.getLastCellNum();
                int filedLength = fields.length;

                if (lastCellNum != filedLength) {
                    throw new Exception("表格格式不正确");
                }
                Class cls = type.getClass();
                T data = (T) cls.newInstance();
                for (int j = 0; j < lastCellNum; j++) {
                    String filedName = fields[j];
                    Field filed = cls.getField(filedName);      //获取字段名
                    filed.setAccessible(true);
                    Cell cell = row.getCell(j);                 //获取单元格
                    int cellType = cell.getCellType();
                    try {
                        if (cellType == 0) {                    //根据单元格数据类型获取数据，保存到属性中
                            filed.set(data, cell.getNumericCellValue());
                        } else if (cellType == 1) {
                            filed.set(data, cell.getStringCellValue());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                datas.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    /***
     * 将数据转换成excel表格
     * @param list
     * @param cls
     * @param columnNames
     * @param fieldsName
     * @return
     * @throws Exception
     */
    public static <T>  Workbook  createWorkBook1(List<T> list, Class<T> cls, String columnNames[], String[] fieldsName) throws Exception {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("xx");

        // 创建第一行
        Row row = sheet.createRow(0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());


        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }

        //设置每行每列的值
        for (int i = 0; i < list.size(); i++) {

            //Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            Row row1 = sheet.createRow(i + 1);
            sheet.setColumnWidth((short) i, (short) (35.7 * 120));
            T data = list.get(i);

            //在row行上创建一个方格

            for (int j = 0; j < fieldsName.length; j++) {
                Cell cell = row1.createCell(j);
                Field field = cls.getDeclaredField(fieldsName[j]);
                field.setAccessible(true);
                cell.setCellValue(field.get(data) == null ? " " : field.get(data).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

}
