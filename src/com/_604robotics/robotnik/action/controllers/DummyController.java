package com._604robotics.robotnik.action.controllers;

import com._604robotics.robotnik.action.ActionController;

public class DummyController extends ActionController {
    protected String pickAction (String lastAction, String triggeredAction) {
        return "";
    }
}
