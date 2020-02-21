package ua.com.idltd.hydracargo.scan.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.dispatch.entity.Dispatch;
import ua.com.idltd.hydracargo.dispatch.repository.DispatchRepository;
import ua.com.idltd.hydracargo.scan.repository.Declaration_cache_scanRepository;
import ua.com.idltd.hydracargo.scan.repository.Declaration_scanRepository;
import ua.com.idltd.hydracargo.scan.repository.Declaration_scanViewRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.sql.DataSource;

import java.math.BigDecimal;

import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;


@RestController
@RequestMapping("/scan")
public class ScanController {

    private final DataSource dataSource;
    private final DispatchRepository dispatchRepository;
    private final Declaration_scanRepository declaration_scanRepository;
    private final Declaration_scanViewRepository declaration_scanViewRepository;
    private final Declaration_cache_scanRepository declaration_cache_scanRepository;

    public ScanController(@Qualifier("dataSource") DataSource dataSource, DispatchRepository dispatchRepository, Declaration_scanRepository declaration_scanRepository, Declaration_scanViewRepository declaration_scanViewRepository, Declaration_cache_scanRepository declaration_cache_scanRepository) {
        this.dataSource = dataSource;
        this.dispatchRepository = dispatchRepository;
        this.declaration_scanRepository = declaration_scanRepository;
        this.declaration_scanViewRepository = declaration_scanViewRepository;
        this.declaration_cache_scanRepository = declaration_cache_scanRepository;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(@RequestParam(value="dis_id") Long dis_id
    ){
        Dispatch dispatch = dispatchRepository.findById(dis_id).orElse(new Dispatch());

        ModelAndView mav = new ModelAndView();
        mav.addObject("dis_id", dis_id);
        mav.addObject("dis_num", dispatch.dis_num);
        mav.setViewName("/scan/cover");
        return mav;
    }
//Раскраска не просканированнная
    @PostMapping("/get_main_table")
    public JSONDatatable get_main_table(@RequestParam(value="dis_id") Long dis_id
    ) {
        JSONDatatable result = new JSONDatatable();
        result.setData(declaration_scanRepository.findByDis_idandDc_idIsNullandDs_scanfoundIsNull(dis_id));
        return result;
    }
//Декларации не просканированные
    @PostMapping("/get_declaration_notScan_table")
    public JSONDatatable get_linkedscan_table(@RequestParam(value="dis_id") Long dis_id
    ) {
        JSONDatatable result = new JSONDatatable();
        result.setData(declaration_cache_scanRepository.findByDis_idandNotScan(dis_id,GetUserName()));
        return result;
    }
//просканированные с ошибками
    @PostMapping("/get_scanerror_table")
    public JSONDatatable get_scanerror_table(@RequestParam(value="dis_id") Long dis_id
    ) {
        JSONDatatable result = new JSONDatatable();
        result.setData(declaration_scanViewRepository.findByDis_idandScanWithError(dis_id));
        return result;
    }
//просканированные и найденные в декларации
    @PostMapping("/get_scansuccess_table")
    public JSONDatatable get_scansuccess_table(@RequestParam(value="dis_id") Long dis_id
    ) {
        JSONDatatable result = new JSONDatatable();
        result.setData(declaration_scanRepository.findByDis_idandScanSuccess(dis_id));
        return result;
    }

    @PostMapping("/scan_shipment")
    public ResponseEntity<?> scan_shipment(@RequestParam(value="dis_id") Long dis_id,
                                           @RequestParam(value="shipment") String shipment
    ) {
        ResponseEntity result;
        try {
            JdbcTemplate template = new JdbcTemplate(dataSource);
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("vdis_id", dis_id)
                    .addValue("shipment", shipment)
                    .addValue("vmode", 1);
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template)
                    .withCatalogName("pkg_scan")
                    .withFunctionName("scan_shipment");
            BigDecimal link_result = simpleJdbcCall.executeFunction(BigDecimal.class, in);

            String link_result_color = template.queryForObject(
                    "select ds_color from Declaration_scan where dis_id = "+dis_id+" and ds_shipment like '%"+shipment+"%'",
                    null, String.class);
            result = ResponseEntity.ok(new ScanResult(link_result,link_result_color,shipment));

        } catch (Exception e){
            e.printStackTrace();
            result = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
