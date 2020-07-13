package ua.com.idltd.hydracargo.trace_cache;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.trace_cache.repository.Trace_cacheRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import java.util.ArrayList;

@RestController
@RequestMapping("/posttracking")
public class PostTrackingController {

    private final Trace_cacheRepository trace_cacheRepository;

    public PostTrackingController(Trace_cacheRepository trace_cacheRepository) {
        this.trace_cacheRepository = trace_cacheRepository;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(ModelAndView model
    ){
        model.setViewName("/posttracking/cover");
        return model;
    }

    @RequestMapping(value = "/gettable",method = { RequestMethod.GET, RequestMethod.POST})
    public JSONDatatable gettable(@RequestParam(value="shipment", required=false) String shipment) {
        JSONDatatable result = new JSONDatatable(new ArrayList<>());
        if (shipment != "") {
            result.setData(trace_cacheRepository.queryByShipment(shipment));
        }
        return result;
    }

}
