package ua.com.idltd.hydracargo.beans;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ua.com.idltd.hydracargo.user.entity.Workplace;


import java.util.List;

@Component
@SessionScope
public class Currentworkplace {

    private long wp_id = -1;
    private String wp_name = "undefined";
    private String wp_texttemplate = "undefined";
    private boolean isLoad = false;
    private boolean isEmpty = true;
    private List<Workplace> workplaces;

    public void setWp_id(long wp_id) {
        if (isLoad && !isEmpty) {
            for(Workplace workplace : workplaces){
                if (workplace.wp_id==wp_id) {
                    this.wp_id=workplace.wp_id;
                    this.wp_name=workplace.wp_name;
                    this.wp_texttemplate=workplace.wp_texttemplate;
                }
            }
            this.wp_id = wp_id;
        }
    }

    public long getWp_id() {
        return wp_id;
    }

    public Workplace getCurrent(){
        Workplace result = null;
        if (isLoad && !isEmpty) {
            for (Workplace workplace : workplaces) {
                if (workplace.wp_id == this.wp_id) {
                    result = workplace;
                }
            }
        }
        return result;
    }

    public String getWp_name() {
        return wp_name;
    }

    public String getWp_texttemplate() {
        return wp_texttemplate;
    }

    public List<Workplace> getWorkplaces() {
        return workplaces;
    }

    public boolean isLoad() {
        return isLoad;
    }


    public boolean isEmpty() {
        return isEmpty;
    }


    public void setWorkplace(List<Workplace> workplaces) {
        this.workplaces = workplaces;
        if (workplaces.size()!=0) {
            this.wp_id = workplaces.get(0).wp_id;
            this.wp_name = workplaces.get(0).wp_name;
            this.wp_texttemplate = workplaces.get(0).wp_texttemplate;
        }
        else {
            this.wp_id = -1;
            this.wp_name = "undefined";
            this.wp_texttemplate = "undefined";
        }
        this.isLoad = true;
        this.isEmpty = (workplaces.size()==0);
    }
}
