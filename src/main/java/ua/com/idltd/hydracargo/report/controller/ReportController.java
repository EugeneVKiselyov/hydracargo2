package ua.com.idltd.hydracargo.report.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @RequestMapping(value="/pdf", method = {GET,POST})
    public void exportReportToPdf(
            @RequestParam(name = "rep_id") Long rep_id,
            @RequestParam(name = "filename") String filename,
            HttpServletResponse response, HttpServletRequest request
    ) throws JRException, SQLException, IOException {

        String report=getReport(rep_id);

        Map<String, Object> params = new HashMap<>();
        Map<String, String[]> parameters = request.getParameterMap();
        parameters.keySet().forEach((key) -> {
            if (!key.equalsIgnoreCase("rep_id") && !key.equalsIgnoreCase("_csrf"))
                params.put(key, parameters.get(key)[0]);
        });

        InputStream jasperStream = this.getClass().getResourceAsStream(report);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        try (Connection conn = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

            response.setContentType("application/x-pdf");
            response.setHeader("Content-disposition", "inline; filename=" + filename + ".pdf");

            final OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        }
    }

    @RequestMapping(value="/xls", method = {GET,POST})
    public void exportReportToXls(
            @RequestParam(name = "rep_id") Long rep_id,
            @RequestParam(name = "filename") String filename,
            HttpServletResponse response, HttpServletRequest request
    ) throws JRException, SQLException, IOException {

        String report=getReport(rep_id);

        Map<String, Object> params = new HashMap<>();
        Map<String, String[]> parameters = request.getParameterMap();
        parameters.keySet().forEach((key) -> {
            if (!key.equalsIgnoreCase("rep_id") && !key.equalsIgnoreCase("_csrf"))
                params.put(key, parameters.get(key)[0]);
        });

        InputStream jasperStream = this.getClass().getResourceAsStream(report);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        try (Connection conn = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "inline; filename=" + filename + ".xls");

            JRXlsExporter exporter = new JRXlsExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setOnePagePerSheet(false);
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        }
    }

    @RequestMapping(value="/docx", method = {GET,POST})
    public void exportReportToDocx(
            @RequestParam(name = "rep_id") Long rep_id,
            @RequestParam(name = "filename") String filename,
            HttpServletResponse response, HttpServletRequest request
    ) throws JRException, SQLException, IOException {

        String report=getReport(rep_id);

        Map<String, Object> params = new HashMap<>();
        Map<String, String[]> parameters = request.getParameterMap();
        parameters.keySet().forEach((key) -> {
            if (!key.equalsIgnoreCase("rep_id") && !key.equalsIgnoreCase("_csrf"))
                params.put(key, parameters.get(key)[0]);
        });

        InputStream jasperStream = this.getClass().getResourceAsStream(report);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        try (Connection conn = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

            response.setContentType("application/vnd.ms-world");
            response.setHeader("Content-disposition", "inline; filename=" + filename + ".xls");

            JRDocxExporter exporter = new JRDocxExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            SimpleDocxExporterConfiguration configuration = new SimpleDocxExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        }
    }

    private String getReport(Long rep_id){
        String report="";
        switch (rep_id.intValue()) {
//            case 1 : report = "/static/report/Labels_Dis_A4.jasper"; break;
            case 1 : report = "/static/report/Labels_Dis_A4_1page1Label.jasper"; break;
            case 2 : report = "/static/report/Full_Dis_Inform_A4.jasper"; break;
            case 3 : report = "/static/report/Full_Dis_Loadfile_Xls.jasper"; break;
            case 4 : report = "/static/report/Labels_Dis_10x14.jasper"; break;
            case 7 : report = "/static/report/Report_7.jasper"; break;
            case 8 : {
                        if (activeProfile.equalsIgnoreCase("ipex")||
                            activeProfile.equalsIgnoreCase("ipex_test")) {
                            report = "/static/report/Report_8_Ipex.jasper";
                        } else {
                            report = "/static/report/Report_8.jasper";
                        }
                        break;
                     }
            case 10 : report = "/static/report/Report_10.jasper"; break;
            case 11 : report = "/static/report/Report_5_pdf.jasper"; break;
            case 12 : report = "/static/report/Scan_1_xls.jasper"; break;
        }
        return report;
    }
}
