package ua.com.idltd.hydracargo.sheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.com.idltd.hydracargo.justin.dto.JustinResponse;
import ua.com.idltd.hydracargo.justin.dto.JustinResponseResult;
import ua.com.idltd.hydracargo.justin.entity.Justin_Trace;
import ua.com.idltd.hydracargo.justin.repository.Justin_TraceRepository;
import ua.com.idltd.hydracargo.trace_cache.entity.Trace_cache;
import ua.com.idltd.hydracargo.trace_cache.repository.Trace_cacheRepository;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static ua.com.idltd.hydracargo.utils.StaticUtils.ConvertTraceExceptionToText;

@Component
public class ShedulerJustinHandler {

    @Value("${scheduler.justin.enable}")
    private boolean enabled;

    private final DataSource dataSource;

    private final Justin_TraceRepository justin_traceRepository;
    private final Trace_cacheRepository trace_cacheRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public ShedulerJustinHandler(@Qualifier("dataSource") DataSource dataSource, Justin_TraceRepository justin_traceRepository, Trace_cacheRepository trace_cacheRepository) {
        this.dataSource = dataSource;
        this.justin_traceRepository = justin_traceRepository;
        this.trace_cacheRepository = trace_cacheRepository;
    }

    @Scheduled(fixedRateString = "${scheduler.justin.fixedRate.in.milliseconds}")
    public void handleAll(){
        if (enabled) {
            List<Justin_Trace> justin_traceList = justin_traceRepository.getForProcessing();
            for (Justin_Trace jt : justin_traceList) {
                //формируем запрос и получаем ответ от Justin
                jt.jt_query=String.format("http://openapi.justin.ua/tracking_history/%s",jt.jt_jastinttn);
                jt.jt_lastupdate=new Date();
                try {
                    jt.jt_jsonconverterror=null;
                    jt.jt_responseerror=null;
                    jt.jt_lastupdate=new Date();

                    jt.jt_response = restTemplate.getForObject(jt.jt_query, String.class);
                    jt.jt_status = 0L;
                    justin_traceRepository.save(jt);

                    //Парсим ответ
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                    try {
                        JustinResponse justinResponse = objectMapper.readValue(jt.jt_response, JustinResponse.class);
                        jt.jt_jsonresponse = objectMapper.writeValueAsString(justinResponse);
                        jt.jt_status = 1L;

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        //Добавляем в нашу трассировку то чего там еще нет
                        for (JustinResponseResult jr : justinResponse.getResult()) {
                            Long count = trace_cacheRepository.queryByShipmentJustinDataTime(jt.jt_shipment, jr.getDate(), jr.getTime());
                            if (count==0) {
                                Trace_cache trace_cache = new Trace_cache();
                                trace_cache.trc_shipment=jt.jt_shipment;
                                trace_cache.trc_tracnum = jt.jt_jastinttn;
                                trace_cache.trc_eventtime = formatter.parse(jr.getDate()+" "+jr.getTime());
                                trace_cache.trc_event = jr.getEvent();
                                trace_cache.user_name="YoudaGZ";
                                trace_cacheRepository.save(trace_cache);
                            }
                            if (jr.getStatus().equalsIgnoreCase("Одержано")) jt.jt_status = 2L; // если посылку забрали то прекращаем трекинг
                        }
                        justin_traceRepository.save(jt);
                    } catch (JsonProcessingException e) {
                        jt.jt_jsonconverterror = ConvertTraceExceptionToText(e);
                        justin_traceRepository.save(jt);
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    jt.jt_responseerror = ConvertTraceExceptionToText(e);
                    justin_traceRepository.save(jt);
                    e.printStackTrace();
                }
            }
        }
    }
}
