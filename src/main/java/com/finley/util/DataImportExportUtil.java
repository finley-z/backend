package com.finley.util;

import com.finley.core.respone.ResultVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author zyf
 * @date 2017/3/8
 */
public class DataImportExportUtil {



    /***
     * excel数据导出
     *
     * @param response
     * @param workbook
     * @param title
     * @return
     * @throws IOException
     */
    public static boolean exportExcel(HttpServletResponse response, Workbook workbook, String title) throws IOException {
        boolean success = true;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            workbook.write(os);
            byte[] byteStream = os.toByteArray();
            InputStream is = new ByteArrayInputStream(byteStream);

            //设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((title + ".xls").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();

            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buffer = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buffer, 0, buffer.length))) {
                bos.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return success;
    }
}
