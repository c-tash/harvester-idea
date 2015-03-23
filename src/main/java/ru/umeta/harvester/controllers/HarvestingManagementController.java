package ru.umeta.harvester.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.umeta.harvester.model.User;
import ru.umeta.harvester.services.IHarvestingManagementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//import ru.umeta.harvesterspring.services.IHarvestingManagementService;


/**
 * Handles requests for the application home page.
 */
@Controller public class HarvestingManagementController {

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

    //	/**
    //	 * Simply selects the home view to render by returning its name.
    //	 */
    //	@RequestMapping(value = "/", method = RequestMethod.GET)
    //	public String home(Locale locale, Model model) {
    //		logger.info("Welcome home! The client locale is {}.", locale);
    //
    //		Date date = new Date();
    //		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
    //		String formattedDate = dateFormat.format(date);
    //
    //		model.addAttribute("serverTime", formattedDate );
    //
    //		return "home";
    //	}
    //
    //	@RequestMapping(value = "/", method = RequestMethod.GET)
    //	public String home(Model model) {
    //		return "";
    //	}

    //	@RequestMapping(value = "/nodes", method = RequestMethod.GET)
    //	public String nodes(Locale locale, Model model) {
    //		logger.info("Nodes controller is accessed.");
    //
    //		QueryMessage queryMessage = new QueryMessage();
    //		try {
    //			queryMessage = harvestingManagementService.getQueriesForUser(user, pw);
    //		} catch (Exception e) {
    //
    //		}
    //
    //		if (queryMessage.getText() == null ) {
    //
    //		}
    //		return "nodes";
    //	}

    @RequestMapping(value = "/login", method = RequestMethod.GET) public String login() {
        return "login";
    }

    @RequestMapping(value = "/loginsubmit", method = RequestMethod.POST)
    public String loginsubmit(@RequestParam("username") String username,
        @RequestParam("password") String password, HttpServletResponse response, Model model) {

        final String hash = getHash(password);
        Integer token = hash.hashCode();
        if (!userMap.containsKey(token)) {
            userMap.put(token, new User(username, hash));
        }

        return queries(token, response, model);
    }

    @RequestMapping(value = "/queries", method = RequestMethod.POST)
    public String queries(@RequestParam("token") Integer token, HttpServletResponse response, Model model) {
        final User user = getUserFromToken(token, response);
        harvestingManagementService.

    }

    @RequestMapping(value = "/register", method = RequestMethod.GET) public String register() {

        return "register";
    }

    @RequestMapping(value = "/registersubmit", method = RequestMethod.POST)
    public String registerSubmit(@RequestParam("username") String username,
        @RequestParam("password") String password, Model model) {
        String result = harvestingManagementService.register(username, password);
        return "registersubmit";
    }

    @RequestMapping(value = "/uploadProtocol", method = RequestMethod.POST)
    public String upload(@RequestParam("token") Integer token, HttpServletResponse response) {
        final User user = getUserFromToken(token, response);
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

    @RequestMapping(value = "/doUploadProtocol", method = RequestMethod.POST) public @ResponseBody
    String handleFileUpload(HttpServletRequest request, @RequestParam("className") String className,
        @RequestParam("file") MultipartFile file) {
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

                return "You successfully uploaded " + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }

    }

    //    @RequestMapping(value = "/queries", method = RequestMethod.POST)
    //    public String nodes(@)
}
