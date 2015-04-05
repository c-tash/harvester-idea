package ru.umeta.harvester.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.umeta.harvester.model.User;
import ru.umeta.harvester.services.IHarvestingManagementService;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;
import ru.umeta.harvesting.base.model.ScheduleElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import ru.umeta.harvesterspring.services.IHarvestingManagementService;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HarvestingManagementController {

    private static final Logger logger =
            LoggerFactory.getLogger(HarvestingManagementController.class);
    private final Map<Integer, User> userMap = new HashMap<>();
    private final IHarvestingManagementService harvestingManagementService;

    public HarvestingManagementController(
            IHarvestingManagementService harvestingManagementService) {
        this.harvestingManagementService = harvestingManagementService;
    }

    private String getHash(String value) {
        try {
            byte[] passwordBites = value.getBytes("UTF-8");
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(passwordBites);
            return Arrays.toString(digest);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/loginsubmit", method = RequestMethod.POST)
    public String loginsubmit(@RequestParam("username") String username,
                              @RequestParam("password") String password, HttpServletResponse response, Model model) {
        final String hash = getHash(password);
        User user = new User(username, hash, null);
        Integer token = user.hashCode();
        if (!userMap.containsKey(token)) {
            user = harvestingManagementService.login(user);
            if (user != null) {
                userMap.put(token, user);
            } else {
                return register();
            }
        }

        return queries(token, response, model);
    }

    @RequestMapping(value = "/queries", method = RequestMethod.POST)
    public String queries(@RequestParam("token") Integer token, HttpServletResponse response, Model model) {
        final User user = getUserFromToken(token, response);
        final List<Query> queries = harvestingManagementService.getQueriesForUser(user);

        model.addAttribute("token", token);
        model.addAttribute("queries", queries);
        return "queries";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {

        return "register";
    }

    @RequestMapping(value = "/registersubmit", method = RequestMethod.POST)
    public String registerSubmit(@RequestParam("username") String username,
                                 @RequestParam("password") String password, Model model) {
        String result = harvestingManagementService.register(username, getHash(password));
        model.addAttribute("result", result);
        return "registersubmit";
    }

    @RequestMapping(value = "/uploadprotocol", method = RequestMethod.POST)
    public String upload(@RequestParam("token") Integer token, HttpServletResponse response, Model model) {
        final User user = getUserFromToken(token, response);
        model.addAttribute("token", token);
        return "uploadProtocol";
    }

    private User getUserFromToken(Integer token, HttpServletResponse response) {
        if (!userMap.containsKey(token)) {
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Unauthorized: Authentication token was either missing or invalid.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return userMap.get(token);
        }
    }

    @RequestMapping(value = "/douploadprotocol", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("token") Integer token, HttpServletRequest request, HttpServletResponse response, @RequestParam("className") String className,
                                   @RequestParam("file") MultipartFile file, Model model) {
        getUserFromToken(token, response);
        String name = file.getOriginalFilename();
        String filePath =
                request.getSession().getServletContext().getRealPath("/") + "upload\\protocols\\";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                new File(filePath).mkdirs();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePath + name)));
                stream.write(bytes);
                stream.close();
                harvestingManagementService.addProtocol(name, className, filePath + name);
                model.addAttribute("uploadProtocolMessage", "You successfully uploaded " + name);

            } catch (Exception e) {
                model.addAttribute("uploadProtocolMessage", "You failed to upload " + name + " => " + e.getMessage());
            }
        } else {
            model.addAttribute("uploadProtocolMessage", "You failed to upload " + name + " because the file was empty.");
        }
        return queries(token, response, model);
    }

    @RequestMapping(value = "/createquery", method = RequestMethod.POST)
    public String createQuery(@RequestParam("token") Integer token, HttpServletResponse response, Model model) {
        final User user = getUserFromToken(token, response);
        final List<Protocol> protocols = harvestingManagementService.getProtocols();
        model.addAttribute("query", new Query());
        model.addAttribute("token", token);
        model.addAttribute("protocols", protocols);
        return "createquery";
    }

    @RequestMapping(value = "/submitquery", method = RequestMethod.POST)
    public String querySubmit(@ModelAttribute Query query, @RequestParam("token") Integer token,
                              HttpServletResponse response, Model model) {
        final User user = getUserFromToken(token, response);
        query = harvestingManagementService.addQuery(query, user);
        model.addAttribute("token", token);
        model.addAttribute("query", query);
        return "submitquery";
    }

    @RequestMapping(value = "/queryinfo", method = RequestMethod.POST)
    public String queryInfo(@RequestParam("token") Integer token, @RequestParam("qid") Integer queryId,
                            Model model, HttpServletResponse response) {
        final User user = getUserFromToken(token, response);
        Query query = harvestingManagementService.getQueryForId(queryId);
        List<ScheduleElement> scheduleList = harvestingManagementService.getFailedAttemptsForQuery(user, query);
        model.addAttribute("scheduleList", scheduleList);
        model.addAttribute("token", token);
        model.addAttribute("query", query);
        return "queryinfo";
    }

    @RequestMapping(value = "/changeactive", method = RequestMethod.POST)
    public String changeActive(
            @RequestParam("token") Integer token, @RequestParam("qid") Integer queryId, @RequestParam("active") String active,
            Model model, HttpServletResponse response) {
        final User user = getUserFromToken(token, response);
        if (harvestingManagementService.queryChangeActive(queryId, active, user)) {
            model.addAttribute("changeActiveStatusColor", "green");
            model.addAttribute("changeActiveStatus", "Успешно изменено.");
        } else {
            model.addAttribute("changeActiveStatusColor", "red");
            model.addAttribute("changeActiveStatus", "Произошла ошибка при изменении");
        }
        return queryInfo(token, queryId, model, response);
    }


    //    @RequestMapping(value = "/queries", method = RequestMethod.POST)
    //    public String nodes(@)
}
