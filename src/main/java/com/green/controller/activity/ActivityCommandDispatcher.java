package com.green.controller.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.controller.Command;
import com.green.service.ActivityService;
import com.green.service.ActivityServiceImpl;
import com.green.service.MemberService;
import com.green.service.MemberServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class ActivityCommandDispatcher {
    private final Map<String, Command> commands = new HashMap<>();

    public ActivityCommandDispatcher() {
        ActivityService activityService = new ActivityServiceImpl();
        MemberService memberService = new MemberServiceImpl();
        ObjectMapper objectMapper = new ObjectMapper();
        commands.put("GET", new GetActivityCommand(activityService, objectMapper));
        commands.put("POST", new PostActivityCommand(activityService, memberService, objectMapper));
        commands.put("PUT", new PutActivityCommand(activityService, objectMapper));
        commands.put("DELETE", new DeleteActivityCommand(activityService, objectMapper));
        commands.put("OPTIONS", new OptionsActivityCommand(activityService, objectMapper));
    }

    public Command getCommand(String name) {
        return commands.getOrDefault(name, (request, response) -> {
            throw new IllegalArgumentException(("Unknown command: " + name));
        });
    }
}