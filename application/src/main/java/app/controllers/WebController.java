package app.controllers;

import DAO.*;
import com.google.common.collect.Lists;
import entities.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class WebController {
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home.html");
        return modelAndView;
    }

    @RequestMapping(value = "/trips", method = RequestMethod.GET)
    public ModelAndView trips(@RequestParam(name = "dep_time_min", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dep_time_min,
                              @RequestParam(name = "dep_time_max", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dep_time_max,
                              @RequestParam(name = "dep_city", required = false) String dep_city,
                              @RequestParam(name = "dep_st", required = false) String dep_st,
                              @RequestParam(name = "arr_time_min", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arr_time_min,
                              @RequestParam(name = "arr_time_max", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arr_time_max,
                              @RequestParam(name = "arr_st", required = false) String arr_st,
                              @RequestParam(name = "arr_city", required = false) String arr_city) {
        StationsOfRouteDAO stationsrt = new StationsOfRouteDAO();
        StationsDAO stations = new StationsDAO();
        SubroutesDAO sub = new SubroutesDAO();
        List<StationsOfRoute> result_dt = new ArrayList<StationsOfRoute>();
        List<StationsOfRoute> result_at = new ArrayList<StationsOfRoute>();
        List<Stations> result_an = new ArrayList<Stations>();
        List<Stations> result_dn = new ArrayList<Stations>();
        if (dep_time_max != null && dep_time_min != null) {
            result_dt = stationsrt.filter(Map.of("departFilter",
                    Lists.newArrayList(dep_time_min, dep_time_max)), StationsOfRoute.class);
        }
        if (arr_time_max != null && arr_time_min != null) {
            result_at = stationsrt.filter(Map.of("arrivalFilter",
                    Lists.newArrayList(arr_time_min, arr_time_max)), StationsOfRoute.class);
        }
        if (arr_st != null && arr_city != null) {
            result_an = stations.filter(Map.of("nameCityFilter",
                    Lists.newArrayList(arr_st, arr_city)), Stations.class);
        }
        if (dep_st != null && dep_city != null) {
            result_dn = stations.filter(Map.of("nameCityFilter",
                    Lists.newArrayList(dep_st, dep_city)), Stations.class);
        }
        if (result_at.size() > 0 && result_an.size() > 0) {
            result_an.retainAll(result_at.stream().map((s) -> s.getSt()).collect(Collectors.toSet()));
        } else if (result_an.size() == 0 && result_at.size() > 0) {
            result_an = result_at.stream().map((s) -> s.getSt()).collect(Collectors.toList());
        }
        if (result_dt.size() > 0 && result_dn.size() > 0) {
            result_dn.retainAll(result_dt.stream().map((s) -> s.getSt()).collect(Collectors.toSet()));
        } else if (result_dn.size() == 0 && result_dt.size() > 0) {
            result_dn = result_dt.stream().map((s) -> s.getSt()).collect(Collectors.toList());
        }
        List<Subroutes> res = new ArrayList<Subroutes>();
        for (Iterator<Stations> it = result_an.iterator(); it.hasNext(); ) {
            res.addAll(sub.getByJoinArr(it.next()));
        }
        List<Subroutes> second_res = new ArrayList<Subroutes>();
        for (Iterator<Stations> it = result_dn.iterator(); it.hasNext(); ) {
            second_res.addAll(sub.getByJoinDep(it.next()));
        }
        ModelAndView modelAndView = new ModelAndView();
        if (res.size() > 0 && second_res.size() > 0) {
            res.retainAll(second_res);
        } else if (res.size() == 0 && second_res.size() > 0) {
            res = second_res;
        } else if (res.size() == 0 && second_res.size() == 0) {
            modelAndView.addObject("error", "Not found!");
        }
        modelAndView.setViewName("trips.html");
        modelAndView.addObject("routes", res);
        return modelAndView;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ModelAndView info(@RequestParam(name = "id", required = false) Long id) {
        SubroutesDAO sub = new SubroutesDAO();
        Subroutes res = sub.getEntityById(id, Subroutes.class);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("info.html");
        modelAndView.addObject("routes", res);
        StationsOfRouteDAO strt = new StationsOfRouteDAO();
        List<StationsOfRoute> sr = strt.getByJoin(res.getRoute());
        if (sr.size() == 0) {
            modelAndView.addObject("error", "Not found!");
        }
        modelAndView.addObject("stations", sr);
        return modelAndView;
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ModelAndView order() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order.html");
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile.html");
        return modelAndView;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin.html");
        return modelAndView;
    }

    @RequestMapping(value = "/story", method = RequestMethod.GET)
    public ModelAndView story() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("story.html");
        return modelAndView;
    }

    @RequestMapping(value = "/passengers", method = RequestMethod.GET)
    public ModelAndView passengers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengers.html");
        return modelAndView;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(name = "user_login", required = true) String user_login,
                              @RequestParam(name = "user_psw", required = true) String user_psw,
                              @RequestParam(name = "error", required = false) String error) {
        UsersDAO users = new UsersDAO();
        List<Users> result = users.filter(Map.of("loginFilter",
                Lists.newArrayList(user_login)), Users.class);
        ModelAndView modelAndView = new ModelAndView();
        if (result.size() == 0) {
            modelAndView.setViewName("signin.html");
            modelAndView.addObject("error", "User not found!");
            return modelAndView;
        } else if (result.size() > 1) {
            modelAndView.setViewName("signin.html");
            modelAndView.addObject("error", "Multiple logins found!");
            return modelAndView;
        }
        if (!result.get(0).getPasswd().equals(user_psw)) {
            modelAndView.setViewName("signin.html");
            modelAndView.addObject("error", "Wrong password!");
            return modelAndView;
        }
        modelAndView.setViewName("signin.html");
        modelAndView.addObject("user_login", user_login);
        return modelAndView;
    }
}
