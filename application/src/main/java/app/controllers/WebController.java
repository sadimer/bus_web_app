package app.controllers;

import DAO.*;
import DTO.*;
import com.google.common.collect.Lists;
import entities.*;
import mapper.RoutesMapper;
import mapper.StationsMapper;
import mapper.SubroutesMapper;
import mapper.UsersMapper;
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
    public ModelAndView home(@RequestParam(name = "user_id", required = false) Long user_id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home.html");
        UsersDAO usr = new UsersDAO();
        Users user = null;
        Boolean admin = null;
        if (user_id != null) {
            user = usr.getEntityById(user_id, Users.class);
            admin = user.getAdmin();
        }
        modelAndView.addObject("user_id", user_id);
        modelAndView.addObject("admin", admin);
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
                              @RequestParam(name = "user_id", required = false) Long user_id,
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
        modelAndView.addObject("user_id", user_id);
        return modelAndView;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ModelAndView info(@RequestParam(name = "id", required = false) Long id,
                             @RequestParam(name = "user_id", required = false) Long user_id) {
        SubroutesDAO sub = new SubroutesDAO();
        Subroutes res = null;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("info.html");
        StationsOfRouteDAO strt = new StationsOfRouteDAO();
        if (id == null) {
            SubroutesMapper mapper = new SubroutesMapper();
            StationsMapper stat_mapper = new StationsMapper();
            RoutesMapper route_mapper = new RoutesMapper();
            StationsDAO stat = new StationsDAO();
            RoutesDAO route = new RoutesDAO();
            Stations dep_st = stat.getEntityById(1L, Stations.class);
            StationsRsDTO dep_st_dto = stat_mapper.toDto(dep_st);
            Stations arr_st = stat.getEntityById(1L, Stations.class);
            StationsRsDTO arr_st_dto = stat_mapper.toDto(arr_st);
            Routes rout = route.getEntityById(1L, Routes.class);
            RoutesRsDTO rout_dto = route_mapper.toDto(rout);
            SubroutesRqDTO dto = new SubroutesRqDTO(rout_dto, arr_st_dto, dep_st_dto);
            res = mapper.toSubroutes(dto);
            sub.create(res);
            List<StationsOfRoute> sr = strt.getByJoin(res.getRoute());
            modelAndView.addObject("stations", sr);
            modelAndView.addObject("error", "This station and route added for example! Please delete it later!");
        } else {
            res = sub.getEntityById(id, Subroutes.class);
            List<StationsOfRoute> sr = strt.getByJoin(res.getRoute());
            modelAndView.addObject("stations", sr);
            if (sr.size() == 0) {
                modelAndView.addObject("error", "Not found!");
            }
        }
        modelAndView.addObject("routes", res);
        UsersDAO usr = new UsersDAO();
        Users user = null;
        Boolean admin = null;
        if (user_id != null) {
            user = usr.getEntityById(user_id, Users.class);
            admin = user.getAdmin();
        }
        modelAndView.addObject("user_id", user_id);
        modelAndView.addObject("id", id);
        modelAndView.addObject("admin", admin);
        return modelAndView;
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ModelAndView info_upd(@RequestParam(name = "id", required = false) Long id,
                                 @RequestParam(name = "user_id", required = false) Long user_id,
                                 @RequestParam(name = "company_name", required = false) String company_name,
                                 @RequestParam(name = "company_id", required = false) Long company_id,
                                 @RequestParam(name = "route_name", required = false) String route_name,
                                 @RequestParam(name = "st_index", required = false) Long st_index,
                                 @RequestParam(name = "st_ind", required = false) Long st_ind,
                                 @RequestParam(name = "st_name", required = false) String st_name,
                                 @RequestParam(name = "st_city", required = false) String st_city,
                                 @RequestParam(name = "st_id", required = false) Long st_id,
                                 @RequestParam(name = "arr_time_min", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arr_time_min,
                                 @RequestParam(name = "arr_time_max", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arr_time_max,
                                 @RequestParam(name = "dep_time_min", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dep_time_min,
                                 @RequestParam(name = "dep_time_max", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dep_time_max) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("info.html");
        modelAndView.addObject("user_id", user_id);
        return modelAndView;
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ModelAndView order(@RequestParam(name = "id", required = false) Long id,
                              @RequestParam(name = "user_id", required = false) Long user_id,
                              @RequestParam(name = "place", required = false) Long place) {
        SubroutesDAO sub = new SubroutesDAO();
        Subroutes res = sub.getEntityById(id, Subroutes.class);
        StationsOfRouteDAO strt = new StationsOfRouteDAO();
        List<StationsOfRoute> sr = strt.getByJoin(res.getRoute());
        UsersDAO usr = new UsersDAO();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order.html");
        Users user = null;
        if (user_id != null) {
            user = usr.getEntityById(user_id, Users.class);
            StationsOfRoute arr = null;
            StationsOfRoute dep = null;
            for (Iterator<StationsOfRoute> it = sr.iterator(); it.hasNext(); ) {
                StationsOfRoute tmp = it.next();
                if (tmp.getSt().getId() == res.getArrival_st().getId()) {
                    arr = tmp;
                }
                if (tmp.getSt().getId() == res.getDepart_st().getId()) {
                    dep = tmp;
                }
            }
            TicketsDAO tk = new TicketsDAO();
            Tickets tick = new Tickets();
            tick.setSub(res);
            tick.setUser(user);
            tick.setPrice(Double.valueOf(100));
            tick.setSeats(place);
            tk.create(tick);
            modelAndView.addObject("tickets", tick);
            modelAndView.addObject("arr", arr);
            modelAndView.addObject("dep", dep);
        } else {
            modelAndView.addObject("error", "Not found!");
        }
        modelAndView.addObject("user_id", user_id);
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(@RequestParam(name = "user_id", required = false) Long user_id) {
        ModelAndView modelAndView = new ModelAndView();
        UsersDAO usr = new UsersDAO();
        Users user = null;
        if (user_id != null) {
            user = usr.getEntityById(user_id, Users.class);
        } else {
            modelAndView.addObject("error", "Not found!");
        }
        if (user == null) {
            modelAndView.addObject("error", "Not found!");
            user_id = null;
        }
        modelAndView.setViewName("profile.html");
        modelAndView.addObject("user_id", user_id);
        modelAndView.addObject("users", user);
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ModelAndView upd_profile(@RequestParam(name = "user_id", required = false) Long user_id,
                                    @RequestParam(name = "name", required = false) String name,
                                    @RequestParam(name = "login", required = false) String login,
                                    @RequestParam(name = "passwd", required = false) String passwd,
                                    @RequestParam(name = "email", required = false) String email,
                                    @RequestParam(name = "phone", required = false) String phone) {
        ModelAndView modelAndView = new ModelAndView();
        UsersDAO usr = new UsersDAO();
        Users user = null;
        if (user_id != null) {
            user = usr.getEntityById(user_id, Users.class);
            if (name != null && name != "") {
                user.setName(name);
            }
            if (login != null && login != "") {
                user.setLogin(login);
            }
            if (passwd != null && passwd != "") {
                user.setPasswd(passwd);
            }
            if (email != null && email != "") {
                user.setEmail(email);
            }
            if (phone != null && phone != "") {
                user.setPhone(phone);
            }
            usr.update(user);
        } else {
            modelAndView.addObject("error", "Not found!");
        }
        if (user == null) {
            modelAndView.addObject("error", "Not found!");
            user_id = null;
        }
        modelAndView.setViewName("profile.html");
        modelAndView.addObject("user_id", user_id);
        modelAndView.addObject("users", user);
        return modelAndView;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin(@RequestParam(name = "user_id", required = false) Long user_id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin.html");
        UsersDAO usr = new UsersDAO();
        Users user = null;
        Boolean admin = null;
        if (user_id != null) {
            user = usr.getEntityById(user_id, Users.class);
            admin = user.getAdmin();
        }
        if (user == null) {
            modelAndView.addObject("error", "Not found!");
            user_id = null;
        }
        modelAndView.addObject("user_id", user_id);
        modelAndView.addObject("admin", admin);
        return modelAndView;
    }

    @RequestMapping(value = "/story", method = RequestMethod.GET)
    public ModelAndView story(@RequestParam(name = "user_id", required = false) Long user_id) {
        ModelAndView modelAndView = new ModelAndView();
        TicketsDAO tick = new TicketsDAO();
        UsersDAO usr = new UsersDAO();
        Users user = null;
        List<Tickets> tickets = null;
        if (user_id != null) {
            user = usr.getEntityById(user_id, Users.class);
            tickets = tick.getByJoin(user);
            if (tickets.size() == 0) {
                modelAndView.addObject("error", "Not found!");
            }
        } else {
            modelAndView.addObject("error", "Not found!");
        }
        if (user == null) {
            modelAndView.addObject("error", "Not found!");
            user_id = null;
        }
        modelAndView.setViewName("story.html");
        modelAndView.addObject("user_id", user_id);
        modelAndView.addObject("tickets", tickets);
        return modelAndView;
    }

    @RequestMapping(value = "/passengers", method = RequestMethod.GET)
    public ModelAndView passengers(@RequestParam(name = "user_id", required = false) Long user_id,
                                   @RequestParam(name = "route_id", required = false) Long route_id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengers.html");
        UsersDAO usr = new UsersDAO();
        SubroutesDAO sr = new SubroutesDAO();
        TicketsDAO tc = new TicketsDAO();
        List <Tickets> tickets = null;
        Users user = null;
        Boolean admin = null;
        if (user_id != null) {
            user = usr.getEntityById(user_id, Users.class);
            admin = user.getAdmin();
            tickets = tc.getByJoin(sr.getEntityById(route_id, Subroutes.class));
            if (tickets.size() == 0) {
                modelAndView.addObject("error", "Not found!");
            }
        }
        if (user == null) {
            modelAndView.addObject("error", "Not found!");
            user_id = null;
        }
        modelAndView.addObject("user_id", user_id);
        modelAndView.addObject("admin", admin);
        modelAndView.addObject("tickets", tickets);
        return modelAndView;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView signin(@RequestParam(name = "user_id", required = false) Long user_id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signin.html");
        modelAndView.addObject("user_id", user_id);
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
        modelAndView.addObject("user_id", result.get(0).getId());
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam(name = "name", required = true) String name,
                                 @RequestParam(name = "login", required = true) String login,
                                 @RequestParam(name = "passwd", required = true) String passwd,
                                 @RequestParam(name = "email", required = false) String email,
                                 @RequestParam(name = "phone", required = false) String phone) {
        ModelAndView modelAndView = new ModelAndView();
        UsersMapper mapper = new UsersMapper();
        UsersDAO users = new UsersDAO();
        Users entity = null;
        List<Users> result = users.filter(Map.of("loginFilter",
                Lists.newArrayList(login)), Users.class);
        if (result.size() > 0) {
            modelAndView.setViewName("signin.html");
            modelAndView.addObject("error", "User found! Please log in your account!");
        } else {
            if ((email == null || email == "") && (phone == null || phone == "")) {
                UsersRqDTO dto1 = new UsersRqDTO(name, passwd, login, false);
                entity = mapper.toUsers(dto1);
            } else if (email == null || email == "") {
                UsersRqPhoneDTO dto2 = new UsersRqPhoneDTO(name, phone, passwd, login, false);
                entity = mapper.toUsers(dto2);
            } else if (phone == null || phone == "") {
                UsersRqEmailDTO dto3 = new UsersRqEmailDTO(name, email, passwd, login, false);
                entity = mapper.toUsers(dto3);
            } else {
                UsersRqEmailPhoneDTO dto3 = new UsersRqEmailPhoneDTO(name, email, phone, passwd, login, false);
                entity = mapper.toUsers(dto3);
            }
            modelAndView.addObject("user_login", entity.getLogin());
            modelAndView.addObject("user_id", entity.getId());
            users.create(entity);
        }
        modelAndView.setViewName("signin.html");
        return modelAndView;
    }
}
