package ua.com.idltd.hydracargo.fileloadlogs.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.fileloadlogs.filehandler_buffer.repository.VFileHandlerBufferRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;
import ua.com.idltd.hydracargo.utils.filehandler.repository.FileHandlerAtomLogRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;


@RestController
@RequestMapping("/fileloadlogs")
public class FileLoadLogsController {

    @PersistenceContext
    private EntityManager entityManager;

    private final VFileHandlerBufferRepository vFileHandlerBufferRepository;
    private final FileHandlerAtomLogRepository fileHandlerAtomLogRepository;

    public FileLoadLogsController(VFileHandlerBufferRepository vFileHandlerBufferRepository, FileHandlerAtomLogRepository fileHandlerAtomLogRepository) {
        this.vFileHandlerBufferRepository = vFileHandlerBufferRepository;
        this.fileHandlerAtomLogRepository = fileHandlerAtomLogRepository;
    }

    @RequestMapping(value = {"/post"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
    ){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/fileloadlogs/post/cover");
        return mav;
    }

    @PostMapping("/post/gettable")
    public JSONDatatable gettable(@RequestParam(name = "fhl_id", required = false) Long fhl_id) {
        JSONDatatable result = new JSONDatatable();
        if (fhl_id != null) {
            result.setData(vFileHandlerBufferRepository.getByFhl_id(GetUserName(),fhl_id));
        }else {
            result.setData(vFileHandlerBufferRepository.getAllByUser(GetUserName()));
        }
        return result;
    }


    @RequestMapping(value = {"/post/atom"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView atom(
    ){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/fileloadlogs/post/atom/cover");
        return mav;
    }

    @PostMapping("/post/atom/gettable")
    public JSONDatatable gettable_atom(@RequestParam(name = "fhl_id") Long fhl_id, @RequestParam(name = "fhal_status", required = false, defaultValue = "ERROR") String fhal_status) {
        JSONDatatable result = new JSONDatatable();
        if (fhl_id != null) {
            if (fhal_status.equalsIgnoreCase("ERROR")) {
                result.setData(fileHandlerAtomLogRepository.getByFhl_idandFhal_status(GetUserName(), fhl_id, fhal_status));
            }
            else {
                result.setData(fileHandlerAtomLogRepository.getByFhl_id(GetUserName(),fhl_id));
            }
        }
        return result;
    }
}
